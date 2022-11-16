package main.PROCESS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import main.OS.OS;
import main.PCB.PCB;

public class FileRead {
    static int current_frame = 0;
    static int current_byte = 0;
    static ArrayList<Byte> data_instr = new ArrayList<Byte>();
    static ArrayList<Byte> code_instr = new ArrayList<Byte>();

    public static void listFilesFromFolder(final File folder, Process process) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesFromFolder(fileEntry, process);
            } else {
                System.out.println(fileEntry.getPath());
                set_process_to_queue_from_file(process, fileEntry.getPath());
            }
        }
    }

    static short join(byte num1, byte num2) {
        return (short) ((num1 * 256) + num2);
    }

    static void copy_data_to_memory(ArrayList<Byte> instr, PCB pcb) {
        int instruction_start_index = 50;
        for (int i = instruction_start_index; i < Process.memory.length; i++) {
            if (Convert.convert_int_to_hexa(Process.memory[i]) == "F3") {
                instruction_start_index = ++i;
                break;
            }
        }
        for (int j = instruction_start_index; j < instr.size() + instruction_start_index; j++) {
            Process.memory[j] = instr.get(current_byte);
            current_byte++;
            if (current_byte == 128) {
                current_byte=0;
                pcb.setDATA_PAGE_TABLE(current_frame);
                current_frame++;
            }
        }
    }

    static void copy_code_to_memory(ArrayList<Byte> instr, PCB pcb) {
        int instruction_start_index = 50;
        for (int i = instruction_start_index; i < Process.memory.length; i++) {
            if (Convert.convert_int_to_hexa(Process.memory[i]) == "F3") {
                instruction_start_index = ++i;
                break;
            }
        }
        for (int j = instruction_start_index; j < instr.size() + instruction_start_index; j++) {
            Process.memory[j] = instr.get(current_byte);
            current_byte++;
            if (current_byte == 128) {
                current_byte=0;
                pcb.setDATA_PAGE_TABLE(current_frame);
                current_frame++;
            }
        }
    }

    // The FileRead Method reads the file and returns the result by concatinating
    // the data in the file, seperated by spaces.
    // This is later used to split the result and store the Hex Instructions into an
    // array.
    public static void set_process_to_queue_from_file(Process process, String path) {
        byte byteRead, priority = 0;
        short id = 0, actual_data_size = 0;
        short data_size = 0, counter = 0;

        try {
            InputStream inputStream = new FileInputStream("./" + path);
            while ((byteRead = (byte) inputStream.read()) != -1) {
                counter++;
                if (counter == 1)
                    priority = byteRead;
                else if (counter == 2) {
                    id = join(byteRead, (byte) inputStream.read());
                    counter++;
                } else if (counter == 4) {
                    actual_data_size = join(byteRead, (byte) inputStream.read());
                    counter++;
                } else if (counter > 8) {
                    data_size++;
                    if (data_size <= actual_data_size) {
                        data_instr.add(byteRead);
                    } else {
                        code_instr.add(byteRead);
                    }
                }
            }
            inputStream.close();
            PCB pcb = new PCB(id, priority, counter, path);
            boolean process_added = OS.check_priority_and_addtoQueue(priority, pcb);
            if (process_added) {
                copy_data_to_memory(data_instr, pcb);
                copy_code_to_memory(code_instr, pcb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
