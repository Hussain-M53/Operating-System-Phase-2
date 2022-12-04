package main.PROCESS;

import main.ERRORHANDLING.ErrorHandling;

public class RegToReg {

    // Decodes the Register to Register Instructions and executes the instruction
    // according to its matching opcode.
    static void decode_execute(Process process, String opcode) {
        process.fetchToIR();
        int value1 = process.SPR[11];
        // System.out.println(value1);
        process.fetchToIR();
        int value2 = process.SPR[11];
        // System.out.println(value2);
        switch (opcode) {
            case "16": // MOV Instruction, Copies the Contents of Register R2 to R1.
                process.GPR[value1] = process.GPR[value2];
                SetFlag.check_zero(process, value1);
                SetFlag.check_sign(process, value1);
                SetFlag.check_overflow(process, value1);
                break;

            case "17": // ADD Instruction, Adds the Contents of Register R1 and R2 and allocates it to
                       // R1.
                process.GPR[value1] += process.GPR[value2];
                SetFlag.check_zero(process, value1);
                SetFlag.check_sign(process, value1);
                SetFlag.check_overflow(process, value1);
                break;

            case "18": // SUB Instruction, Subtracts the Contents of Register R1 from R2 and allocates
                       // it to R1.
                process.GPR[value1] -= process.GPR[value2];
                SetFlag.check_zero(process, value1);
                SetFlag.check_sign(process, value1);
                SetFlag.check_overflow(process, value1);
                break;

            case "19": // MUL Instruction, Multiplies the Contents of Register R1 and R2 and allocates
                       // it to R1.
                process.GPR[value1] *= process.GPR[value2];
                SetFlag.check_zero(process, value1);
                SetFlag.check_sign(process, value1);
                SetFlag.check_overflow(process, value1);
                break;

            case "1A": // DIV Instruction, Divides the Contents of Register R1 from R2 and allocates it
                       // to R1.
                try {
                    process.GPR[value1] /= process.GPR[value2];
                    SetFlag.check_zero(process, value1);
                    SetFlag.check_sign(process, value1);
                    SetFlag.check_overflow(process, value1);
                } catch (Exception e) {
                    ErrorHandling.divide_by_zero();
                }
                break;

            case "1B": // AND Instruction, Ands the Contents of Register R1 and R2 and allocates it to
                       // R1.
                process.GPR[value1] &= process.GPR[value2]; // flag register work to add
                SetFlag.check_zero(process, value1);
                SetFlag.check_sign(process, value1);
                SetFlag.check_overflow(process, value1);
                break;

            case "1C": // OR Instruction, Ors the Contents of Register R1 and R2 and allocates it to
                       // R1.
                process.GPR[value1] |= process.GPR[value2];// flag register work to add
                SetFlag.check_zero(process, value1);
                SetFlag.check_sign(process, value1);
                SetFlag.check_overflow(process, value1);
        }
    }

}
