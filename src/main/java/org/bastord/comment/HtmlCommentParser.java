package org.bastord.comment;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class HtmlCommentParser extends AbstractCommentParser {


    @Override
    public List<Comment> parse(Reader reader) throws IOException {
        //List<Boolean> multiRiga = new ArrayList<>();
        String contenuto = readAsString(reader);

        StringBuilder commentoAttuale = new StringBuilder();
        var commenti = new ArrayList<Comment>();

        boolean rimuoviAsterischi = false;
        boolean contenutoMultiRiga = false;
        boolean contenutoRiga = false;
        boolean inStringa = false;
        boolean inScript = false;

        char caratterePrecedente = 0;
        char caratterePrePrecedente = 0;
        char caratterePrePrePrePrecendente = 0;

        int numeroRiga = 1;
        int colonna = 0;
        int colonnaCommento = 0;
        int numeroMultiRiga = 1;


        //trovo il numero di righe del file
        int totaleNumeroRigheFile = numeroRigheFile(contenuto);
        var rigaInizioScript = new ArrayList<Integer>();
        var rigaFineScript = new ArrayList<Integer>();
        var colonnaInizioScript = new ArrayList<Integer>();
        var colonnaFineScript = new ArrayList<Integer>();

        //splittiamo tutta la stringa per ricavare le singole righe di codice
        String[] strings = contenuto.split("\n");

        //ciclo per trovare l'inizio e la fine degli script
        for (int j = 0; j < totaleNumeroRigheFile; j++) {

            if (strings[j].contains("<script>")) {
                rigaInizioScript.add(j + 1);
            }
            if (strings[j].contains("</script>")) {
                rigaFineScript.add(j + 1);


            }

        }

        for (int i = 0; i < contenuto.length(); i++) {
            boolean ultimoCarattere = i == contenuto.length() - 1;
            char carattereAttuale = contenuto.charAt(i);


            if (inStringa) {

                if (carattereAttuale == '"' && caratterePrecedente != '\\') {
                    inStringa = false;
                }
            } else if (contenutoRiga || contenutoMultiRiga) {
                //fine del commento singolo Javascript all'interno del corpo <script> //
                if (contenutoRiga && ((carattereAttuale == '\n' || ultimoCarattere)) && inScript) {
                    contenutoRiga = false;

                    commenti.add(new Comment(numeroRiga, colonnaCommento, numeroMultiRiga, commentoAttuale.toString()));
                    commentoAttuale.setLength(0);
                } else if (contenutoMultiRiga && ((carattereAttuale == '/' && caratterePrecedente == '*') || ultimoCarattere) && inScript) {
                    contenutoMultiRiga = false;


                    if (carattereAttuale == '/') {
                        commentoAttuale.setLength(commentoAttuale.length() - 1);
                    }

                    //COMMENTO MULTI RIGA
                    String commento = commentoAttuale.toString().stripIndent();

                    //rimuovi asterischi dai commenti javadoc

                    if (rimuoviAsterischi) {
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


                    commenti.add(new Comment(numeroRiga - numeroMultiRiga + 1, colonnaCommento, numeroMultiRiga, commento));
                    numeroMultiRiga = 1;
                    rimuoviAsterischi = false; // disabilita il flag per i prossimi commenti

                    commentoAttuale.setLength(0);

                    //--> fine del commento html stile
                } else if (contenutoMultiRiga && ((carattereAttuale == '>' && caratterePrecedente == '-' && caratterePrePrecedente == '-') || ultimoCarattere)) {
                    contenutoMultiRiga = false;

                    if (carattereAttuale == '>') {
                        commentoAttuale.setLength(commentoAttuale.length() - 1);
                    }
                    //COMMENTO MULTI RIGA
                    String commento = commentoAttuale.toString().stripIndent();


                    commenti.add(new Comment(numeroRiga - numeroMultiRiga + 1, colonnaCommento, numeroMultiRiga, commento));
                    numeroMultiRiga = 1;
                    commentoAttuale.setLength(0);
                    commentoAttuale.append(carattereAttuale);

                    //fine del m


                } else {
                    // se siamo in un commento multilinea ed il primo carattere è un asterisco, vuol dire
                    //che è cominciato con /** e lo consideration un commento javadoc
                    if (commentoAttuale.isEmpty() && contenutoMultiRiga && carattereAttuale == '*') {
                        rimuoviAsterischi = true;
                    }
                    commentoAttuale.append(carattereAttuale);
                }
                //Inizio commento html style <!--
            } else if (carattereAttuale == '-' && caratterePrecedente == '-' && caratterePrePrecedente == '!' && caratterePrePrePrePrecendente == '<') {

                colonnaCommento = colonna;
                contenutoMultiRiga = true;

                //inizio stringa


                //inizio commento javascript nel caso in cui siamo nel corpo dello script //
            } else if (carattereAttuale == '/' && caratterePrecedente == '/' && inScript) {
                colonnaCommento = colonna;
                contenutoRiga = true;

                //inizio commento multiriga Javascript nel caso in cui siamo nel corpo delle script /*
            } else if (carattereAttuale == '*' && caratterePrecedente == '/' && inScript) {
                colonnaCommento = colonna;
                contenutoMultiRiga = true;

            } else if (carattereAttuale == '"') {
                inStringa = true;
                //controllo se la riga contiene l'inizio del corpo dello script
            } else if (rigaInizioScript.contains(numeroRiga) && (rigaFineScript.contains(numeroRiga)) ) {
              if(carattereAttuale=='>' && caratterePrecedente=='t' && caratterePrePrecedente=='p' && caratterePrePrePrePrecendente=='i'){
                  inScript=false;


              }

            } else if (rigaInizioScript.contains(numeroRiga)) {
                inScript = true;
            } else if (rigaFineScript.contains(numeroRiga)) {
                inScript = false;

            }


            caratterePrePrePrePrecendente = caratterePrePrecedente;
            caratterePrePrecedente = caratterePrecedente;
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


    public int numeroRigheFile(String contenutoFile) {

        int numeroRighe = 1;


        for (int i = 0; i < contenutoFile.length(); i++) {
            char carattereAttuale = contenutoFile.charAt(i);
            if (carattereAttuale == '\n') {
                numeroRighe++;
            }
        }


        return numeroRighe;
    }


    //funzione che mi ritorna un array int , ogni valore all'interno dell'array
    //equivale al numero di colonna dove inizia la stringa nella riga passata al metodo
    public ArrayList<Integer> colonnineScript(String str, String commento) {
        var colonnina = new ArrayList<Integer>();
        int colonna = 0;

        char[] ch = new char[str.length()];

        // Copying character by character into array
        // using for each loop
        for (int i = 0; i < str.length(); i++) {
            ch[i] = str.charAt(i);
        }


        for (int i = 0; i < commento.length(); i++) {
            char carattereAttuale = commento.charAt(i);


            colonna++;
        }

        return colonnina;

    }
}
