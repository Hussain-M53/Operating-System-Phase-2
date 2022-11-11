package main.PROCESS;

public class Convert {

    //Converts the Hex Instruction to its equivalent integer representation and returns it as an Integer.
    static int convert_hex_to_int(String value) {
        return Integer.parseInt(value, 16);
    }

    //Converts an entire String array and generates another array with its equivalent integer value.
    static byte[] convert_array_to_decimal(String[] arr) {
        byte[] byte_instr = new byte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            byte decimal = (byte) convert_hex_to_int(arr[i]);
            byte_instr[i] = decimal;
        }
        return byte_instr;
    }

    //Converts the Integer to its equivalent Hex representation and returns it as a String.
    static String convert_int_to_hexa(int number) {
        return (Integer.toHexString(number).toUpperCase());
    }
}
