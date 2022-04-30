package org.bastord.comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;

public abstract class AbstractCommentParser implements CommentParser {


    protected String readAsString(Reader reader) throws IOException {
        try (var writer = new StringWriter()) {
            reader.transferTo(writer);
            return writer.toString();
        }
    }

}
