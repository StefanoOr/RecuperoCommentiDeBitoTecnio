package org.example;
// import statement
import java.io.File;
public class DisplayFileExample
{
    public void printFileNames(File[] a, int i, int lvl)
    {
// caso base della ricorsione
// i == a.length significa che la directory ha
// niente più file. Quindi, la ricorsione deve fermarsi
        if(i == a.length)
        {
            return;
        }
// verifica se l'oggetto incontrato è un file o meno
        if(a[i].isFile())
        {
            System.out.println(a[i].getName());
        }
// Stampa ricorsivamente i file dalla directory
// i + 1 significa cercare il file successivo
        printFileNames(a, i + 1, lvl);
    }
    // Metodo principale
    public static void main(String[] argvs)
    {
// Fornendo il percorso completo per la directory
        String path = "C:\\Users\\ste_1\\Desktop\\CartellaFileTestTirocinio";
// creazione di un oggetto file
        File fObj = new File(path);
// creazione su oggetto della classe DisplayFileExample
        DisplayFileExample obj = new DisplayFileExample();
        if(fObj.exists() && fObj.isDirectory())
        {
// array per i file della directory puntata da fObj
            File a[] = fObj.listFiles();
// visualizza le istruzioni
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println("Displaying Files from the directory : " + fObj);
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
// Chiamare il metodo
            obj.printFileNames(a, 0, 0);
        }
    }
}