package main.PROCESS;

import java.io.File;

public class Main {
     //The program starts execution from the main method
    //First the contents of the file are read and stored in the readFile variable as a String
    //This string is split and stored in the hex_instr array.
    //The hex_instr array is converted into its equivalent byte and stored in the byte_instr array
    //The byte_instr array is copied to the memory
    //And then the instructions are executed one by one.

    public static void main(String[] args) {
        final File folder = new File("./Files");
        FileRead.listFilesForFolder(folder);
        // Process process = new Process();
        // String readFile = FileRead.read_file();
        // String[] hex_instr = readFile.split(" ");

        // byte[] byte_instr = Convert.convert_array_to_decimal(hex_instr);

        // process.copy_instructions_to_memory(byte_instr);
        // Display.print_mem(process);
        // process.execute_instr();
        // Display.print_mem(process);
    }
}
