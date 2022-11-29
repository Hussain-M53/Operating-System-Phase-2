package main.PROCESS;

public class Single_Op {

    // Decodes the Single Operation Instructions and executes the instruction
    // according to its matching opcode.
    static void decode_execute(Process process, String opcode) {
        process.fetchToIR();

        switch (opcode) {
            case "71": // SLL Instruction, moves each bit in the register to the left one by one.
                process.GPR[process.SPR[11]] = (short) (process.GPR[process.SPR[11]] << 1);
                break;

            case "72": // SRL Instruction, moves each bit in the register to the right one by one.
                process.GPR[process.SPR[11]] = (short) (process.GPR[process.SPR[11]] >> 1);
                break;

            case "73": // RL Instruction , shifts left sets and lower bit accordingly.
                process.GPR[process.SPR[11]] = (short) Integer.rotateLeft(process.GPR[process.SPR[11]], 1);
                break;

            case "74": // RR Instruction, shifts right sets and lower bit accordingly.
                process.GPR[process.SPR[11]] = (short) Integer.rotateRight(process.GPR[process.SPR[11]], 1);
                break;

            case "75": // Increment Instruction, increments the value in the Instruction Register by
                       // one.
                process.GPR[process.SPR[11]]++;
                break;

            case "76": // Decrement Instruction, decrements the value in the Instruction Register by
                       // one.
                process.GPR[process.SPR[11]]--;
                break;

            case "77": // PUSH Instruction , Pushes the contents of R1 on the Stack.
                Process.memory[process.SPR[8]] = (byte) process.SPR[11];
                ++process.SPR[8];
                break;

            case "78": // POP Instruction / Pop the contents from Stack and allocates it to R1.
                --process.SPR[8];
                process.GPR[process.SPR[11]] = Process.memory[process.SPR[8]];

        }
    }
}
