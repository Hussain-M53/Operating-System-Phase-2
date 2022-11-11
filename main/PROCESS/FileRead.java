package main.PROCESS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileRead {
    public static void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getPath());
                read_file("./" + fileEntry.getPath());
            }
        }
    }

    // The FileRead Method reads the file and returns the result by concatinating
    // the data in the file, seperated by spaces.
    // This is later used to split the result and store the Hex Instructions into an
    // array.
    public static void read_file(String path) {
        // String result = "";
        // try {
        // File myObj = new File(path);
        // Scanner myReader = new Scanner(myObj);
        // while (myReader.hasNextLine()) {
        // String data = myReader.next();
        // result = result + data + " ";
        // }
        // myReader.close();
        // } catch (FileNotFoundException e) {
        // System.out.println("An error occurred.");
        // e.printStackTrace();
        // }
        // return result;
        int byteRead;
        try {
            InputStream inputStream = new FileInputStream(path);
            while ((byteRead = inputStream.read())!= -1) {
                System.out.print(Convert.convert_int_to_hexa(byteRead) + " ");
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
