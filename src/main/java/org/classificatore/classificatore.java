package org.classificatore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.classify.LinearClassifier;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.objectbank.ObjectBank;
import edu.stanford.nlp.util.ErasureUtils;

class classificatore {

    public static void main(String[] args) throws Exception {
        ColumnDataClassifier cdc = new ColumnDataClassifier("C:\\Users\\ste_1\\Desktop\\Tirocinio\\Classificatore\\20news1.prop");
        Classifier<String, String> cl = cdc
                .makeClassifier(cdc.readTrainingExamples("C:\\Users\\ste_1\\Desktop\\Tirocinio\\Classificatore\\td_trainset.csv"));
        for (String line : ObjectBank.getLineIterator("C:\\Users\\ste_1\\Desktop\\Tirocinio\\Classificatore\\td_testset.csv", "utf-8")) {
            // instead of the method in the line below, if you have the individual elements
            // already you can use cdc.makeDatumFromStrings(String[])
            Datum<String, String> d = cdc.makeDatumFromLine(line);
            System.out.println(line + "  ==>  " + cl.classOf(d));
        }//   w   ww  .   d e   m  o   2  s  .  c  o m

        //    demonstrateSerialization();
    }

    public static void demonstrateSerialization() throws IOException, ClassNotFoundException {
        System.out.println("Demonstrating working with a serialized classifier");
        ColumnDataClassifier cdc = new ColumnDataClassifier("C:\\Users\\ste_1\\Desktop\\Tirocinio\\Classificatore\\cheese2007.prop");
        Classifier<String, String> cl = cdc
                .makeClassifier(cdc.readTrainingExamples("C:\\Users\\ste_1\\Desktop\\Tirocinio\\Classificatore\\td_testset.csv"));

        // Exhibit serialization and deserialization working. Serialized to bytes in memory for simplicity
        System.out.println();
        System.out.println();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(cl);
        oos.close();
        byte[] object = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(object);
        ObjectInputStream ois = new ObjectInputStream(bais);
        LinearClassifier<String, String> lc = ErasureUtils.uncheckedCast(ois.readObject());
        ois.close();
        ColumnDataClassifier cdc2 = new ColumnDataClassifier("C:\\Users\\ste_1\\Desktop\\Tirocinio\\Classificatore\\cheese2007.prop");

        // We compare the output of the deserialized classifier lc versus the original one cl
        // For both we use a ColumnDataClassifier to convert text lines to examples
        for (String line : ObjectBank.getLineIterator("C:\\Users\\ste_1\\Desktop\\Tirocinio\\Classificatore\\td_testset.csv", "utf-8")) {
            Datum<String, String> d = cdc.makeDatumFromLine(line);
            Datum<String, String> d2 = cdc2.makeDatumFromLine(line);
            System.out.println(line + "  =origi=>  " + cl.classOf(d));
            System.out.println(line + "  =deser=>  " + lc.classOf(d2));
        }
    }



}

