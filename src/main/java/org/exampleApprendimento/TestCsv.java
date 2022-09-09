package org.exampleApprendimento;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestCsv {

    public static void main(String... args) throws IOException {
        //leggi la stringa e trasformare le righe in csv
        ArrayList<String> rows = new ArrayList<>();

       String linea="""
                si ,va
                  
                avanti  
                forza!""".lines().collect(Collectors.joining(","));

        System.out.println(linea);

        System.out.println(linea.lines().spliterator());


        FileWriter csvWriter = new FileWriter("C:\\Users\\ste_1\\Desktop\\nuovo.csv");


        for (String riga : rows) {
            csvWriter.append(String.join(",", riga));
            csvWriter.append("\n");
            System.out.println(riga);
        }

        csvWriter.flush();
        csvWriter.close();



    }


}
