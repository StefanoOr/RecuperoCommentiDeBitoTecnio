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

    public static void main(String[] args) throws IOException {
        var source = Paths.get("C:\\Users\\ste_1\\Desktop\\TirocinioCSV\\Terra");
        var target = Paths.get("C:\\Users\\ste_1\\Desktop\\Extractor\\");
        copyFilesTo(source, target);
    }

    private static void copyFilesTo(Path source, Path target) throws IOException {
        for (Path path : Files.list(source).toList()) {
            if (Files.isDirectory(path)) {
                copyFilesTo(path, target);
            }
            else {

                    System.out.printf("Copia da <%s> a <%s>%n", path, target.resolve(path.getFileName()));
                    Files.copy(path, target.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);

            }
        }
    }

}



