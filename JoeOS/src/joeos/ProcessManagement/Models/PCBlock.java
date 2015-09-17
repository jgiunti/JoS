/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeos.ProcessManagement.Models;

/**
 *
 * @author Joe
 */
public class PCBlock {
    
    public int pID;
    public char pState;
    public String pName;
    public int priority;
    public int arrivalTime;
    public int cpuBurst;
    public RegisterSet RegVals = new RegisterSet();
    
    public PCBlock(String[] proc){
        pID = ProcessTable.nextID();
        pState = 'n';
        pName = proc[0];
        priority = Integer.parseInt(proc[1]);
        arrivalTime = Integer.parseInt(proc[2]);
        cpuBurst = Integer.parseInt(proc[3]);     
        RegVals.XAR = proc[5];
        RegVals.XDI = proc[6];
        RegVals.XDO = proc[7];
        RegVals.PC  = proc[8];
        RegVals.IR = proc[9];
        RegVals.EMIT = proc[10];
        RegVals.RR = proc[11];
        RegVals.PSW = proc[12];
        RegVals.R0 = proc[13];
        RegVals.R1 = proc[14];
        RegVals.R2 = proc[15];
        RegVals.R3 = proc[16];
        RegVals.R4 = proc[17];
        RegVals.R5 = proc[18];
        RegVals.R6 = proc[19];
        RegVals.R7 = proc[20];
    }   
}
