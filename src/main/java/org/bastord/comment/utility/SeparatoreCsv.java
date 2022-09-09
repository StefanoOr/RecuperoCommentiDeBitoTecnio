package org.bastord.comment.utility;
import com.opencsv.*;
import org.bastord.comment.Comment;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class SeparatoreCsv {

    public static void main(String[] args) throws Exception {

        File file = new File("C:\\Users\\ste_1\\Desktop\\kk.csv");

        FileWriter outputfile = new FileWriter(file);
        CSVWriter writer = new CSVWriter(outputfile,
                '\t',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);



        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build(); // custom separator
        try(CSVReader reader = new CSVReaderBuilder(
                new FileReader("C:\\Users\\ste_1\\Desktop\\merged-file.csv"))
                .withCSVParser(csvParser)   // custom CSV parser
                // skip the first line, header info
                .build()) {
            List<String[]> r = reader.readAll();

            r.forEach(x -> writer.writeNext(x));

            writer.close();


            System.out.println("lettura nuovo file");


            CSVReader reader1 = new CSVReaderBuilder(
                    new FileReader("C:\\Users\\ste_1\\Desktop\\kk.csv"))
                    .withCSVParser(csvParser)   // custom CSV parser
                    // skip the first line, header info
                    .build();
                List<String[]> r1 = reader1.readAll();

            r1.forEach(x -> System.out.println(Arrays.toString(x)));






        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }


}