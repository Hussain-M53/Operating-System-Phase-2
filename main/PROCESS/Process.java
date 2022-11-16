package main.PROCESS;

import java.util.ArrayList;
import java.util.BitSet;

import main.PCB.PCB;

public class Process {

    int clock_cycle = 0;
    int clock_cycle_range = 2;

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
    PCB process_pcb;

    public Process() {
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
        //copy_instructions_to_memory();
    }

    // The fetchToIR Method fetches the instruction from the Program Counter and
    // stores it into the Instruction Register
    // After fetching this instruction, the Program Counter is incremented by one,
    // pointing to the next instruction.
    void fetchToIR() {
        SPR[10] = (short) unsign(memory[SPR[9]]);
        SPR[9]++;
    }

    // The unsign method converts the byte into a unsigned integer and casts the
    // value to the data type short.
    short unsign(byte num) {
        return (short) Byte.toUnsignedInt(num);
    }

    // This method copies the instructions read from the file into the memory
    // The first 50 bytes of the memory are allocated to the stack
    public void copy_instructions_to_memory(ArrayList<Byte> byte_instr) {

        int instruction_start_index = 50;
        // The Stack limit (SPR[8]) is set to 49, which is the last index allocated to
        // the stack in the memory.
        SPR[8] = 49;
        // The Stack Base (SPR[6]) is set to 0.
        SPR[6] = 0;
        // The Stack counter is set to the Stack Base which is 0.
        SPR[7] = SPR[6];

        // Checks the memory for pre-existing data and sets the instruction start index
        // to the memory index that is currently empty.
        for (int i = instruction_start_index; i < memory.length; i++) {
            if (Convert.convert_int_to_hexa(memory[i]) == "F3") {
                instruction_start_index = ++i;
                break;
            }
        }
        // The Code Base and the Program Counter is set to this instruction_start_index
        SPR[0] = (short) instruction_start_index;
        SPR[9] = SPR[0];
        // The Code Counter currently points to the last index instruction in the
        // memory.
        SPR[2] = (short) (instruction_start_index - 1);
        SPR[1] = (short) memory.length;
        int count = 0;

        // Now after checking the pre-existing data in the memory
        // The new instructions are incremented one by one into the memory
        for (int j = instruction_start_index; j < byte_instr.size() + instruction_start_index; j++) {
            memory[j] = byte_instr.get(count);
            count++;
            // After incrementing each instruction in the memory the Code Counter is
            // incremented by one.
            SPR[2]++;
        }
    }

    // This method is used to decode the instruction and convert it from an integer
    // to its equivalent hex representation.
    String decode_IR(int opcode) {
        return Convert.convert_int_to_hexa(opcode);
    }

    // The findOpcodeInstr analysis the Opcode and executes it accordingly
    void findOpcodeInstr(int op) {
        String opcode = decode_IR(op);
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
        while (!process_end && SPR[9] <= SPR[2]) {
            fetchToIR();
            findOpcodeInstr(SPR[10]);
            // flag_reg.clear();
            Display.print_registers(this);
            clock_cycle++;
            if (clock_cycle > clock_cycle_range) {
                return false;
            }
        }
        return true;
    }
}
