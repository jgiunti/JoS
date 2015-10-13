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
package joeos.ProcessManagement.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import joeos.ProcessManagement.Models.PCBlock;
import joeos.ProcessManagement.Models.ProcessTable;
import joeos.ProcessManagement.Models.VirtualProcess;



/**
 *
 * @author Joe
 */
public class Processes {
    private static PCBlock CPU;
    public static int globalTime = 0;
    public static int cpuTime = 0;
    public static boolean readyQChanged = false;
    public static boolean termQChanged = false;
    
    public static void intitialize() throws FileNotFoundException, IOException{              
        File procData = new File("G:/Documents/Computer Science/CS451/project2/processes2.txt");
        FileReader reader = new FileReader(procData);
        BufferedReader br = new BufferedReader(reader);
        LinkedList<VirtualProcess> procList = new LinkedList<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            VirtualProcess vp = new VirtualProcess(line.split("\\s"));
            procList.offer(vp);
        }       
        ProcessTable pTable = new ProcessTable();  
        pTable.init();
        while (!procList.isEmpty() || !pTable.isEmpty()) {
            readyQChanged = false;
            termQChanged = false;
            for (int i = 0; i < procList.size(); i ++) {
                if (procList.get(i).getArrivalTime() == globalTime) {
                    VirtualProcess arrivedProcess = procList.get(i);
                    if (!pTable.isFull()) {
                        PCBlock block = new PCBlock(arrivedProcess.getProcessInfo());          
                        pTable.add(block);
                        procList.remove(i);
                        i--;
                        readyQChanged = true;
                    }
                    else {
                        arrivedProcess.incArrivalTime();                                         
                    }                                                                         
                }                     
            }
            if (cpuFree() && !pTable.isEmpty()) {
                cpuTime = 0;
                PCBlock nextProc = pTable.nextProcess();              
                schedule(nextProc);
                readyQChanged = true;
            }
            else if (!cpuFree()) {
                cpuTime++;
                if (CPU.getCpuBurst() <= cpuTime) {
                    CPU.updateRegVals(genRandomVals());
                    CPU.terminated();
                    pTable.updateTermQ(CPU);
                    termQChanged = true;
                    CPU = null;
                }
            }
            if (readyQChanged) {
                pTable.printQ('r');
            }
            if (termQChanged) {
                pTable.printQ('t');
            }
            if (globalTime % 200 == 0) {
                pTable.clearTermQ();
            }
            globalTime++;
        }
    }
    
    private static boolean cpuFree() {
        return CPU == null;
    }
    
    private static void schedule(PCBlock block) {
        block.setNextPCB(null);
        CPU = block;
        CPU.executing();
    }
    
    private static String[] genRandomVals() {
        String[] regVals = new String[16];
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < regVals.length; i++) {           
            sb.append(Integer.toHexString(rand.nextInt()));          
            regVals[i] = sb.toString();
            sb.setLength(0);
        }
        return regVals;    
    }
}


