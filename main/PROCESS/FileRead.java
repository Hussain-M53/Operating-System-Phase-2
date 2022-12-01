package main.PROCESS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import main.OS.OS;
import main.PCB.PCB;

public class FileRead {
    public static int current_frame = 0;
    public static int memory_index = 0;
    static int bytes_in_frame = 0;
    final static int frame_size = 128;
    static ArrayList<Byte> data_instr = new ArrayList<Byte>();
    static ArrayList<Byte> code_instr = new ArrayList<Byte>();

    public static void listFilesFromFolder(final File folder, Process process) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesFromFolder(fileEntry, process);
            } else {
                set_process_to_queue_from_file(process, fileEntry.getPath());
            }
        }
    }

    public static void set_process_to_queue_from_file(Process process, String path) {
        byte byteRead, priority = 0;
        short id = 0, data_size = 0;
        short instr_counter = 0, counter = 0;
        byte second_byte = 0;
        try {
            InputStream inputStream = new FileInputStream("./" + path);
            while ((byteRead = (byte) inputStream.read()) != -1) {
                // System.out.print(byteRead + " ");
                counter++;
                if (counter == 1)
                    priority = byteRead;
                else if (counter == 2) {
                    second_byte = (byte) inputStream.read();
                    // System.out.print(second_byte + " ");
                    id = join(byteRead, second_byte);
                    counter++;
                } else if (counter == 4) {
                    second_byte = (byte) inputStream.read();
                    // System.out.print(second_byte + " ");
                    data_size = join(byteRead, second_byte);
                    counter++;
                } else if (counter > 8) {
                    instr_counter++;
                    if (instr_counter <= data_size) {
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
                load_data_to_memory(data_instr, pcb);
                load_code_to_memory(code_instr, pcb);
                System.out.println("data size : " + data_size + " code size : " +
                        (instr_counter - data_size));
                System.out.println("data page table : " +
                        pcb.getDATA_PAGE_TABLE().toString());
                System.out.println("code page table : " +
                        pcb.getCODE_PAGE_TABLE().toString());
                // System.out.println("current frame_offset : " + bytes_in_frame + " of frame no : " + current_frame);
            }
            System.out.println(path + " , " + priority + " is loaded in memory");
            // System.out.println(data_instr.toString());
            // System.out.println(code_instr.toString());

            data_instr.clear();
            code_instr.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static short join(byte num1, byte num2) {
        return (short) ((num1 * 256) + num2);
    }

    static void load_data_to_memory(ArrayList<Byte> instr, PCB pcb) {
        // The Stack limit (SPR[9]) is set to current memory index + 49, which is the
        // last index allocated to
        // the stack in the memory.
        pcb.get_SPR()[9] = (short) (memory_index + 49);
        // The Stack Base (SPR[7]) is set to 0.
        pcb.get_SPR()[7] = (short) memory_index;
        // The Stack counter is set to the Stack Base which is 0.
        pcb.get_SPR()[8] = pcb.get_SPR()[7];
        pcb.setSTACK_FRAME(current_frame);
        memory_index += 50;
        if (bytes_in_frame + 50 >= 128) {
            int temp = frame_size - bytes_in_frame;
            current_frame++;
            bytes_in_frame = 50 - temp;
        } else {
            bytes_in_frame += 50;
        }
        // Data base
        pcb.get_SPR()[4] = (short) memory_index;
        // Data limit
        pcb.get_SPR()[5] = (short) (memory_index + instr.size() - 1);
        // Data counter
        pcb.get_SPR()[6] = pcb.get_SPR()[4];
        int current_byte = 0;
        for (int j = memory_index; j < instr.size() + memory_index; j++) {
            Process.memory[j] = instr.get(current_byte);
            current_byte++;
            bytes_in_frame++;
            if (bytes_in_frame == 128) {
                bytes_in_frame = 0;
                pcb.setDATA_PAGE_TABLE(current_frame);
                current_frame++;
            } else if (j == instr.size() + memory_index - 1) {
                pcb.setDATA_PAGE_TABLE(current_frame);
            }
        }
        memory_index += current_byte;
    }

    static void load_code_to_memory(ArrayList<Byte> instr, PCB pcb) {
        // The Code Base and the Program Counter is set to current memory index
        pcb.get_SPR()[1] = (short) memory_index;
        pcb.get_SPR()[10] = pcb.get_SPR()[1];
        // The Code Counter currently points to the next index of data segment
        pcb.get_SPR()[3] = (short) (pcb.get_SPR()[1] - 1);
        int current_byte = 0;
        for (int j = memory_index; j < instr.size() + memory_index; j++) {
            Process.memory[j] = instr.get(current_byte);
            pcb.get_SPR()[3]++;
            current_byte++;
            bytes_in_frame++;
            if (bytes_in_frame == 128) {
                bytes_in_frame = 0;
                pcb.setCODE_PAGE_TABLE(current_frame);
                current_frame++;
            } else if (j == instr.size() + memory_index - 1) {
                pcb.setCODE_PAGE_TABLE(current_frame);
            }
        }
        memory_index += (current_byte);
        // code limit
        pcb.get_SPR()[2] = (short) (memory_index - 1);
    }

}