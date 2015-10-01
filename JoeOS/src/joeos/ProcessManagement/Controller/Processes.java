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
import joeos.ProcessManagement.Models.PCBlock;
import joeos.ProcessManagement.Models.ProcessTable;
import joeos.ProcessManagement.Models.VirtualProcess;



/**
 *
 * @author Joe
 */
public class Processes {
    
    public static void intitialize() throws FileNotFoundException, IOException{       
        int globalTime = 0;
        int cpuTime = 0;
        File procData = new File("G:/Documents/Computer Science/CS451/project2/processes2.txt");
        FileReader reader = new FileReader(procData);
        BufferedReader br = new BufferedReader(reader);
        LinkedList<VirtualProcess> procList = new LinkedList<>();
        String line = null;
        while((line = br.readLine()) != null){
            VirtualProcess vp = new VirtualProcess(line.split("\\s"));
            procList.offer(vp);
        }       
        ProcessTable pTable = new ProcessTable();  
        pTable.init();
        while(!procList.isEmpty()){
            for(int i = 0; i < procList.size(); i ++){
                if(procList.get(i).getArrivalTime() == globalTime){
                    VirtualProcess arrivedProcess = procList.get(i);
                    if(!pTable.isFull()){
                        PCBlock block = new PCBlock(arrivedProcess.getProcessInfo());          
                        pTable.add(block);
                        procList.remove(i);
                    }
                    else{
                        arrivedProcess.incArrivalTime();                                         
                        procList.set(i, arrivedProcess);
                        System.out.println("Process Table full");
                        i--;
                    }                                                                         
                }                     
            }
            globalTime++;
        }
        
        
        ArrayList<PCBlock> ptable2 = pTable.getTable();
        ptable2.stream().forEach((processBlock) -> {
            System.out.println("Process Name: " + processBlock.getPname() + "\n" +
                    "Process   ID: " + processBlock.getPID());
        });
    }   
}


