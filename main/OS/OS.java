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
    Queue<PCB> RUNNING_QUEUE;
    Process running_process;

    public OS() {
        HIGH_PRIORITY_QUEUE = new LinkedList<PCB>();
        LOW_PRIORITY_QUEUE = new LinkedList<PCB>();
        RUNNING_QUEUE = new LinkedList<PCB>();
        running_process = new Process();
    }

    public void set_process_to_queue_from_file() {

    }

    public void execute() {
        if (!HIGH_PRIORITY_QUEUE.isEmpty()) {
            PCB pcb = HIGH_PRIORITY_QUEUE.remove();
            RUNNING_QUEUE.add(pcb);
            running_process.get_from_pcb(pcb);
        } else {

        }
    }

    public static void main(String[] args) {
        final File folder = new File("./Files");
        Process process = new Process();
        FileRead.listFilesFromFolder(folder, process);

    }
}
