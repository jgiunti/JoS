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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import joeos.ProcessManagement.Models.PCBlock;
import joeos.ProcessManagement.Models.ProcessTable;
import joeos.ProcessManagement.Models.VirtualProcess;
import joeos.Utility.FreePartitionList;
import joeos.Utility.ReadyQueue;



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
        LinkedList<PCBlock> memWaitingQ = new LinkedList<>();
        String line;
        while ((line = br.readLine()) != null) {
            VirtualProcess vp = new VirtualProcess(line.split("\\s"));
            procList.offer(vp);
        }       
        ProcessTable pTable = new ProcessTable();
        FreePartitionList freeParts = new FreePartitionList();
        ReadyQueue rq = new ReadyQueue(100);
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
                        if (freeParts.allocate(block)) {   
                            rq.offer(block);
                            block.ready();
                            readyQChanged = true;
                        }
                        else {
                            block.waiting();
                            memWaitingQ.offer(block);
                            System.out.println("Process: " + block.getPname() + " waiting for memory");
                        }
                        
                    }
                    else {
                        arrivedProcess.incArrivalTime();  
                        //System.out.println("Process creating delayed");
                    }                                                                         
                }                     
            }
            if (cpuFree() && !rq.isEmpty()) {
                cpuTime = 0;
                PCBlock nextProc =  pTable.schedule((Integer)rq.poll());           
                schedule(nextProc);
                readyQChanged = true;
            }
            else if (!cpuFree()) {
                cpuTime++;
                CPU.updateRegVals(genRandomVals());
                if (CPU.getCpuBurst() <= cpuTime) {                   
                    CPU.terminated();
                    freeParts.deallocate(CPU);
                    pTable.updateTermQ(CPU);
                    ArrayList<PCBlock> remove = new ArrayList<>();
                    for (PCBlock waiting : memWaitingQ) {
                        if (freeParts.allocate(waiting)) {
                            PCBlock allocated = waiting;                          
                            allocated.ready();
                            rq.offer(allocated);
                            remove.add(allocated);
                        }
                    }
                    for(PCBlock alloc : remove) {
                        memWaitingQ.remove(alloc);
                    }
                    termQChanged = true;
                    CPU = null;
                }
            }
            if (readyQChanged) {
                pTable.printReadyQ(rq);
            }
            if (termQChanged) {
                pTable.printTermQ('t');
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


