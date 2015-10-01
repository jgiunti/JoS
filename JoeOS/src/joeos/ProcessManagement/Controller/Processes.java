/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeos.ProcessManagement.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        File procData = new File("G:/Documents/Computer Science/CS451/project2/processes2.txt");
        FileReader reader = new FileReader(procData);
        BufferedReader br = new BufferedReader(reader);
        LinkedList<VirtualProcess> procList = new LinkedList<>();
        String line = null;
        int i = 0;
        while((line = br.readLine()) != null){
            VirtualProcess vp = new VirtualProcess(line.split("\\s"));
            procList.offer(vp);
        }       
        ProcessTable pTable = new ProcessTable();  
        pTable.init();
        while(!procList.isEmpty()){
            for(i = 0; i < procList.size(); i ++){
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
            System.out.println("Process Name: " + processBlock.pName + "\n" +
                    "Process   ID: " + processBlock.pID);
        });
    }   
}


