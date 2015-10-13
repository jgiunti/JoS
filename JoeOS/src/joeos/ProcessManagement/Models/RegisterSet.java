/*
 * The MIT License
 *
 * Copyright 2015 Joe.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
    
    public RegisterSet(String[] vals) {
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
    
    public void updateRegVals(String[] vals) {
        XAR = vals[0];
        XDI = vals[1];
        XDO = vals[2];
        PC  = vals[3];
        IR = vals[4];
        EMIT = vals[5];
        RR = vals[6];
        PSW = vals[7];
        R0 = vals[8];
        R1 = vals[9];
        R2 = vals[10];
        R3 = vals[11];
        R4 = vals[12];
        R5 = vals[13];
        R6 = vals[14];
        R7 = vals[15];
    }
}
