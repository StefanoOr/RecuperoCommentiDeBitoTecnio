package org.exampleApprendimento;

import org.bastord.comment.AbstractCommentParser;
import org.bastord.comment.Comment;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import java.io.*;
import java.util.List;

// example solution to remove comments from HTML.
// re: http://groups.google.com/group/jsoup/browse_thread/thread/419b5ac4be88b086

public class RemoveComments  {

    public static void main(String... args) throws FileNotFoundException  {
       // String h = "<div><!-- no --><p>Hello<!-- gone --></div>";
        String fileName="C:\\Users\\ste_1\\Desktop\\CartellaFileTestTirocinio\\provaSito.html";
        File file = new File(fileName);
        Reader reader = new FileReader( file);

        try {
            String contenuto = readAsString(reader);
            Document doc = Jsoup.parse(contenuto);

            removeComments(doc);
           // print(doc.html());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static void removeComments(Node node) {
        // as we are removing child nodes while iterating, we cannot use a normal foreach over children,
        // or will get a concurrent list modification error.
        int i = 0;


        while (i < node.childNodes().size()) {
            Node child = node.childNode(i);

            if (child.nodeName().equals("#comment")) {

                System.out.println(child.toString()  );
                child.remove();

            } else {

                removeComments(child);
                i++;
            }
        }
    }



    private static void print(String msg) {
        System.out.println(msg);
    }


   public static String readAsString(Reader reader) throws IOException {
        try (var writer = new StringWriter()) {
            reader.transferTo(writer);
            return writer.toString();
        }
    }
}