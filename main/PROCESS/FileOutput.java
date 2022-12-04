package main.PROCESS;

import java.io.File;
import java.io.FileWriter;

import main.PCB.PCB;

public class FileOutput {

    public static void write_to_file(PCB pcb) {
        try {
            File file = new File("./Outputs/" + pcb.get_PROCESS_FILE_NAME() + "_output.txt");
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(file,true);
                writer.write("\nGPR : ");
                for (int i = 0; i < pcb.get_GPR().length; i++) {
                    writer.write((pcb.get_GPR()[i]) + " ");
                }
                writer.write("\nSPR : ");
                for (int i = 0; i < pcb.get_SPR().length; i++) {
                    writer.write((pcb.get_SPR()[i]) + " ");
                }
                writer.write("\nMemory : ");
                for (int i = pcb.get_SPR()[7]; i <= pcb.get_SPR()[3]; i++) {
                    writer.write(Convert.convert_int_to_hexa(Byte.toUnsignedInt(Process.memory[i])) + " ");
                }
                writer.write("\n");
                writer.close();
            }
            System.out.println("data added into file for file name : " + pcb.get_PROCESS_FILE_NAME());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
