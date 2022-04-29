package org.example;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader; // versione precedente
import java.io.StringWriter;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ste_1
 */
public class Main {

    public static final char CSV_SEPARATOR = ';'; // it could be a comma or a semi colon

    enum TipoCommento {
        RIGA,
        MULTI_RIGA,
    }

    public record Commento(int riga, int colonna, String commento) {

    }

    public static void main(String[] args) throws IOException {

        String fileName = "C:\\Users\\ste_1\\Desktop\\DemoJava.java";
        System.out.println("Nome del file=" + fileName + ". Tipo di file= " + getFileExtension(fileName));

        String typeOfFile = getFileExtension(fileName);

        if (typeOfFile.equals("c") || typeOfFile.equals("java")) {
            cLike(fileName);
        } else if (typeOfFile.equals("py")) {
            pythonType(fileName);
        }

    }

    //funzione  per estrarre il tipo di  file  , return string del tipo del file
    public static String getFileExtension(String fullName) {

        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private static void stampaCommenti(List<Commento> listaCommenti) {
        System.err.println("--------------- \n ");
        for (Commento string : listaCommenti) {

            System.out.format("%d: %d: %s\n", string.riga, string.colonna, string.commento.trim().replaceAll(" " + " ", ""));
        }

    }

    public static void cLike(String fileName) throws IOException {
        //List<Boolean> multiRiga = new ArrayList<>();
        int numeroRiga = 0;
        int colonna = 0;
        int colonnaCommento = 0;

        String contenuto;
        try ( var reader = new BufferedReader(new FileReader(fileName));) {
            try ( var writer = new StringWriter()) {
                reader.transferTo(writer);
                contenuto = writer.toString();
            }
        }

        StringBuilder commentoAttuale = new StringBuilder();
        List<String> listaCommenti = new ArrayList<>();
        boolean contenutoMultiRiga = false;
        boolean contenutoRiga = false;
        boolean inStringa = false;
        char caratterePrecedente = 0;
        var commenti = new ArrayList<Commento>();

        for (int i = 0; i < contenuto.length(); i++) {
            boolean ultimoCarattere = i == contenuto.length() - 1;
            char carattereAttuale = contenuto.charAt(i);

            if (inStringa) {

                if (carattereAttuale == '"' && caratterePrecedente != '\\') {
                    inStringa = false;
                }

            } else if (contenutoRiga || contenutoMultiRiga) {
                if (contenutoRiga && ((carattereAttuale == '\n' || ultimoCarattere) || ultimoCarattere)) {
                    contenutoRiga = false;

                    commenti.add(new Commento(numeroRiga, colonnaCommento, commentoAttuale.toString()));

                    listaCommenti.add(commentoAttuale.toString());
                    commentoAttuale.setLength(0);
                } else if (contenutoMultiRiga && (carattereAttuale == '/' && caratterePrecedente == '*')) {
                    contenutoMultiRiga = false;
                    commenti.add(new Commento(numeroRiga, colonnaCommento, commentoAttuale.toString()));
                    listaCommenti.add(commentoAttuale.toString());
                    commentoAttuale.setLength(0);
                } else {
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
                colonna = 0;
                numeroRiga++;
            }

        }
        stampaCommenti(commenti);
        //csvCreate(commenti);
    }

    private static void pythonType(String fileName) throws IOException {
        String contenuto;
        try ( var reader = new BufferedReader(new FileReader(fileName));) {
            try ( var writer = new StringWriter()) {
                reader.transferTo(writer);
                contenuto = writer.toString();

                StringBuilder commentoAttuale = new StringBuilder();
                List<String> listaCommenti = new ArrayList<>();
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
                        if (contenutoRiga && ((carattereAttuale == '\n' || ultimoCarattere) || ultimoCarattere)) {
                            contenutoRiga = false;
                            listaCommenti.add(commentoAttuale.toString());
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
                //stampaCommenti(listaCommenti);

            }
        }
    }

   /* public static void csvCreate(List<Commento> commenti) {

        // first create file object for file placed at location
        // specified by filepath
        File file = new File("C:\\Users\\ste_1\\Desktop\\DemoJava.csv");

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // create a List which contains String array
            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[] { "Name", "Class", "Marks" });
            data.add(new String[] { "Aman", "10", "620" });
            data.add(new String[] { "Suraj", "10", "630" });
            writer.writeAll(data);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/


    }