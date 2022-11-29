package main.PROCESS;

import java.util.ArrayList;
import java.util.BitSet;

import main.PCB.PCB;

public class Process {

    int clock_cycle = 0;
    int clock_cycle_range = 2;
    public boolean apply_RR = false;
    // Initializes the memory containing 2^16 bytes in a byte array.
    public static byte memory[] = new byte[65536];
    static final int frame_size = 128;
    static final int total_frame = memory.length / frame_size;
    // Initializes the flag register to 16 bits (2 bytes).
    BitSet flag_reg = new BitSet(16);

    // Intializes 16 General and Special Purpose registers in a short (2 bytes)
    // array.
    short GPR[] = new short[16];
    short SPR[] = new short[16];

    // This variable determines when the process ends, if the last instruction read
    // from the file
    // is an END instruction then changes to true and the instructions stop
    // executing.
    boolean process_end = false;

    // every process will have its own PCB
    public PCB process_pcb;

    int current_frame, current_page, current_offset, jump_page, jump_offset;

    public Process() {
    }

    public void set_cycle_limit(int limit) {
        clock_cycle_range = limit;
    }

    public PCB load_to_pcb() {
        process_pcb.set_GPR(this.GPR);
        process_pcb.set_SPR(this.SPR);
        return process_pcb;
    }

    public void load_from_pcb(PCB process_pcb) {
        this.process_pcb = process_pcb;
        this.GPR = process_pcb.get_GPR();
        this.SPR = process_pcb.get_SPR();
        // copy_instructions_to_memory();
    }

    // The fetchToIR Method fetches the instruction from the Program Counter and
    // stores it into the Instruction Register
    // After fetching this instruction, the Program Counter is incremented by one,
    // pointing to the next instruction.
    void fetchToIR() {
        SPR[11] = (short) unsign(memory[SPR[10]]);
        SPR[10]++;
    }

    // The unsign method converts the byte into a unsigned integer and casts the
    // value to the data type short.
    short unsign(byte num) {
        return (short) Byte.toUnsignedInt(num);
    }

    public void set_gpr_spr_first() {

    }

    // This method is used to decode the instruction and convert it from an integer
    // to its equivalent hex representation.
    String decode_IR(int opcode) {
        return Convert.convert_int_to_hexa(opcode);
    }

    // The findOpcodeInstr analysis the Opcode and executes it accordingly
    void findOpcodeInstr(int op) {
        String opcode = decode_IR(op);
        System.out.println("Opcode : " + opcode);
        switch (opcode) {
            case "16", "17", "18", "19", "1A", "1B", "1C":
                RegToReg.decode_execute(this, opcode);
                break;

            case "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C", "3D":
                RegToImmediate.decode_execute(this, opcode);
                break;

            case "51", "52":
                RegToMem.decode_execute(this, opcode);
                break;

            case "71", "72", "73", "74", "75", "76", "77", "78":
                Single_Op.decode_execute(this, opcode);
                break;

            case "F1", "F2", "F3":
                No_Op.decode_execute(this, opcode);
            default:
                System.out.println("Invalid opcode");
        }
    }

    // The execute_instr Method fetches the instruction from instruction Register
    // It then finds and executes the opcode instruction accordingly
    // After executing and making the appropiate changes, it clears the Flag
    // Register
    // And displayes the values in the Special Purpose registers and General Purpose
    // registers.
    public boolean execute_instr() {
        while (!process_end && SPR[10] <= SPR[3]) {
            // System.out.println("pc : "+SPR[10]);
            fetchToIR();
            findOpcodeInstr(SPR[11]);
            // flag_reg.clear();
            Display.print_registers(this);
            clock_cycle++;
            if (clock_cycle > clock_cycle_range && apply_RR) {
                return false;
            }
        }
        return true;
    }
}
