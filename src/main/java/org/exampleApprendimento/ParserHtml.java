package org.exampleApprendimento;

import org.bastord.comment.commentParser.CLikeCommentParser;
import org.bastord.comment.Comment;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.BooleanSupplier;

public class ParserHtml {

    private static final boolean sì = true;
    private static final boolean no = false;

    private static String input = """
            <html>ciao</html>
            """;

    public static void main(String[] args) throws Exception {
        parse(input);
    }

    public static void parse(String input) throws IOException {
        var tokenizer = new Tokenizer(new StringReader(input));
        tokenizer.next();
    }

    record Cursore(int riga, int colonna) {

        public Cursore avanza(char c) {

            int nuovaColonna = colonna + 1;
            int nuovaRiga = riga;

            if (c == '\n') {
                nuovaRiga = riga + 1;
                nuovaColonna = 1;
            }
            return new Cursore(nuovaRiga, nuovaColonna);
        }

        public Cursore colonnaPrecedente() {
            return new Cursore(riga, colonna - 1);
        }
    }

    ;

    static class Tokenizer {

        private final Reader reader;
        private Cursore cursore = new Cursore(1, 1);
        private char attuale;
        private char precedente;
        private Stack<InizioTag> daChiudere = new Stack<>();
        private Cursore cursorePrecedente;
        private boolean oracolo;

        Tokenizer(String s) {
            this(new StringReader(s));
        }

        Tokenizer(Reader reader) {
            this.reader = reader;
        }

        public void next() throws IOException {
            avanza();
            while (true) {

                if (èLaFine(attuale)) {
                    break;
                }
                //controllo  di inizio tag
                else if (attuale == '<') {

                    if (prossimo() == '!') {
                        Commento commento = leggiCommento();
                        System.out.println(commento);
                    } else {
                        InizioTag tag = leggiTag();
                        if (tag != null) {
                            if (!tag.giàChiuso) daChiudere.push(tag);
                            System.out.println(tag);
                            if ("script".equalsIgnoreCase(tag.nome)) {
                                var da = cursore;
                                String testoScript = leggiScript();

                                Reader reader = new StringReader(testoScript);
                                CLikeCommentParser a =new CLikeCommentParser();

                             List<Comment> listaCommenti = a.parse(reader);

                             for (Comment commento : listaCommenti){
                                 System.out.println("\n\ncommenti clike\n\n "+commento.comment()+ " "+ commento.line()+ " "+ commento.column());
                             }


                                Script script = new Script(da, cursore, testoScript);



                                System.out.println(script);
                                // Quasi finito qui, i tag letti sono quelli
                                // tag = leggiTag();
//                                if (tag != null)
//                                    throw documentoInvalido("Atteso </script> ma era " + tag.nome);

                                continue;
                            }
                        }
                        avanza();
                    }
                } else {
                    System.out.println(leggiTesto());
                }
            }
        }

        record Script(Cursore da, Cursore a, String script) {
        }

        ;

        private String leggiScript() throws IOException {
            var sb = new StringBuilder();
            while (!sb.toString().toLowerCase().endsWith("</script>")) {
                avanza();
                sb.append(attuale);
            }
            avanza();
            return sb.substring(0, sb.length() - "</script>".length());
        }

        record Commento(Cursore da, Cursore a, String testo) {
        }

        ;

        private Commento leggiCommento() throws IOException {
            var inizio = cursore;

            aspettati('!');
            aspettati('-');
            aspettati('-');

            StringBuilder commento = new StringBuilder();
            while (!commento.toString().endsWith("-->")) {
                commento.append(avanza());
            }
            return new Commento(inizio, cursore, commento.substring(0, commento.length() - 3));
        }

        private void aspettati(char c) throws IOException {
            avanza();
            if (attuale != c) {
                throw documentoInvalido("Atteso '" + c + "' ma era '" + attuale + "'");
            }
        }

        private boolean èLaFine(char c) {
            return c == (char) -1;
        }

        private Testo leggiTesto() throws IOException {
            var inizio = cursore;
            var testo = new StringBuilder();
            do {
                testo.append(attuale);
                avanza();
            } while (attuale != '<' && attuale != (char) -1);

            return new Testo(inizio, cursorePrecedente, testo.toString());
        }

        record Testo(Cursore da, Cursore a, String testo) {
        }

        ;

        record InizioTag(Cursore da, Cursore a, String nome, List<Attributo> attributi, boolean giàChiuso) {
        }

        ;

