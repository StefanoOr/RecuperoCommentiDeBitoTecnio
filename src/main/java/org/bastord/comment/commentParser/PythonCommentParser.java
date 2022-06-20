package org.bastord.comment.commentParser;

import org.bastord.comment.AbstractCommentParser;
import org.bastord.comment.Comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class PythonCommentParser extends AbstractCommentParser {

    @Override
    public List<Comment> parse(Reader reader) throws IOException {
        String contenuto = readAsString(reader);

        StringBuilder commentoAttuale = new StringBuilder();
        var commenti = new ArrayList<Comment>();
        boolean contenutoMultiRiga = false;
        boolean contenutoRiga = false;
        boolean inStringa = false;
        char caratterePrecedente = 0;
        char caratterePrePrecedente=0;

        int numeroRiga = 1;
        int colonna = 0;
        int colonnaCommento = 0;
        int numeroMultiRiga = 1;



        for (int i = 0; i < contenuto.length(); i++) {
            boolean ultimoCarattere = i == contenuto.length() - 1;
            char carattereAttuale = contenuto.charAt(i);

            if(reader.toString().equals("feature_block.py")){
                System.out.println("ferma");

            }

            if (inStringa) {

                if (carattereAttuale == '"' && caratterePrecedente != '\\') {
                    inStringa = false;
                }
            } else if (contenutoRiga || contenutoMultiRiga) {
                if (contenutoRiga && ((carattereAttuale == '\n' || ultimoCarattere) )) {
                    contenutoRiga = false;

                    commenti.add(new Comment(numeroRiga, colonnaCommento, numeroMultiRiga, commentoAttuale.toString()));
                    commentoAttuale.setLength(0);
                } else if (contenutoMultiRiga && ((carattereAttuale == '"' && caratterePrecedente=='"' && caratterePrePrecedente== '"') || ultimoCarattere)) {
                    contenutoMultiRiga = false;

                    if (carattereAttuale == '"') {
                        commentoAttuale.setLength(commentoAttuale.length() - 1);
                    }
                    //COMMENTO MULTI RIGA
                    String commento = commentoAttuale.toString().stripIndent();


                    commenti.add(new Comment(numeroRiga - numeroMultiRiga + 1, colonnaCommento, numeroMultiRiga, commento));
                    numeroMultiRiga = 1;
                    commentoAttuale.setLength(0);
                    commentoAttuale.append(carattereAttuale);

                }else{
                    commentoAttuale.append(carattereAttuale);
                }



            } else if (carattereAttuale == '#' ) {
                colonnaCommento = colonna;
                contenutoRiga = true;
            }else if (carattereAttuale=='"' && caratterePrecedente=='"' && caratterePrePrecedente=='"') {

                colonnaCommento = colonna;
                contenutoMultiRiga = true;

            }else if (carattereAttuale == '"') {
                inStringa = true;
            }

            caratterePrePrecedente=caratterePrecedente;
            caratterePrecedente = carattereAttuale;
            colonna++;

            if (carattereAttuale == '\n') {
                if(contenutoMultiRiga){
                    numeroMultiRiga++;
                }

                colonna = 0;
                numeroRiga++;
            }

        }

        return commenti;
    }
}