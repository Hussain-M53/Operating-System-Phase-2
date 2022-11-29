package main.PROCESS;

public class RegToMem {

    //Decodes the Register to Memory Instructions and executes the instruction according to its matching opcode.
    static void decode_execute(Process process,String opcode) {

        process.fetchToIR();
        int value = process.SPR[11];

        switch (opcode) {
            case "51":  // MOVL Instruction, loads the value from the memory location and assigns it to R1.
                process.GPR[value] = (short) RegToImmediate.immediate(process);
                break;

            case "52": // MOVS Instruction, loads the value R1 and assigns it to memory location.
            Process.memory[value] = (byte) RegToImmediate.immediate(process);
        }
    }
}
