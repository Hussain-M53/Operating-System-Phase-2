package main.OS;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import main.PCB.PCB;
import main.PROCESS.FileRead;
import main.PROCESS.Process;

public class OS {
    public static ArrayList<PCB> HIGH_PRIORITY_QUEUE = new ArrayList<PCB>();;
    public static Queue<PCB> LOW_PRIORITY_QUEUE = new LinkedList<PCB>();
    public static Queue<PCB> RUNNING_QUEUE = new LinkedList<PCB>();
    public static Process running_process = new Process();
    public static ArrayList<Integer> Free_PAGE_TABLE = new ArrayList<Integer>();

    public static boolean check_priority_and_addtoQueue(byte priority, PCB pcb) {
        if (priority >= 0 && priority <= 15) {
            HIGH_PRIORITY_QUEUE.add(pcb);
        } else if (priority > 15 && priority <= 31) {
            LOW_PRIORITY_QUEUE.add(pcb);
        } else {
            System.out.println("Invalid priority");
            return false;
        }
        return true;
    }

    public static void priority_scheduling() {
        int index = 0;
        for (int i = 1; i < HIGH_PRIORITY_QUEUE.size(); i++) {
            if (HIGH_PRIORITY_QUEUE.get(index).get_PROCESS_PRIORITY() > HIGH_PRIORITY_QUEUE.get(i)
                    .get_PROCESS_PRIORITY()) {
                index = i;
            }
        }
        PCB pcb = HIGH_PRIORITY_QUEUE.remove(index);
        running_process.load_from_pcb(pcb);
        RUNNING_QUEUE.add(pcb);
        running_process.load_from_pcb(pcb);
    }

    public static void round_robin_scheduling() {
        PCB pcb = LOW_PRIORITY_QUEUE.remove();
        RUNNING_QUEUE.add(pcb);
        running_process.load_from_pcb(pcb);
        running_process.set_cycle_limit(8);
        running_process.apply_RR = true;
    }

    public static void execute() {
        PCB pcb;
        while (!HIGH_PRIORITY_QUEUE.isEmpty() || !LOW_PRIORITY_QUEUE.isEmpty()) {
            if (!HIGH_PRIORITY_QUEUE.isEmpty()) {
                priority_scheduling();
            } else {
                round_robin_scheduling();
            }
            boolean switch_context = !running_process.execute_instr();
            if (switch_context) {
                System.out.println("Context switched");
                pcb = running_process.load_to_pcb();
                byte priority = pcb.get_PROCESS_PRIORITY();
                check_priority_and_addtoQueue(priority, pcb);
            } else {
                RUNNING_QUEUE.remove();
                System.out.println("Process " + running_process.process_pcb.get_PROCESS_ID() + " of name "
                        + running_process.process_pcb.get_PROCESS_FILE_NAME() + " and priority "
                        + running_process.process_pcb.get_PROCESS_PRIORITY() + " is completed in execution time : "
                        + running_process.process_pcb.EXECUTION_TIME + " and waiting time : "
                        + running_process.process_pcb.WAITING_TIME);
                for (int i = running_process.SPR[7]; i <= running_process.SPR[2]; i++) {
                    System.out.print((byte) Process.memory[i] + " ");
                }
                System.out.println();
            }
            // break;
        }
    }

    static void set_Free_page_table() {
        for (int i = FileRead.current_frame; i <= (Process.memory.length / 128); i++) {
            Free_PAGE_TABLE.add(i);
        }
    }

    public static void main(String[] args) {
        final File folder = new File("./Files");
        Process process = new Process();
        FileRead.listFilesFromFolder(folder, process);
        set_Free_page_table();
        execute();
        // for (int i = 0; i < FileRead.memory_index; i++) {
        // System.out.print((byte) Process.memory[i] + " ");
        // }
        // System.out.println();
    }
}
