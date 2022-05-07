package org.bastord.comment;

import org.exampleApprendimento.CreateDirectory;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.*;

public class ClonaCartelleTest {

    @Test
    void test(){
        File file = new File("C:\\cartella\\file.java");
        String cartellaDiUscita="clone";

        File risultato = CreateDirectory.metodo(file, cartellaDiUscita, "cartella");
        File clone= new File("C:\\clone\\file.java");

        assertThat(risultato).isEqualTo(clone);
    }

    @Test
    void test2(){
        File file = new File("C:\\path\\a\\cartella\\file.java");
        String cartellaDiUscita="clone";

        File risultato = CreateDirectory.metodo(file, cartellaDiUscita,"cartella" );
        File clone= new File("C:\\path\\a\\clone\\file.java");

        assertThat(risultato).isEqualTo(clone);
    }

    @Test
    void test3(){
        File file = new File("C:\\cartella\\sottocartella\\file.java");
        String cartellaDiUscita="clone";

        File risultato = CreateDirectory.metodo(file, cartellaDiUscita,"cartella" );
        File clone= new File("C:\\clone\\sottocartella\\file.java");

        assertThat(risultato).isEqualTo(clone);
    }


    @Test
    void test4(){
        File file = new File("C:\\path\\a\\cartella\\sottocartella\\file.java");
        String cartellaDiUscita="clone";

        File risultato = CreateDirectory.metodo(file, cartellaDiUscita,"cartella" );
        File clone= new File("C:\\path\\a\\clone\\sottocartella\\file.java");

        assertThat(risultato).isEqualTo(clone);
    }

    @Test
    void test5(){
        File file = new File("C:\\cartella\\con\\path\\a\\cartella\\sottocartella\\file.java");
        String cartellaDiUscita="clone";

        File nuovo= new File("C:\\cartella\\con\\path\\a\\cartella");

        File risultato = CreateDirectory.metodo(file, cartellaDiUscita,nuovo );
        File clone= new File("C:\\cartella\\con\\path\\a\\clone\\sottocartella\\file.java");

        assertThat(risultato).isEqualTo(clone);
    }

    private void copia(File a, File b) {}

    @Test
    void test6(){
        File file = new File("C:\\cartella\\con\\path\\a\\cartella\\sottocartella\\file.java");
        String cartellaDiUscita="clone";

        File risultato = CreateDirectory.metodo(file, cartellaDiUscita,"cartella" );
        File clone= new File("C:\\clone\\con\\path\\a\\cartella\\sottocartella\\file.java");

        assertThat(risultato).isEqualTo(clone);
    }
}