        private InizioTag leggiTag() throws IOException {
            var posizione = cursore.colonnaPrecedente();
            var nomeTag = leggiNomeTag();

            if (nomeTag.startsWith("/")) {
                if (daChiudere.isEmpty())
                    throw documentoInvalido("Tag '" + nomeTag + "' chiuso, ma nessuno è stato aperto");

                if (!daChiudere.peek().nome.equals(nomeTag.substring(1)))
                    throw documentoInvalido("Tag '" + nomeTag + "' chiuso, ma bisognava chiudere '" + daChiudere.peek().nome + "'");

                daChiudere.pop();
                return null;
            }

            var attributi = new ArrayList<Attributo>();
            while (true) {
                if (isASpece(attuale)) {
                    avanza();
                    continue;
                } else if (isALetter(attuale)) {
                    attributi.add(leggiAttributo());
                    continue;
                } else if (attuale == '/') {
                    aspettati('>');
                    return new InizioTag(posizione, cursore, nomeTag, attributi, sì);
                } else if (attuale == '>') {
                    return new InizioTag(posizione, cursore, nomeTag, attributi, no);
                }

                // avanza
                avanza();
            }
        }

        record Attributo(Cursore da, Cursore a, String nome, String valore) {
        }

        ;

        private Attributo leggiAttributo() throws IOException {
            var posizione = cursore;
            var nomeAttributo = leggiNomeAttributo();
            if (isASpece(attuale)) {
                // Attributo letto, non ha un valore
                return new Attributo(posizione, cursore, nomeAttributo, null);
            } else if (attuale != '=') {
                throw documentoInvalido("'=' atteso ma era '" + attuale + "'");
            }

            avanza();
            if (attuale == '\'' || attuale == '"') {
                var virgolette = attuale;
                var valore = leggiStringa(() -> attuale == virgolette && precedente != '\\');
                return new Attributo(posizione, cursore, nomeAttributo, valore);
            } else {
                var valore = attuale + leggiStringa(() -> !isALetter(attuale) && !isANumber(attuale) && attuale != '_');
                return new Attributo(posizione, cursore, nomeAttributo, valore);
            }
        }

        private String leggiStringa(BooleanSupplier stop) throws IOException {
            StringBuilder sb = new StringBuilder();
            while (true) {
                avanza();
                if (stop.getAsBoolean()) {
                    break;
                }
                sb.append(attuale);
            }
            return sb.toString();
        }

        private IOException documentoInvalido(String messaggio) {
            return new IOException("Il documento non è un HTML valido, " + messaggio);
        }

        private void leggiSpazi() throws IOException {
            while (isASpece(attuale)) {
                avanza();
            }
        }

        private String leggiNomeAttributo() throws IOException {
            var sb = new StringBuilder();
            sb.append(attuale);
            while (true) {
                var carattere = avanza();
                if (isASpece(carattere) || carattere == '=') {
                    break;
                } else if (!isALetter(carattere) && !isANumber(carattere) && carattere != '-' && carattere != '_') {
                    throw documentoInvalido("'" + carattere + "' è usato in un nome attributo");
                }
                sb.append(carattere);
            }

            return sb.toString();
        }

        private String leggiNomeTag() throws IOException {
            var sb = new StringBuilder();
            while (true) {
                avanza();
                if (isASpece(attuale)) {
                    break;
                } else if (attuale == '/' && !sb.isEmpty()) {
                    // lo slash prima della fine
                    break;
                } else if (attuale == '>') {
                    break;
                } else if (isALetter(attuale) || isANumber(attuale) || attuale == '-' || attuale == '_'
                        || (attuale == '/' && sb.isEmpty())) {
                    sb.append(attuale);
                } else {
                    throw documentoInvalido("'" + attuale + "' è usato in un nome tag");
                }
            }

            return sb.toString();
        }

        private boolean isASpece(char c) {
            return Character.isWhitespace(c);
        }

        private boolean isALetter(char c) {
            return Character.isLetter(c);
        }

        private boolean isANumber(char c) {
            return Character.isDigit(c);
        }


        private char avanza() throws IOException {
            if (attuale == (char) -1)
                throw new IOException("Tentativo di avanzare dopo la fine");

            precedente = attuale;
            cursorePrecedente = cursore;
            if (oracolo) {
                oracolo = false;
            } else {
                attuale = (char) reader.read();
            }
            cursore = cursore.avanza(attuale);

            return attuale;
        }

        private char prossimo() throws IOException {
            if (oracolo)
                return attuale;

            avanza();
            oracolo = true;
            return attuale;
        }
    }

}
