package org.bastord.comment;

import org.example.Main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Example {


    public static void main(String[] args) throws Exception {
        String fileName;

        fileName = "C:\\Users\\ste_1\\Desktop\\PhytonDemo.py";
        fileName = "C:\\Users\\ste_1\\Desktop\\DemoJava.java";

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
        try (var reader = new FileReader(csvFile)) {
            commenti = new Comments(csvFile.getName(), Comments.readFromCSV(reader));
            System.out.println("Abbiamo letto i commenti di: " + commenti.filename());
            commenti.print();
        }
    }
}
