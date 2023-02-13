package org.bastord.comment.utility;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderCsv {


    public List<String> lettura(File file) throws FileNotFoundException {
        List<String> comment = new ArrayList<>();
        String a = file.getName();

        System.out.println("\n"+ a);
        if(file.getName() == "Avalanche-avalanche-smart-contract-quickstart-main-contracts-MockContract.sol.csv"){


            System.out.println("debug");
        }

        Reader reader = new FileReader(file);
        try {
            CSVParser parser = new CSVParserBuilder().withSeparator(',').build();

            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(parser).build();
            List<String[]> allData = csvReader.readAll();
            // print Data
            for (String[] row : allData) {
                    comment.add(row[3]);
                }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return comment;


    }
}