package main.OS;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import main.PCB.PCB;
import main.PROCESS.FileRead;
import main.PROCESS.Process;

public class OS {
    public static Queue<PCB> HIGH_PRIORITY_QUEUE;
    public static Queue<PCB> LOW_PRIORITY_QUEUE;
    public static Queue<PCB> RUNNING_QUEUE;
    public static Process running_process;

    public OS() {
        HIGH_PRIORITY_QUEUE = new LinkedList<PCB>();
        LOW_PRIORITY_QUEUE = new LinkedList<PCB>();
        RUNNING_QUEUE = new LinkedList<PCB>();
        running_process = new Process();
    }

    public void set_process_to_queue_from_file() {

    }

    public static void execute() {
        PCB pcb ;
        if (!HIGH_PRIORITY_QUEUE.isEmpty()) {
            pcb = HIGH_PRIORITY_QUEUE.remove();
            RUNNING_QUEUE.add(pcb);
            running_process.get_from_pcb(pcb);
        } else {
            pcb = LOW_PRIORITY_QUEUE.remove();
            RUNNING_QUEUE.add(pcb);
            running_process.get_from_pcb(pcb);
        }
        boolean switch_context = !running_process.execute_instr();
        if(switch_context){
            pcb = running_process.transfer_to_pcb();
            byte priority = pcb.get_PROCESS_PRIORITY();
            
        }
    }

    public static void main(String[] args) {
        final File folder = new File("./Files");
        Process process = new Process();
        FileRead.listFilesFromFolder(folder, process);
        execute();
    }
}
