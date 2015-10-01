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
public class PCBlock implements Comparable<PCBlock>{
    
    private int pID;
    private char pState;
    private String pName;
    private int priority;
    private int arrivalTime;
    private int cpuBurst;
    private RegisterSet RegVals;
    private PCBlock nextPCB;
    
    public PCBlock(String[] proc){
        pID = ProcessTable.getNextID();
        pState = 'r';
        pName = proc[0];
        priority = Integer.parseInt(proc[1]);
        arrivalTime = Integer.parseInt(proc[2]);
        cpuBurst = Integer.parseInt(proc[3]);
        RegVals = new RegisterSet(proc);
    }
    
    public void decCpuBurst(){
        this.cpuBurst--;
    }
    
    public PCBlock nextPCB(){
        return this.nextPCB;
    }
    
    public void setNextPCB(PCBlock block){
        this.nextPCB = block;
    }
    
    public int getPID(){
        return this.pID;
    }
    
    public String getPname(){
        return this.pName;
    }

    @Override
    public int compareTo(PCBlock t) {
        if(this.cpuBurst == t.cpuBurst){
            return 0;
        }
        else if(this.cpuBurst > t.cpuBurst){
            return 1;
        }
        else{
            return -1;
        }
    }
}
