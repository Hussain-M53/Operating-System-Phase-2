package main.PROCESS;

public class Display {

    // Prints the contents of the memory
    public static void print_mem(Process process) {
        System.out.print("Memory : ");
        for (int i = 0; i < FileRead.memory_index; i++) {
            System.out.print(Convert.convert_int_to_hexa(Byte.toUnsignedInt(Process.memory[i])) + " ");
        }
        System.out.println();
        System.out.println("x-------------------------------------------------x");

    }

    static void print_stack(Process process) {
        System.out.print("Stack : ");
        for (int i = 0; i <= process.process_pcb.get_SPR()[9]; i++) {
            System.out.print(Process.memory[(process.process_pcb.getSTACK_FRAME() * 128) + i] + ",");
        }
        System.out.println();
    }

    // Prints the contents of General Purpose Register and Special Purpose Register
    static void print_registers(Process process) {
        System.out.print("GPR : ");
        for (int i = 0; i < process.GPR.length; i++) {
            System.out.print((process.GPR[i]) + " ");
        }
        System.out.println();
        System.out.print("SPR : ");
        for (int i = 0; i < process.SPR.length; i++) {
            System.out.print((process.SPR[i]) + " ");
        }
        // System.out.println();
        // System.out.print("Flag Register : ");
        // for (int i = 0; i < 16; i++) {
        // System.out.print((process.flag_reg.get(i)) + " ");
        // }
        System.out.println();
        System.out.println("x-------------------------------------------------x");
    }
}
