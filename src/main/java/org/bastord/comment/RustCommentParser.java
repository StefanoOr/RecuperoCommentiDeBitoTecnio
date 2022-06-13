package org.bastord.comment;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RustCommentParser extends AbstractCommentParser {
    @Override
    public List<Comment> parse(Reader reader) throws IOException {
        //List<Boolean> multiRiga = new ArrayList<>();
        int numeroRiga = 1;
        int colonna = 0;
        int colonnaCommento = 0;
        int numeroMultiRiga = 1;

        String contenuto = readAsString(reader);

        //querto

        StringBuilder commentoAttuale = new StringBuilder();
        boolean contenutoMultiRiga = false;
        boolean contenutoRiga = false;
        boolean inStringa = false;
        char caratterePrecedente = 0;
        var commenti = new ArrayList<Comment>();
        var rimuoviAsterischi = false;
        var rimuoviSlash = false;
        var rimuoviEsclamativi=false;

        for (int i = 0; i < contenuto.length(); i++) {
            boolean ultimoCarattere = i == contenuto.length() - 1;
            char carattereAttuale = contenuto.charAt(i);

            if (inStringa) {

                if (carattereAttuale == '"' && caratterePrecedente != '\\') {
                    inStringa = false;
                }

            } else if (contenutoRiga || contenutoMultiRiga) {
                if (contenutoRiga && ((carattereAttuale == '\n' || ultimoCarattere) )) {
                    contenutoRiga = false;

                    // COMMENTO RIGA NORMALE
                    // non deve essere processato ulteriormente

                    //numeroMultiRiga = 0;
                    String commento = commentoAttuale.toString().stripIndent();
                     if(rimuoviSlash){

                        if(commento.stripLeading().startsWith("/")){
                            commento= commento.stripLeading().substring(1).stripLeading();
                        }
                        commento = commento
                                .lines() // prendi le linee del commento
                                .map(riga -> {
                                    // se la riga inizia con '/', rimuovilo, e rimuovi eventuail spazi
                                    if (riga.stripLeading().startsWith("/")) {
                                        return riga.stripLeading().substring(1).stripLeading();
                                    }


                                    return riga;
                                })
                                // raggruppa le righe in una stringa
                                .collect(Collectors.joining("\n"));

                    }

                    rimuoviSlash = false;
                    commenti.add(new Comment(numeroRiga, colonnaCommento, numeroMultiRiga, commento));

                    commentoAttuale.setLength(0);
                } else if (contenutoMultiRiga && ((carattereAttuale == '/' && caratterePrecedente == '*') || ultimoCarattere)) {
                    contenutoMultiRiga = false;


                    if (carattereAttuale=='/') {
                        commentoAttuale.setLength(commentoAttuale.length()-1);
                    }

                    //COMMENTO MULTI RIGA
                    String commento = commentoAttuale.toString().stripIndent();

                    //rimuovi asterischi dai commenti rustDoc
                    if (rimuoviAsterischi ) {
                        commento = commento
                                .lines() // prendi le linee del commento
                                .map(riga -> {
                                    // se la riga inizia con '*', rimuovilo, e rimuovi eventuail spazi


                                    if (riga.stripLeading().startsWith("*")) {
                                        return riga.stripLeading().substring(1).stripLeading();
                                    }

                                    // la riga non inizia con '*', non modificarla
                                    return riga;
                                })
                                // raggruppa le righe in una stringa
                                .collect(Collectors.joining("\n"));
                    }

                    for (int j =0 ; j<1 && commento.stripLeading().startsWith("!"); j++){
                       commento= commento.substring(1);
                    }



                    commenti.add(new Comment(numeroRiga-numeroMultiRiga+1, colonnaCommento, numeroMultiRiga, commento));
                    numeroMultiRiga = 1;
                    rimuoviAsterischi = false; // disabilita il flag per i prossimi commenti


                    commentoAttuale.setLength(0);
                } else {
                    // se siamo in un commento multilinea ed il primo carattere è un asterisco, vuol dire
                    //che è cominciato con /** e lo consideration un commento javadoc
                    if (commentoAttuale.isEmpty() && contenutoMultiRiga && (carattereAttuale=='*' || carattereAttuale=='!')){
                         rimuoviAsterischi = true;
                    }else if(commentoAttuale.isEmpty() && contenutoRiga && carattereAttuale=='/'){
                        rimuoviSlash = true;
                    }
                    commentoAttuale.append(carattereAttuale);
                }

            } else if (carattereAttuale == '/' && caratterePrecedente == '/') {
                colonnaCommento = colonna;

                contenutoRiga = true;

            } else if (carattereAttuale == '*' && caratterePrecedente == '/') {
                colonnaCommento = colonna;

                contenutoMultiRiga = true;
            } else if (carattereAttuale == '"') {
                inStringa = true;
            }

            caratterePrecedente = carattereAttuale;
            colonna++;

            if (carattereAttuale == '\n') {
                if (contenutoMultiRiga) {
                    numeroMultiRiga++;
                }
                colonna = 0;
                numeroRiga++;
            }

        }

        return commenti;
    }


}
