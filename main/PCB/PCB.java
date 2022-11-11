package main.PCB;

import java.time.LocalTime;
import java.util.ArrayList;

public class PCB {
    private int PROCESS_ID;
    private int PROCESS_PRIORITY;
    private double PROCESS_SIZE;
    private String PROCESS_FILE_NAME;
    private short[] GPR = new short[16];
    private short[] SPR = new short[16];
    private int[][] PAGE_TABLE;
    private LocalTime WAITING_TIME, EXECUTION_TIME;
    private ArrayList<Byte> INTRUCTIONS;

    public PCB(int PROCESS_PRIORITY,double PROCESS_SIZE,String PROCESS_FILE_NAME,ArrayList<Byte> INTRUCTIONS) {
        set_PROCESS_ID();
        set_PROCESS_PRIORITY(PROCESS_PRIORITY);
        this.PROCESS_FILE_NAME= PROCESS_FILE_NAME;
        this.INTRUCTIONS= INTRUCTIONS;
    }

    ArrayList<Byte> get_INSTRUCTIONS() {
        return this.INTRUCTIONS;
    }

    private void set_PROCESS_ID() {
        this.PROCESS_ID = (int) (1 + Math.random() * 1000);
    }

    int get_PROCESS_ID() {
        return this.PROCESS_ID;
    }

    private void set_PROCESS_PRIORITY(int PROCESS_PRIORITY) {
        this.PROCESS_PRIORITY = PROCESS_PRIORITY;
    }

    public int get_PROCESS_PRIORITY() {
        return PROCESS_PRIORITY;
    }

    public void set_PROCESS_SIZE(double PROCESS_SIZE) {
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

    public void set_PAGE_TABLE(int[][] PAGE_TABLE) {
        this.PAGE_TABLE = PAGE_TABLE;
    }

    public int[][] get_PAGE_TABLE() {
        return this.PAGE_TABLE;
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
