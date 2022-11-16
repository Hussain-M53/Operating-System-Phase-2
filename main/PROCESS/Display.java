package main.PROCESS;

public class Display{

    //Prints the contents of the memory 
    static void print_mem(Process process) {
        System.out.print("Memory : ");
        for (int i = 0; i <= process.SPR[2]; i++) {
            System.out.print(Convert.convert_int_to_hexa(Byte.toUnsignedInt(Process.memory[i])) + " ");
        }
        System.out.println();
        System.out.println("x-------------------------------------------------x");

    }

//Prints the contents of General Purpose Register and Special Purpose Register
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
        System.out.println();
        System.out.print("Flag Register : ");
        for (int i = 0; i < 16; i++) {
            System.out.print((process.flag_reg.get(i)) + " ");
        }
        System.out.println();
        System.out.println("x-------------------------------------------------x");
    }
}
