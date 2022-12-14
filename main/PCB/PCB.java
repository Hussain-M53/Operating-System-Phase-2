package main.PCB;
import java.util.ArrayList;
import java.util.Arrays;

public class PCB {

    @Override
    public String toString() {
        return "PCB [PROCESS_ID=" + PROCESS_ID + ", PROCESS_PRIORITY=" + PROCESS_PRIORITY + ", PROCESS_SIZE="
                + PROCESS_SIZE + ", PROCESS_FILE_NAME=" + PROCESS_FILE_NAME + ", STACK_FRAME=" + STACK_FRAME + ", GPR="
                + Arrays.toString(GPR) + ", SPR=" + Arrays.toString(SPR) + ", DATA_PAGE_TABLE=" + DATA_PAGE_TABLE
                + ", CODE_PAGE_TABLE=" + CODE_PAGE_TABLE + ", WAITING_TIME=" + WAITING_TIME + ", EXECUTION_TIME="
                + EXECUTION_TIME + "]";
    }

    private short PROCESS_ID;
    private byte PROCESS_PRIORITY;
    private short PROCESS_SIZE;
    private String PROCESS_FILE_NAME;
    private int STACK_FRAME;
    private short[] GPR = new short[16];
    private short[] SPR = new short[16];
    private ArrayList<Integer> DATA_PAGE_TABLE;
    private ArrayList<Integer> CODE_PAGE_TABLE;
    public int WAITING_TIME, EXECUTION_TIME;

    public PCB(short PROCESS_ID, byte PROCESS_PRIORITY, short PROCESS_SIZE, String PROCESS_FILE_NAME) {
        this.PROCESS_ID = PROCESS_ID;
        set_PROCESS_PRIORITY(PROCESS_PRIORITY);
        this.PROCESS_FILE_NAME = PROCESS_FILE_NAME;
        DATA_PAGE_TABLE = new ArrayList<Integer>();
        CODE_PAGE_TABLE = new ArrayList<Integer>();

    }

    public int getSTACK_FRAME() {
        return STACK_FRAME;
    }

    public void setSTACK_FRAME(int frame_no) {
        STACK_FRAME = frame_no;
    }

    public ArrayList<Integer> getCODE_PAGE_TABLE() {
        return CODE_PAGE_TABLE;
    }

    public void setCODE_PAGE_TABLE(int frame) {
        CODE_PAGE_TABLE.add(frame);
    }

    public ArrayList<Integer> getDATA_PAGE_TABLE() {
        return DATA_PAGE_TABLE;
    }

    public void setDATA_PAGE_TABLE(int frame) {
        DATA_PAGE_TABLE.add(frame);
    }

    public void set_PROCESS_ID() {
        this.PROCESS_ID = (byte) (1 + Math.random() * 1000);
    }

    public Short get_PROCESS_ID() {
        return this.PROCESS_ID;
    }

    private void set_PROCESS_PRIORITY(byte PROCESS_PRIORITY) {
        this.PROCESS_PRIORITY = PROCESS_PRIORITY;
    }

    public byte get_PROCESS_PRIORITY() {
        return PROCESS_PRIORITY;
    }

    public void set_PROCESS_SIZE(short PROCESS_SIZE) {
        this.PROCESS_SIZE = PROCESS_SIZE;
    }

    public double get_PROCESS_SIZE() {
        return this.PROCESS_SIZE;
    }

    public void set_PROCESS_FILE_NAME(String PROCESS_FILE_NAME) {
        this.PROCESS_FILE_NAME = PROCESS_FILE_NAME;
    }

    public String get_PROCESS_FILE_NAME() {
        return PROCESS_FILE_NAME;
    }

    public void set_GPR(short[] GPR) {
        this.GPR = GPR;
    }

    public short[] get_GPR() {
        return this.GPR;
    }

    public void set_SPR(short[] SPR) {
        this.SPR = SPR;
    }

    public short[] get_SPR() {
        return this.SPR;
    }
}
