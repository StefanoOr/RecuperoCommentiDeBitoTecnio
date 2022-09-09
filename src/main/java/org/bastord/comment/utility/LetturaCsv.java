package org.bastord.comment.utility;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LetturaCsv {





    public List<String> lettura(String path){
        List<String> comment = new ArrayList<>();
        try {

            CSVReader reader = new CSVReader(new FileReader(path));

            String [] nextLine;

            reader.getSkipLines();

            while ((nextLine = reader.readNext()) != null ) {
                try {
                    comment.add(nextLine[3]);
                }catch (Exception exception){
                    System.out.println(exception);
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return comment;

    }
}