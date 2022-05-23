package org.exampleApprendimento;


import org.exampleApprendimento.ParserHtml;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.parser.Parser;

import static org.assertj.core.api.Assertions.assertThatCode;

class ParserHtmlTest {

    @Test
    void testa() {
        Document doc = Jsoup.parse("""
                <script>
                <!--
                var y = "</script>";
                var x = "<!-- ";
                hello world
                </script>
                """);

        System.out.println(doc.selectFirst("script").html());
    }

    @Test
    void prova() {
        String input = """
                <xyz>ciao<b>grassetto</b>

                <!-- questo è un 
                
                
                commento -->
                             
                <br/>altro testo</xyz>
                                
                <script>
                  prova <!-- con un commento -->
                  questo //commento 
                  
                  yoyo */ a a */
                  
                  /* prova commento multi linea 
                  aaahahaha*/
                </script>
                """;
        System.out.println(input);
        assertThatCode(() -> ParserHtml.parse(input)).doesNotThrowAnyException();
    }

    @Test
    void provaTokenizer() {
        ParserHtml.Tokenizer tokenizer = new ParserHtml.Tokenizer("<html     attributo='valore' attributo2\n" +
                "attributo3=\"123\" attributi4=124> ");
        assertThatCode(() -> tokenizer.next()).doesNotThrowAnyException();
    }
}
