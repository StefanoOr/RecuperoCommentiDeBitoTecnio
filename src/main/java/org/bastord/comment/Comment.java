package org.bastord.comment;

public record Comment(int line, int column, int linesCount, String comment) {

    public Comment {
        comment = comment.trim();
    }
}
