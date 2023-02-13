package org.bastord.comment.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Extractor {
//estrae tutti i file  da una  directory e sotto directory
    public  void estrazioneFile(Path source ,Path target) throws IOException {

        File createDirectory = new File(target.toUri());
        if(createDirectory.mkdir()){
            System.out.println("cartella creata con successo");
        }
        copyFilesTo(source, createDirectory.toPath());
    }

    private static void copyFilesTo(Path source, Path target) throws IOException {
        for (Path path : Files.list(source).toList()) {
            if (Files.isDirectory(path)) {
                copyFilesTo(path, target);
            }
            else {

                    System.out.printf("Copia da <%s> a <%s>%n", path, target.resolve(path.toString()));
                    Files.copy(path, target.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);



            }
        }
    }

    public static void main(String[] args) throws Exception {
        String directory = "C:\\Users\\ste_1\\Desktop\\TirocinioCSV";
        File directoryPath = new File(directory);

        Extractor estrattore = new Extractor();
        for (File file : directoryPath.listFiles()) {


            var source = Paths.get("C:\\Users\\ste_1\\Desktop\\TirocinioCSV\\" + file.getName());
            var target = Paths.get("C:\\Users\\ste_1\\Desktop\\Extractor\\" + file.getName());
            estrattore.estrazioneFile(source, target);

        }
    }
}



