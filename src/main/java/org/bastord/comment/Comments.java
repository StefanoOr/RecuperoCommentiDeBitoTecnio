package org.bastord.comment;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.example.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Comments(String filename, List<Comment> comments) {

    public void print() {
        System.out.println("-".repeat(60) + "\n");
        for (Comment commento : comments) {
            System.out.format("%d: %d: %d: %s\n", commento.line(), commento.column(), commento.linesCount(), commento.comment()/*.trim().replaceAll(" " + " ", "")*/);
        }
    }

    private static final String[] CSV_HEADER = {"Riga", "Colonna", "NumeroDiRighe", "Commento"};

    public void writeToCSV(Writer writer) throws IOException {
        try (var csv = new CSVWriter(writer)) {

            // Stampa l'header
            csv.writeNext(CSV_HEADER);

            for (Comment commento : comments) {
                csv.writeNext(new String[]{"" + commento.line(),
                        "" + commento.column(),
                        "" + commento.linesCount(),
                        commento.comment()/*.trim().replaceAll(" " + " ", "")*/});
            }

        }
    }

    public static List<Comment> readFromCSV(Reader reader) throws IOException, CsvException {
        try (var csvReader = new CSVReader(reader)) {
            List<Comment> comments = new ArrayList<>();
            csvReader.readNext(); // ignore the header
            for (String[] riga : csvReader.readAll()) {
                comments.add(new Comment(
                        Integer.parseInt(riga[0]), // line
                        Integer.parseInt(riga[1]), // column
                        Integer.parseInt(riga[2]), // linesCount
                        riga[3]) // comment
                );
            }
            return comments;
        }
    }
}
