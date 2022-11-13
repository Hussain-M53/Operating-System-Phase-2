package main.PROCESS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import main.OS.OS;
import main.PCB.PCB;

public class FileRead {
    public static void listFilesFromFolder(final File folder,Process process) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesFromFolder(fileEntry,process);
            } else {
                System.out.println(fileEntry.getPath());
                set_process_to_queue_from_file(process,fileEntry.getPath());
            }
        }
    }

    static short join(byte num1,byte num2){
        return (short) ((num1*256) + num2);
    }
    // The FileRead Method reads the file and returns the result by concatinating
    // the data in the file, seperated by spaces.
    // This is later used to split the result and store the Hex Instructions into an
    // array.
    public static void set_process_to_queue_from_file(Process process,String path) {
        byte byteRead,priority=0;
        short id=0,data_size=0;
        int counter=0;
        ArrayList<Byte> instr = new ArrayList<Byte>();
        try {
            InputStream inputStream = new FileInputStream("./"+path);
            while ((byteRead = (byte) inputStream.read())!= -1) {
                counter++;
                if (counter == 1)
                     priority =byteRead;
                else if(counter == 2){
                    id =  join(byteRead,(byte)inputStream.read());
                    counter++;
                }else if(counter == 4){
                    data_size = join(byteRead,(byte)inputStream.read());
                    counter++;
                }else if (counter > 8){
                    instr.add(byteRead);
                }
            }

            if(priority >=0 && priority<=15){
                OS.HIGH_PRIORITY_QUEUE.add(new PCB(id,priority,data_size,path,instr));
            }else if(priority > 15){
                OS.LOW_PRIORITY_QUEUE.add(new PCB(id,priority,data_size,path,instr));
            }else
                System.out.println("Invalid priority");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
