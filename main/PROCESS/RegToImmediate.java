package main.PROCESS;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class RegToImmediate {
    

    //The immediate method is used to fetch the immediate value one by one from the Instruction register
    //These two bytes are then combined and stored in a byte buffer
    //The order assigned to the buffer is BIG_ENDIAN so it reads the bytes from down to up.
    //The combines immediate value of the two bytes are stored in the num variable and casted to the data type short.
    static short immediate(Process process) {
        process.fetchToIR();
        short value1 = process.SPR[10];
        process.fetchToIR();
        short value2 = process.SPR[10];
        if(value1 >= 255 && value2 >=255){
            System.out.println("overflow");
            process.flag_reg.set(3,true);
        }
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.put((byte) value1);
        bb.put((byte) value2);
        bb.order(ByteOrder.BIG_ENDIAN);
        short num = bb.getShort(0);
        return num;
    }

    //Decodes the Register to Immediate Instructions and executes the instruction according to its matching opcode.
    static void decode_execute(Process process,String opcode) {
        
        process.fetchToIR();
        int value = process.SPR[10];
        switch (opcode) {
            case "30": // MOVI Instruction, Assigns the Register R1 to the Immediate value.
                process.GPR[value] = immediate(process);
                break;

            case "31": // ADDI Instruction, Adds R1 with the Immediate value and stores it in R1.
                process.GPR[value] += immediate(process);
                break;

            case "32": // SUBI Instruction, Subtracts R1 with the Immediate value and stores it in R1.
                process.GPR[value] -= immediate(process);
                break;

            case "33": // MULI Instruction, Multiplies R1 with the Immediate value and stores it in R1.
                process.GPR[value] *= immediate(process);
                break;

            case "34": // DIVI Instruction, Divides R1 with the Immediate value and stores it in R1.
                process.GPR[value] /= immediate(process);
                break;

            case "35": // ANDI Instruction, Logical ANDS, R1 with the Immediate value and stores it in R1.
                process.GPR[value] &= immediate(process);
                break;

            case "36": // ORI Instruction, Logical ORS, R1 with the Immediate value and stores it in R1.
                process.GPR[value] |= immediate(process);
                break;

            case "37": // BZ Instruction , Checks the flag register and if the zero bit is on, then it jumps to the offset.
                if (process.flag_reg.get(1) == true) {
                    process.SPR[9] = immediate(process);
                }
                break;

            case "38": // BNZ Instruction, Checks the flag register and if the zero bit is off, then it jumps to the offset.
                if (process.flag_reg.get(1) == false) {
                    process.SPR[9] = immediate(process);
                }
                break;

            case "39": // BC Instruction, Checks the flag register and if the carry bit is on, then it jumps to the offset.
                if (process.flag_reg.get(0) == true) {
                    process.SPR[9] = immediate(process);
                }
                break;

            case "3A": // BS Instruction, Checks the flag register and if the sign bit is on, then it jumps to the offset.
                if (process.flag_reg.get(2) == true) {
                    process.SPR[9] = immediate(process);
                }
                break;

            case "3B": // JMP Instruction , jumps to the offset.
                process.SPR[9] = immediate(process);
                break;

            case "3C": // CALL Instruction ,Pushes the PC on stack, and jumps to offset.
                process.memory[process.SPR[7]] = (byte)process.SPR[9];
                process.SPR[9] = immediate(process);
                ++process.SPR[7];
                break;

            case "3D": // ACT Instruction ,Does the service defined by num
        }
    }
}