package org.bastord.comment;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class CommentProcessorTest {
    @Test
    void commentoRiga() {
        String javaComments = """
                package org.bastord.comment;
                                
                import org.junit.jupiter.api.Test;
                // single line comment
                // another single line comment                               
                class CommentProcessorTest {
                    /**
                     * javadoc comment style
                     * that spans
                     * multiple lines
                     */
                    @Test
                    void commentoRiga() {
                        /* single line star comment */
                        String javaComments = ""\"
                               \s
                                ""\";
                        /*
                        multi line
                        star comment
                        */
                        Comments result = CommentProcessor.process(given, "java");
                    }
                                
                }
                """;

        Comments result = CommentProcessor.process(javaComments, "java");
        assertThat(result.comments()).hasSize(5);

        int i=0;
        List<Comment> comments = result.comments();
        check(comments.get(i++), 4, 1, 1, "single line comment");
        check(comments.get(i++), 5, 1, 1, "another single line comment");
        check(comments.get(i++), 7, 5, 5, """
                javadoc comment style
                that spans
                multiple lines""");
        check(comments.get(i++), 14, 9, 1, "single line star comment");
        check(comments.get(i++), 18, 9, 4, """
                                                                                    multi line
                                                                                    star comment""");
    }

    private static void check(Comment comment, int expectedLine, int expectedColumn, int expectedLinesCount, String expectedComment) {
        assertThat(comment.line()).describedAs("line (numeroRiga)").isEqualTo(expectedLine);
        assertThat(comment.column()).describedAs("column (colonna)").isEqualTo(expectedColumn);
        assertThat(comment.linesCount()).describedAs("linesCount (numeroMultiRiga)").isEqualTo(expectedLinesCount);
        assertThat(comment.comment()).isEqualTo(expectedComment);
    }

    @Test
    void test_line_number() {
        String testPiuMinimale = """
                // primo                
                // secondo
                // terzo
                /* quarto */
                // quinto
                """;

        Comments result = CommentProcessor.process(testPiuMinimale, "java");
        assertThat(result.comments()).hasSize(5);
        assertThat(result.comments().get(0).line()).isEqualTo(1);
        assertThat(result.comments().get(1).line()).isEqualTo(2);
        assertThat(result.comments().get(2).line()).isEqualTo(3);
        assertThat(result.comments().get(3).line()).isEqualTo(4);
        assertThat(result.comments().get(4).line()).isEqualTo(5);
    }






}