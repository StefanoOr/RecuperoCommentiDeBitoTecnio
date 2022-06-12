package org.bastord.comment;

import javax.annotation.processing.Filer;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
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
        map.put("ts", cLikeParser);

       // map.put("html", new HtmlCommentParser());

        map.put("sol", new SolidityCommentParser());

        map.put("rs", new RustCommentParser());

        map.put("hs",new HaskellCommentParser());


        parsers = map;
    }


    public static Comments process(File file) throws IOException {
        // Determine the file type
        String fileType = getFileExtension(file);
        if (fileType.isEmpty())
            return  null;
           // throw new IllegalArgumentException("Cannot determine file type for: " + file.getAbsolutePath());

        var parser = parsers.get(fileType);
        if (parser==null) {
            return null;
          //  throw new IllegalArgumentException("No parser available for file: " + file.getAbsolutePath());
        }


        try (var reader = new FileReader(file)) {
            return new Comments(file.getName(), parser.parse(reader));
        }
    }

    public static Comments process(String input, String fileType) {
        var parser = parsers.get(fileType);
        if (parser==null)
            throw new IllegalArgumentException("No parser available for fileType: " + fileType);

        try (var reader = new StringReader(input)) {
            try {
                return new Comments("<String>", parser.parse(reader));
            } catch (IOException e) {
                throw new RuntimeException("Error reading from String", e);
            }
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }


}
