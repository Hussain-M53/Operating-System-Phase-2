package main.OS;

import java.util.Queue;

import main.PCB.PCB;
import main.PROCESS.Process;

public class OS {
    Queue<PCB> HIGH_PRIORITY_QUEUE;
    Queue<PCB> LOW_PRIORITY_QUEUE;
    Queue<PCB> RUNNING_QUEUE;
    Process running_process= new Process();

public void set_process_to_queue_from_file(){

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

    }
}
