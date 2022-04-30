package org.bastord.comment;

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
        List<Comment> listaCommenti = new ArrayList<>();
        boolean contenutoMultiRiga = false;
        boolean contenutoRiga = false;
        boolean inStringa = false;
        char caratterePrecedente = 0;

        for (int i = 0; i < contenuto.length(); i++) {
            boolean ultimoCarattere = i == contenuto.length() - 1;
            char carattereAttuale = contenuto.charAt(i);

            if (inStringa) {

                if (carattereAttuale == '"' && caratterePrecedente != '#') {
                    inStringa = false;
                }
            } else if (contenutoRiga || contenutoMultiRiga) {
                if (contenutoRiga && ((carattereAttuale == '\n' || ultimoCarattere) )) {
                    contenutoRiga = false;
                    String commento = commentoAttuale.toString();
                    int numeroRighe = Math.toIntExact(commento.lines().count());
                    listaCommenti.add(new Comment(-1, -1, numeroRighe, commento));
                    commentoAttuale.setLength(0);
                } else {
                    commentoAttuale.append(carattereAttuale);
                }

            } else if (carattereAttuale == '#') {
                contenutoRiga = true;
            } else if (carattereAttuale == '"') {
                inStringa = true;
            }

            caratterePrecedente = carattereAttuale;

        }

        return listaCommenti;
    }
}
