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
public class RegisterSet {
    
    private String XAR; 
    private String XDI; 
    private String XDO;
    private String PC; 
    private String IR;
    private String EMIT;           
    private String RR;
    private String PSW; 
    private String R0;
    private String R1;
    private String R2;
    private String R3;
    private String R4;
    private String R5;
    private String R6;
    private String R7;   
    
    public RegisterSet(String[] vals){
        XAR = vals[4];
        XDI = vals[5];
        XDO = vals[6];
        PC  = vals[7];
        IR = vals[8];
        EMIT = vals[9];
        RR = vals[10];
        PSW = vals[11];
        R0 = vals[12];
        R1 = vals[13];
        R2 = vals[14];
        R3 = vals[15];
        R4 = vals[16];
        R5 = vals[17];
        R6 = vals[18];
        R7 = vals[19];
    }
}
