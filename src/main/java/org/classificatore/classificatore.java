package org.classificatore;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.ling.Datum;
import org.bastord.comment.utility.ReaderCsv;
import org.bastord.comment.utility.WriterCsv;

class classificatore {
    //chiedere a maldonado il prop via email  fatto
    //addestrare il classificatore e salvare il file (addestrare su tutto il dataset completo) fatto
    //caricare  il file gz (classificatore adddestrato)  nel script java
    //elimima i segni di punteggiatura per ogni commento tranne ? e !  fatto
    //avviare la classificazione commento per commento fatto
    //inserire il commento e la classificazione nel file csv merge || colonna A commento , B classificazione , C nome del file csv originale

    public static void main(String[] args) throws Exception {

        String path = "C:\\Users\\ste_1\\Desktop\\Extractor"; //directory of all file csv project
        String pathCsv="C:\\Users\\ste_1\\Desktop\\Bitcoin.csv";
        File fObj = new File(path);
        int numerOfFile=0;

        File[] listOfFile = fObj.listFiles();

        //ColumnDataClassifier cdc = new ColumnDataClassifier("C:\\Users\\ste_1\\Desktop\\prop1.prop");
        //Classifier<String, String> cl = cdc
              //  .makeClassifier(cdc.readTrainingExamples("C:\\Users\\ste_1\\Desktop\\Tirocinio\\Classificatore\\td_trainset.csv"));


        // inserisci qui il file gz  ColumnDataClassifier cdc = ColumnDataClassifier.getClassifier(/// inserisci qui gz)

        //ColumnDataClassifier cdc =  ColumnDataClassifier.getClassifier("C:\\Users\\ste_1\\Desktop\\classificatore2.ser");
        ColumnDataClassifier classifier = new ColumnDataClassifier("C:\\Users\\ste_1\\Desktop\\prop1.prop");
        Classifier<String,String> cdc = classifier.makeClassifier(classifier.readTrainingExamples("C:\\Users\\ste_1\\Desktop\\td_dataset.csv"));

        ReaderCsv csv = new ReaderCsv();

        WriterCsv writerCsv = new WriterCsv();

        writerCsv.createCsv(pathCsv);

        for (File file : listOfFile ) {
            numerOfFile++;
            List<String> commenti =csv.lettura(file);

           for (String line : commenti) {

                // instead of the method in the line below, if you have the individual elements

                if ( !line.isEmpty()) {
                    CsvClassificazione recordCsv = new CsvClassificazione();

                    recordCsv.setNomeFile(file.getName());
                    line = removePunctuations(line);

                    recordCsv.setCommento(line);


                    Datum<String, String> d = classifier.makeDatumFromLine(line);

                    //inserisce nella colonna del file csv la classificazione
                    recordCsv.setClassificazione(cdc.classOf(d));

                    writerCsv.AppendToCsv(recordCsv,pathCsv);
                    System.out.println(line + "  ==>  " + cdc.classOf(d));
                }


            }





        }
        System.out.println("Number of file read : "+ numerOfFile);

    }


    public static String removePunctuations(String source) {
        return source.replaceAll("[\"#$%&'()*+,-./:;<=>@\\[\\]^_`{|}~\n]", " ");
    }


}

