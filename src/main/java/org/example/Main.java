package org.example;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author ste_1
 */
public class Main {

    public static final char CSV_SEPARATOR = ';'; // it could be a comma or a semi colon

    enum TipoCommento {
        RIGA,
        MULTI_RIGA,
    }

    public record Commento(int riga, int colonna, int righeCommento, String commento) {

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

            System.out.format("%d: %d: %d: %s\n", string.riga, string.colonna, string.righeCommento, string.commento.trim().replaceAll(" " + " ", ""));
        }

    }

    public static void cLike(Reader reader) throws IOException,CsvException {
        //List<Boolean> multiRiga = new ArrayList<>();
        int numeroRiga = 0;
        int colonna = 0;
        int colonnaCommento = 0;
        int numeroMultiRiga = 1;

        String contenuto;
        try (var r = new BufferedReader(reader);) {
            try (var writer = new StringWriter()) {
                r.transferTo(writer);
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
        var rimuoviAsterischi = false;

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
                    commenti.add(new Commento(numeroRiga, colonnaCommento, numeroMultiRiga, commentoAttuale.toString()));
                    numeroMultiRiga = 0;

                    listaCommenti.add(commentoAttuale.toString());
                    commentoAttuale.setLength(0);
                } else if (contenutoMultiRiga && ((carattereAttuale == '/' && caratterePrecedente == '*') || ultimoCarattere)) {
                    contenutoMultiRiga = false;
                    numeroMultiRiga++;

                    if (carattereAttuale=='/') {
                        commentoAttuale.setLength(commentoAttuale.length()-1);
                    }

                    //COMMENTO MULTI RIGA
                    String commento = commentoAttuale.toString();

                    //Rasael: rimuovi asterischi dai commenti javadoc
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

                    commenti.add(new Commento(numeroRiga, colonnaCommento, numeroMultiRiga, commento));
                    numeroMultiRiga = 0;
                    rimuoviAsterischi = false; // disabilita il flag per i prossimi commenti

                    listaCommenti.add(commentoAttuale.toString());
                    commentoAttuale.setLength(0);
                } else {
                    // se siamo in un commento multilinea ed il primo carattere è un asterisco, vuol dire
                    //che è cominciato con /** e lo consideration un commento javadoc
                    if (commentoAttuale.isEmpty() && contenutoMultiRiga && carattereAttuale=='*') {
                        rimuoviAsterischi = true;
                    }
                    commentoAttuale.append(carattereAttuale);
                }

            } else if (carattereAttuale == '/' && caratterePrecedente == '/') {
                colonnaCommento = colonna;

                contenutoRiga = true;
                numeroMultiRiga++;
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
        stampaCommenti(commenti);
        csvCreate(commenti);
    }

    private static void pythonType(Reader reader) throws IOException, CsvException {
        String contenuto;
        try (var r = new BufferedReader(reader);) {
            try (var writer = new StringWriter()) {
                r.transferTo(writer);
                contenuto = writer.toString();
            }
        }

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

    public static void csvCreate(List<Commento> commenti) throws IOException, CsvException {

        /* first create file object for file placed at location
        // specified by filepath
        //File file = new File("C:\\Users\\ste_1\\Desktop\\DemoJava.csv");
        String file = "C:\\Users\\ste_1\\Desktop\\DemoJava.csv";


        CSVWriter writer = new CSVWriter(new FileWriter(file, StandardCharsets.UTF_8));
        for (Commento commento : commenti) {
            writer.writeNext(new String[]{commento.riga + "" + commento.colonna + "" + commento.commento});
        }

        writer.close();

*/


        // Prova
        File tmpFile = new File("C:\\Users\\ste_1\\Desktop\\Prova.csv");

        try (var csv = new CSVWriter(new FileWriter(tmpFile, StandardCharsets.UTF_8))) {

            csv.writeNext(new String[]{"Riga", "Colonna", "NumeroDiRighe", "Commento"});

            for (Commento commento : commenti) {


                csv.writeNext(new String[]{"" + commento.riga,
                        "" + commento.colonna,
                        "" + commento.righeCommento,
                        commento.commento.trim().replaceAll(" " + " ", "")});
            }

        }


        try (var csvReader = new CSVReader(new FileReader(tmpFile, StandardCharsets.UTF_8))) {
            for (String[] strings : csvReader.readAll()) {
                System.out.println(Arrays.toString(strings));
            }
        }


    }

    public static void main(String[] args) throws IOException,CsvException {

        String fileName = "C:\\Users\\ste_1\\Desktop\\DemoJava.java";
        System.out.println("Nome del file=" + fileName + ". Tipo di file= " + getFileExtension(fileName));

        String typeOfFile = getFileExtension(fileName);

        Reader reader = new FileReader(fileName);

        boolean esempio = false;
        if (esempio) {
            reader = new StringReader("""
                    /* su una riga */
                    /** su una riga */
                    /**
                             * java doc
                                 questa riga inizia con degli spazi che non sono rimossi
                             *     questa riga inizia con degli spazi che sono rimossi
                     */
                     /*
                     qualcosa
                     */
                     /* riga che finisce con commento*/""");
            typeOfFile = "java";
        }

        if (typeOfFile.equals("c") || typeOfFile.equals("java")) {
            cLike(reader);
        } else if (typeOfFile.equals("py")) {
            pythonType(reader);
        }


    }


}