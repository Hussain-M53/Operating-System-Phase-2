package main.PROCESS;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.SourceDataLine;

public class RegToImmediate {
    static int current_frame, current_page, current_offset, jump_pages, jump_offset,offset;

    // The immediate method is used to fetch the immediate value one by one from the
    // Instruction register
    // These two bytes are then combined and stored in a byte buffer
    // The order assigned to the buffer is BIG_ENDIAN so it reads the bytes from
    // down to up.
    // The combines immediate value of the two bytes are stored in the num variable
    // and casted to the data type short.
    static short immediate(Process process) {
        process.fetchToIR();
        short value1 = process.SPR[11];
        process.fetchToIR();
        short value2 = process.SPR[11];
        if (value1 >= 255 && value2 >= 255) {
            System.out.println("overflow");
            process.flag_reg.set(3, true);
        }
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.put((byte) value1);
        bb.put((byte) value2);
        bb.order(ByteOrder.BIG_ENDIAN);
        short num = bb.getShort(0);
        return num;
    }

    // Decodes the Register to Immediate Instructions and executes the instruction
    // according to its matching opcode.
    static void decode_execute(Process process, String opcode) {

        process.fetchToIR();
        int value = process.SPR[11];
        switch (opcode) {
            case "30": // MOVI Instruction, Assigns the Register R1, the Immediate value.
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

            case "35": // ANDI Instruction, Logical ANDS, R1 with the Immediate value and stores it in
                       // R1.
                process.GPR[value] &= immediate(process);
                break;

            case "36": // ORI Instruction, Logical ORS, R1 with the Immediate value and stores it in
                       // R1.
                process.GPR[value] |= immediate(process);
                break;

            case "37": // BZ Instruction , Checks the flag register and if the zero bit is on, then it
                       // jumps to the offset.
                if (process.flag_reg.get(1) == true) {
                    current_frame = process.SPR[10] / 128;
                    current_offset = process.SPR[10] % 128;
                    current_page = process.process_pcb.getCODE_PAGE_TABLE().indexOf(current_frame);
                    jump_pages = immediate(process) / 128;
                    jump_offset = immediate(process) % 128;
                    offset = jump_offset + current_offset;
                    if (offset >= 128) {
                        jump_pages++;
                        offset = offset % 128;
                    }
                    current_page = current_page + jump_pages;
                    current_frame = process.process_pcb.getCODE_PAGE_TABLE().get(current_page);
                    process.SPR[10] = (short) ((current_frame * 128) + offset);
                }
                break;

            case "38": // BNZ Instruction, Checks the flag register and if the zero bit is off, then it
                       // jumps to the offset.
                if (process.flag_reg.get(1) == false) {
                    current_frame = process.SPR[10] / 128;
                    current_offset = process.SPR[10] % 128;
                    current_page = process.process_pcb.getCODE_PAGE_TABLE().indexOf(current_frame);
                    jump_pages = immediate(process) / 128;
                    jump_offset = immediate(process) % 128;
                    offset = jump_offset + current_offset;
                    if (offset >= 128) {
                        jump_pages++;
                        offset = offset % 128;
                    }
                    current_page = current_page + jump_pages;
                    current_frame = process.process_pcb.getCODE_PAGE_TABLE().get(current_page);
                    process.SPR[10] = (short) ((current_frame * 128) + offset);
                }
                break;

            case "39": // BC Instruction, Checks the flag register and if the carry bit is on, then it
                       // jumps to the offset.
                if (process.flag_reg.get(0) == true) {
                    current_frame = process.SPR[10] / 128;
                    current_offset = process.SPR[10] % 128;
                    current_page = process.process_pcb.getCODE_PAGE_TABLE().indexOf(current_frame);
                    jump_pages = immediate(process) / 128;
                    jump_offset = immediate(process) % 128;
                    offset = jump_offset + current_offset;
                    if (offset >= 128) {
                        jump_pages++;
                        offset = offset % 128;
                    }
                    current_page = current_page + jump_pages;
                    current_frame = process.process_pcb.getCODE_PAGE_TABLE().get(current_page);
                    process.SPR[10] = (short) ((current_frame * 128) + offset);
                }
                break;

            case "3A": // BS Instruction, Checks the flag register and if the sign bit is on, then it
                       // jumps to the offset.
                if (process.flag_reg.get(2) == true) {
                    current_frame = process.SPR[10] / 128;
                    current_offset = process.SPR[10] % 128;
                    current_page = process.process_pcb.getCODE_PAGE_TABLE().indexOf(current_frame);
                    jump_pages = immediate(process) / 128;
                    jump_offset = immediate(process) % 128;
                    offset = jump_offset + current_offset;
                    if (offset >= 128) {
                        jump_pages++;
                        offset = offset % 128;
                    }
                    current_page = current_page + jump_pages;
                    current_frame = process.process_pcb.getCODE_PAGE_TABLE().get(current_page);
                    process.SPR[10] = (short) ((current_frame * 128) + offset);
                }
                break;

            case "3B": // JMP Instruction , jumps to the offset.
                current_frame = process.SPR[10] / 128;
                current_offset = process.SPR[10] % 128;
                current_page = process.process_pcb.getCODE_PAGE_TABLE().indexOf(current_frame);
                jump_pages = immediate(process) / 128;
                jump_offset = immediate(process) % 128;
                offset = jump_offset + current_offset;
                if (offset >= 128) {
                    jump_pages++;
                    offset = offset % 128;
                }
                current_page = current_page + jump_pages;
                current_frame = process.process_pcb.getCODE_PAGE_TABLE().get(current_page);
                process.SPR[10] = (short) ((current_frame * 128) + offset);
                break;

            case "3C": // CALL Instruction ,Pushes the PC on stack, and jumps to offset.
                Process.memory[process.SPR[8]] = (byte) process.SPR[10];
                current_frame = process.SPR[10] / 128;
                current_offset = process.SPR[10] % 128;
                current_page = process.process_pcb.getCODE_PAGE_TABLE().indexOf(current_frame);
                jump_pages = immediate(process) / 128;
                jump_offset = immediate(process) % 128;
                System.out.println("current_frame "+current_frame+"current_offset "+current_offset+"current_page "+current_page+"jump_page "+jump_pages+"jump_offset "+jump_offset);
                offset = jump_offset + current_offset;
                if (offset >= 128) {
                    jump_pages++;
                    offset = offset % 128;
                }
                current_page = current_page + jump_pages;
                current_frame = process.process_pcb.getCODE_PAGE_TABLE().get(current_page);
                process.SPR[10] = (short) ((current_frame * 128) + offset);
                ++process.SPR[8];
                break;
            case "3D": // ACT Instruction ,Does the service defined by num
        }
    }
}