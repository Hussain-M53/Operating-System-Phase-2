package main.PROCESS;

public class No_Op {
    //Decodes the No Operation Instructions and executes the instruction according to its matching opcode.
    static void decode_execute(Process process,String opcode) {
        
        switch (opcode) {
            case "F1": // RETURN Instruction , Pops PC from the Stack
                --process.SPR[8];
                process.SPR[10] = Process.memory[process.SPR[8]];
                if(process.SPR[10]   == 0)
                    process.process_end = true;
                break;
            case "F2": // NOOP Instruction, does nothing
                break;
            case "F3": // END Instruction, Terminates the process and stops executing the instructions 
            process.process_end = true; // and assigns the process_end variable to true.
        }
    }
}
