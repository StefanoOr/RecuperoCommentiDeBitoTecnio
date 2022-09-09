package org.bastord.comment.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Scanner;

public class MergeCsv {

    public static void main(String[] args) throws Exception {
        String directory = "C:\\Users\\ste_1\\Desktop\\Extractor";


        File directoryPath = new File(directory);

        if (directoryPath.exists() && directoryPath.isDirectory()) {
            File a[] = directoryPath.listFiles();//prendo tutti i file della directory

            for(int i =0 ;i<a.length ;i++){
                System.out.println(a[i]);
            }

    }



}}
