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
    public RegisterSet RegVals;
    
    public PCBlock(String[] proc){
        pID = ProcessTable.getNextID();
        pState = 'n';
        pName = proc[0];
        priority = Integer.parseInt(proc[1]);
        arrivalTime = Integer.parseInt(proc[2]);
        cpuBurst = Integer.parseInt(proc[3]);
        RegVals = new RegisterSet(proc);
    }   
}
