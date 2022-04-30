package org.bastord.comment;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public interface CommentParser {

    List<Comment> parse(Reader reader) throws IOException;

}
