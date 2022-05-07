package org.exampleApprendimento;// import statement
import java.io.File;
public class DisplayFileExample {
    public void printFileNames(File[] a, int i, int lvl)
    {
// base case of the recursion  
// i == a.length means the directory has   
// no more files. Hence, the recursion has to stop  
        if(i == a.length)
        {
            return;
        }
// tabs for providing the indentation  
// for the files of sub-directory  
        for (int j = 0; j < lvl; j++)
        {
            System.out.print("\t");
        }
// checking if the encountered object is a file or not  
        if(a[i].isFile())
        {
            System.out.println(a[i].getName());
        }
// for sub-directories  
        else if(a[i].isDirectory())
        {
            System.out.println("[" + a[i].getName() + "]");
// recursion for sub-directories  
            printFileNames(a[i].listFiles(), 0, lvl + 1);
        }
// recursively printing files from the directory  
// i + 1 means look for the next file  
        printFileNames(a, i + 1, lvl);
    }
    // Main Method
    public static void main(String[] argvs)
    {
// Providing the full path for the directory  
        String path = "C:\\Users\\ste_1\\Desktop\\bitcoin-master" +
                "";
// creating a file object  
        File fObj = new File(path);
// creating on object of the class DisplayFileExample1  
        DisplayFileExample obj = new DisplayFileExample();
        if(fObj.exists() && fObj.isDirectory())
        {
// array for the files of the directory pointed by fObj  
            File a[] = fObj.listFiles();
// display statements  
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println("Displaying Files from the directory: " + fObj);
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
// Calling the method  
            obj.printFileNames(a, 0, 0);
        }
    }
}  