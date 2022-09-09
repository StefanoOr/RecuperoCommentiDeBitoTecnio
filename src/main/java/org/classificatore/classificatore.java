package org.classificatore;

import java.io.*;
import java.util.List;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.objectbank.ObjectBank;
import org.bastord.comment.utility.LetturaCsv;

class classificatore {
    //chiedere a maldonado il prop via email  fatto
    //addestrare il classificatore e salvare il file (addestrare su tutto il dataset completo) fatto
    //caricare  il file gz (classificatore adddestrato)  nel script java
    //elimima i segni di punteggiatura per ogni commento tranne ? e !  fatto
    //avviare la classificazione commento per commento fatto
    //inserire il commento e la classificazione nel file csv merge || colonna A commento , B classificazione , C nome del file csv originale

    public static void main(String[] args) throws Exception {

        String path = "C:\\Users\\ste_1\\Desktop\\Extractor"; //directory of all file csv project
        File fObj = new File(path);


        File a[] = fObj.listFiles();

        ColumnDataClassifier cdc = new ColumnDataClassifier("C:\\Users\\ste_1\\Desktop\\prop1.prop");
        Classifier<String, String> cl = cdc
                .makeClassifier(cdc.readTrainingExamples("C:\\Users\\ste_1\\Desktop\\Tirocinio\\Classificatore\\td_trainset.csv"));


        // inserisci qui il file gz  ColumnDataClassifier cdc = ColumnDataClassifier.getClassifier(/// inserisci qui gz)

        //ColumnDataClassifier cdc = ColumnDataClassifier.getClassifier("C:\\Users\\ste_1\\Desktop\\classificatore.ser");

        LetturaCsv csv = new LetturaCsv();
        int nFile=0;
        for (File file : a ) {
            nFile++;

            List<String> commenti =csv.lettura(file.getPath());
            int riga =0;
           for (String line : commenti) {
               System.out.println(nFile+" "+ file.getName());
                // instead of the method in the line below, if you have the individual elements
                if ( !line.isEmpty()) {
                    //line = removePunctuations(line);
                    System.out.println(line);
                    riga++;
                    System.out.println(riga);
                    //Datum<String, String> d = cdc.makeDatumFromLine(line);
                    //System.out.println(line + "  ==>  " + cl.classOf(d));
                }
            }//   w   ww  .   d e   m  o   2  s  .  c  o m


        }

    }


    public static String removePunctuations(String source) {
        return source.replaceAll("[\"#$%&'()*+,-./:;<=>@\\[\\]^_`{|}~]", " ");
    }


}

