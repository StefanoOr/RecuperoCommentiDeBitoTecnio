package org.bastord.comment;

import javax.annotation.processing.Filer;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentProcessor {

    private static final Map<String, CommentParser> parsers;
    static {
        var map = new HashMap<String, CommentParser>();
        map.put("py", new PythonCommentParser());

        var cLikeParser = new CLikeCommentParser();
        map.put("java", cLikeParser);
        map.put("js", cLikeParser);
        map.put("c", cLikeParser);
        map.put("cpp", cLikeParser);

        parsers = map;
    }


    public static Comments process(File file) throws IOException {
        // Determine the file type
        String fileType = getFileExtension(file);
        if (fileType.isEmpty())
            throw new IllegalArgumentException("Cannot determine file type for: " + file.getAbsolutePath());

        var parser = parsers.get(fileType);
        if (parser==null)
            throw new IllegalArgumentException("No parser available for file: " + file.getAbsolutePath());

        try (var reader = new FileReader(file)) {
            return new Comments(file.getName(), parser.parse(reader));
        }
    }



    private static String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }


}
