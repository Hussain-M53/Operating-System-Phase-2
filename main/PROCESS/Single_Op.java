package main.PROCESS;

import main.ERRORHANDLING.ErrorHandling;

public class Single_Op {

    // Decodes the Single Operation Instructions and executes the instruction
    // according to its matching opcode.
    static void decode_execute(Process process, String opcode) {
        process.fetchToIR();

        switch (opcode) {
            case "71": // SLL Instruction, moves each bit in the register to the left one by one.
                SetFlag.check_carry(process,process.SPR[11]);
                process.GPR[process.SPR[11]] = (short) (process.GPR[process.SPR[11]] << 1);
                SetFlag.check_sign(process,process.SPR[11]);
                SetFlag.check_zero(process,process.SPR[11]);
                break;

            case "72": // SRL Instruction, moves each bit in the register to the right one by one.
                SetFlag.check_carry(process,process.SPR[11]);
                process.GPR[process.SPR[11]] = (short) (process.GPR[process.SPR[11]] >> 1);
                SetFlag.check_sign(process,process.SPR[11]);
                SetFlag.check_zero(process,process.SPR[11]);
                break;

            case "73": // RL Instruction , shifts left sets and lower bit accordingly.
                SetFlag.check_carry(process,process.SPR[11]);
                process.GPR[process.SPR[11]] = (short) Integer.rotateLeft(process.GPR[process.SPR[11]], 1);
                SetFlag.check_sign(process,process.SPR[11]);
                SetFlag.check_zero(process,process.SPR[11]);
                break;

            case "74": // RR Instruction, shifts right sets and lower bit accordingly.
                SetFlag.check_carry(process,process.SPR[11]);
                process.GPR[process.SPR[11]] = (short) Integer.rotateRight(process.GPR[process.SPR[11]], 1);
                SetFlag.check_sign(process,process.SPR[11]);
                SetFlag.check_zero(process,process.SPR[11]);
                break;

            case "75": // Increment Instruction, increments the value in the Instruction Register by
                       // one.
                process.GPR[process.SPR[11]]++;
                SetFlag.check_sign(process,process.SPR[11]);
                SetFlag.check_zero(process,process.SPR[11]);
                break;

            case "76": // Decrement Instruction, decrements the value in the Instruction Register by
                       // one.
                process.GPR[process.SPR[11]]--;
                SetFlag.check_sign(process,process.SPR[11]);
                SetFlag.check_zero(process,process.SPR[11]);
                break;

            case "77": // PUSH Instruction , Pushes the contents of R1 on the Stack.
                if (process.SPR[8] > process.SPR[9]) {
                    ErrorHandling.stack_overflow();
                } else {
                    Process.memory[(process.process_pcb.getSTACK_FRAME() * 128)
                            + process.SPR[8]] = (byte) process.GPR[process.SPR[11]];
                    process.SPR[8]++;
                }
                break;

            case "78": // POP Instruction / Pop the contents from Stack and allocates it to R1.
                if (process.SPR[8] <= process.SPR[7]) {
                    ErrorHandling.stack_overflow();
                } else {
                    --process.SPR[8];
                    process.GPR[process.SPR[11]] = Process.memory[(process.process_pcb.getSTACK_FRAME() * 128)
                            + process.SPR[8]];
                }
        }
    }
}
