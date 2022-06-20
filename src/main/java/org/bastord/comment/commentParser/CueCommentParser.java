package org.bastord.comment.commentParser;


import org.bastord.comment.AbstractCommentParser;
import org.bastord.comment.Comment;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CueCommentParser extends AbstractCommentParser {

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

        for (int i = 0; i < contenuto.length(); i++) {
            boolean ultimoCarattere = i == contenuto.length() - 1;
            char carattereAttuale = contenuto.charAt(i);

            if (inStringa) {

                if (carattereAttuale == '"' && caratterePrecedente != '\\') {
                    inStringa = false;
                }

            } else if (contenutoRiga ) {
                if (contenutoRiga && ((carattereAttuale == '\n' || ultimoCarattere) )) {
                    contenutoRiga = false;

                    // COMMENTO RIGA NORMALE
                    // non deve essere processato ulteriormente
                    commenti.add(new Comment(numeroRiga, colonnaCommento, numeroMultiRiga, commentoAttuale.toString()));
                    //numeroMultiRiga = 0;

                    commentoAttuale.setLength(0);

                } else {

                    commentoAttuale.append(carattereAttuale);
                }

            } else if (carattereAttuale == '/' && caratterePrecedente == '/') {
                colonnaCommento = colonna;

                contenutoRiga = true;

            } else if (carattereAttuale == '"') {
                inStringa = true;
            }

            caratterePrecedente = carattereAttuale;
            colonna++;

            if (carattereAttuale == '\n') {
                colonna = 0;
                numeroRiga++;
            }

        }

        return commenti;
    }
}
