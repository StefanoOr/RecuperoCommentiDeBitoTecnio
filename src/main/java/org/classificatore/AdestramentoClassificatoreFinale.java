package org.classificatore;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;

import java.io.IOException;

public class AdestramentoClassificatoreFinale {



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // TODO Auto-generated method stub
        // Initialize the tagger
        //String options = "-loadClassifier C:\\Users\\ste_1\\Desktop\\classificatore.ser.gz";
        ColumnDataClassifier classifier = new ColumnDataClassifier("C:\\Users\\ste_1\\Desktop\\prop1.prop");
        Classifier<String,String> cdc = classifier.makeClassifier(classifier.readTrainingExamples("C:\\Users\\ste_1\\Desktop\\td_dataset.csv"));



        classifier.serializeClassifier("C:\\Users\\ste_1\\Desktop\\classificatore2.ser");
        //ColumnDataClassifier classifer = ColumnDataClassifier.getClassifier("C:\\Users\\ste_1\\Desktop\\classificatore2.ser");




    }
}