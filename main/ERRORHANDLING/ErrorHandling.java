package main.ERRORHANDLING;

public class ErrorHandling {

    public static void overflow() {
        System.out.println("The immediate offset is out of bound");
    }
    public static void stack_overflow() {
        System.out.println("ERROR : Stack overflow");
    }
    public static void divide_by_zero() {
        System.out.println("ERROR: divide_by_zero");
    }

    public static void invalid_opcode(String opcode) {
        System.out.println("ERROR: Invalid opcode = " + opcode);
    }

    public static void frame_not_exist_in_pageTable() {
        System.out.println("ERROR : Frame does not exist in the page table");
    }

    public static void frame_to_jump_not_exist_in_pageTable() {
        System.out.println("ERROR : Frame to jump does not exist in the page table");
    
    }

    public static void access_outside_code_limit() {
        System.out.println("ERROR : index is outside of code limit");
    }
    public static void access_outside_stack_limit() {
        System.out.println("ERROR : index is outside of stack limit");
    }
    public static void invalid_priority() {
        System.out.println("ERROR : Invalid priority");

    }

}