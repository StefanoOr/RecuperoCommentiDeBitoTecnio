package org.bastord.comment;

import com.opencsv.exceptions.CsvException;
import org.example.Main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Example {


    public static void main(String[] args) throws Exception {
        String fileName, directory;

        //path della cartella
        directory = "C:\\Users\\ste_1\\Desktop\\CartellaFileTestTirocinio";
        //fileName = "C:\\Users\\ste_1\\Desktop\\PhytonDemo.py";
        //fileName = "C:\\Users\\ste_1\\Desktop\\DemoJava.java";


        File directoryPath = new File(directory);

        Example obj = new Example();

        if (directoryPath.exists() && directoryPath.isDirectory()) {
            File a[] = directoryPath.listFiles();//prendo tutti i file della directory



                //File file = new File(a[i].getPath());

                //Comments commenti = CommentProcessor.process(file);
/*

                // Stampa i commenti (per debug)
                System.out.println("\nLeggiamo di commenti di: " + commenti.filename());
                commenti.print();
                */


                // Scrivi i commento su un file CSV
                /*File csvFile = new File(file + ".csv");
                commenti.writeToCSV(new FileWriter(csvFile, StandardCharsets.UTF_8));
                */

                // Leggi i commenti dal file CSV
                /*try (var reader = new FileReader(csvFile)) {
                    commenti = new Comments(csvFile.getName(), Comments.readFromCSV(reader));
                    //System.out.println("Abbiamo letto i commenti di: " + commenti.filename());
                    commenti.print();
                }*/
                obj.printFileNames(a,0,0);
            }

        }

        public void printFileNames (File[] a,int i, int lvl) throws Exception {
            // caso base della ricorsione
            // i == a.length significa che la directory ha
            // niente più file. Quindi, la ricorsione deve fermarsi

            if (i == a.length) {
                return;
            }

            File file = new File(a[i].getPath());

            // schede per fornire il rientro
            // per i file della sottodirectory
            for (int j = 0; j < lvl; j++) {
                System.out.print("\t");
            }
            // verifica se l'oggetto incontrato è un file o meno
            if (a[i].isFile()) {

                Comments commenti = CommentProcessor.process(file);
                // Stampa i commenti (per debug)
                if(commenti!=null) {
                    System.out.println("\nLeggiamo di commenti di: " + commenti.filename());
                    commenti.print();


                    // Scrivi i commento su un file CSV
                    File csvFile = new File(file + ".csv");
                    commenti.writeToCSV(new FileWriter(csvFile, StandardCharsets.UTF_8));

                    try (var reader = new FileReader(csvFile)) {
                        commenti = new Comments(csvFile.getName(), Comments.readFromCSV(reader));
                        //System.out.println("Abbiamo letto i commenti di: " + commenti.filename());
                        commenti.print();
                    }
                }

            }
            // per le sottodirectory
            else if (file.isDirectory()) {
                System.out.println("[" + a[i].getName() + "]");
                // ricorsione per le sottodirectory
                printFileNames(file.listFiles(), 0, lvl + 1);
            }
            // Stampa ricorsivamente i file dalla directory
            // i + 1 significa cercare il file successivo
            printFileNames(a, i + 1, lvl);
        }

}
