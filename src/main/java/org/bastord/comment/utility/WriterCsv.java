package org.bastord.comment.utility;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriterCsv {


    public void AppendToCsv(List<String> comments , String filePath) throws IOException {

        String[] strings = comments.toArray(String[]::new);



        CSVWriter writer = new CSVWriter(new FileWriter(filePath, true));


            writer.writeNext(strings);

            writer.close();

    }




    public  void createCsv(String filePath) {

        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "Comment", "classification", "NameFile" };
            writer.writeNext(header);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }


}
