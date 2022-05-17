package org.bastord.comment;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Example {


    public static void main(String[] args) throws Exception {
        String fileName,directoryCsv;

        //path del file
        fileName = "C:\\Users\\ste_1\\Desktop\\CartellaFileTestTirocinio\\provaSito.html";

        //path della cartella
        String directory = "C:\\Users\\ste_1\\Desktop\\bitcoin-master";

        Scanner input = new Scanner(System.in);
        System.out.println("Inserisci \n 1 Directory\n 2 file" );

        int userName = input.nextInt();


    if(userName==1) {


        File directoryPath = new File(directory);
        directoryCsv = directoryPath.getName() + "CSV";
        Example obj = new Example();

        if (directoryPath.exists() && directoryPath.isDirectory()) {
            File a[] = directoryPath.listFiles();//prendo tutti i file della directory

            File FdirectoryCsv = new File(directoryPath.getParent() + File.separator + directoryCsv);//creo la  directory  clone
            boolean D1 = FdirectoryCsv.mkdir();
            if (D1) {
                System.out.println("Directory is created successfully");
            } else {
                System.out.println("Error !");
            }


            obj.printFileNames(a, 0, 0, FdirectoryCsv, directory);
        }

    }else {



            //TODO Test da eliminare , pezzo di ccodice per la lettura di un file singolo



            // Leggi i commenti da un File
            File file = new File(fileName);
            Comments commenti = CommentProcessor.process(file);

            // Stampa i commenti (per debug)
            System.out.println("Abbiamo letto i commenti di: " + commenti.filename());
            commenti.print();

            // Scrivi i commento su un file CSV
            File csvFile = new File(fileName + ".csv");
            commenti.writeToCSV(new FileWriter(csvFile, StandardCharsets.UTF_8));

            // Leggi i commenti dal file CSV
         var reader = new FileReader(csvFile);
                commenti = new Comments(csvFile.getName(), Comments.readFromCSV(reader));
                System.out.println("Abbiamo letto i commenti di: " + commenti.filename());
                commenti.print();


            }
        }






        public void printFileNames (File[] a,int i, int lvl,File FdirectoryCsv,String directory) throws Exception {
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
            if (a[i].isFile() ) {

                Comments commenti = CommentProcessor.process(file);
                // Stampa i commenti (per debug)
                if(commenti!=null) {
                    System.out.println("\nLeggiamo di commenti di: " + commenti.filename());
                  //  commenti.print();


                    // Scrivi i commento su un file CSV
                    File cvsTest = cambiaPercorsoFile(a[i],FdirectoryCsv.getName(),new File(directory),".csv");
                    commenti.writeToCSV(new FileWriter(cvsTest, StandardCharsets.UTF_8));

                  var reader = new FileReader(cvsTest);
                       if(a[i].getName().equals("feature_block.py")){
                           System.out.println("test");
                       }
                        commenti = new Comments(cvsTest.getName(), Comments.readFromCSV(reader));
                        commenti.print();

                    }




            }

            // per le sottodirectory
            else if (file.isDirectory()) {
                System.out.println("[" + a[i].getName() + "]");
                // ricorsione per le sottodirectory

               File directoryNew= cambiaPercorsoFile(a[i],FdirectoryCsv.getName(),new File(directory),"");

                boolean D1 = directoryNew.mkdir();
                if(D1){
                    System.out.println("Directory is created successfully");
                }else{
                    System.out.println("Error !");
                }

                printFileNames(file.listFiles(), 0, lvl + 1,FdirectoryCsv,directory);
            }
            // Stampa ricorsivamente i file dalla directory
            // i + 1 significa cercare il file successivo
            printFileNames(a, i + 1, lvl,FdirectoryCsv,directory);
        }


    /**
     * Funzione che permette di cambiare il percorso di un file gia esistente
     * @param file
     * @param cartellaDiUscita
     * @param cartella
     * @return
     */
    public static File cambiaPercorsoFile(File file, String cartellaDiUscita, File cartella,String csv) {


        String primaParte = cartella.getParent()+ File.separator;

        int i = cartella.getPath().length();
        String  secondaParte=file.getPath().substring(i);

        return new File(primaParte + cartellaDiUscita + secondaParte+csv);

    }



}
