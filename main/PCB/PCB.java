package main.PCB;

import java.time.LocalTime;
import java.util.ArrayList;

public class PCB {

    private short PROCESS_ID;
    private byte PROCESS_PRIORITY;
    private short PROCESS_SIZE;
    private String PROCESS_FILE_NAME;
    private short[] GPR = new short[16];
    private short[] SPR = new short[16];
    private ArrayList<Integer> DATA_PAGE_TABLE;
    private ArrayList<Integer> CODE_PAGE_TABLE;
    //private ArrayList<Integer> Free_PAGE_TABLE;
    private LocalTime WAITING_TIME, EXECUTION_TIME;

    public PCB(short PROCESS_ID, byte PROCESS_PRIORITY, short PROCESS_SIZE, String PROCESS_FILE_NAME) {
        this.PROCESS_ID = PROCESS_ID;
        set_PROCESS_PRIORITY(PROCESS_PRIORITY);
        this.PROCESS_FILE_NAME = PROCESS_FILE_NAME;
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

    Short get_PROCESS_ID() {
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

    public void set_WAITING_TIME(LocalTime WAITING_TIME) {
        this.WAITING_TIME = WAITING_TIME;
    }

    public LocalTime get_WAITING_TIME() {
        return this.WAITING_TIME;
    }

    public void set_EXECUTION_TIME(LocalTime EXECUTION_TIME) {
        this.EXECUTION_TIME = EXECUTION_TIME;
    }

    public LocalTime get_EXECUTION_TIME() {
        return this.EXECUTION_TIME;
    }

}
