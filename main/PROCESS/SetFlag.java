package main.PROCESS;

public class SetFlag {

    static void check_carry(Process process, int value) {
        if (value < 0)
            process.flag_reg.set(0, true);
    }

    static void check_zero(Process process, int value) {
        if (value == 0)
            process.flag_reg.set(1, true);
    }

    static void check_sign(Process process, int value) {
        if (value < 0)
            process.flag_reg.set(2, true);
    }

    static void check_overflow(Process process, int value) {
        if (value > Byte.MAX_VALUE)
            process.flag_reg.set(3, true);
    }
}
