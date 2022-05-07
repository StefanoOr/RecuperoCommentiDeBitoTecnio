package org.exampleApprendimento;

import java.io.File;
import java.util.Scanner;
public class CreateDirectory {
    public static void main(String args[]) {
        System.out.println("Path of Directory? ");
        Scanner obj = new Scanner(System.in);
        String path = obj.next();
        System.out.println("Directory Name? ");
        path = path+obj.next();
        File D = new File(path);
        boolean D1 = D.mkdir();
        if(D1){
            System.out.println("Directory is created successfully");
        }else{
            System.out.println("Error !");
        }
    }

    public static File metodo(File file, String cartellaDiUscita, String nome) {

       String a =file.getAbsolutePath();
a= a.replaceFirst(nome,cartellaDiUscita);

        File nuovo = new File(a);

        return nuovo;
    }

    public static File metodo(File file, String cartellaDiUscita, File cartella) {

        System.out.println("File: "+ file);
        System.out.println("cartella: "+ cartella);
        System.out.println("cartellaDiUscita: "+ cartellaDiUscita);

//        File: C:\cartella\con\path\a\cartella\sottocartella\file.java
//        cartella: C:\cartella\con\path\a\cartella
//        cartellaDiUscita: clone

        String primaParte = cartella.getParent()+ File.separator;
        String secondaParte = null;//\sottocartella\file.java


        int i = cartella.getPath().length();
        secondaParte=file.getPath().substring(i);
        //https://forum.ubuntu-it.org/viewtopic.php?t=174506

        return new File(primaParte + cartellaDiUscita + secondaParte);

//        System.out.println(cartella.getPath());

//        File nuovo = new File(a);
//        return nuovo;
    }

}