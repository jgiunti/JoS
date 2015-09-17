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
import joeos.ProcessManagement.Models.PCBlock;
import joeos.ProcessManagement.Models.ProcessTable;



/**
 *
 * @author Joe
 */
public class Processes {
    
    public static void intitialize() throws FileNotFoundException, IOException{
        File procData = new File("G:/Documents/Computer Science/CS451/CS451_Project_1/processes1.txt");
        FileReader reader = new FileReader(procData);
        BufferedReader br = new BufferedReader(reader);
        String line = null;
        ProcessTable.init();
        int count = 0;
        while((line = br.readLine()) != null && count < 100){
            String[] procInfo = line.split("\\s");
            //System.out.println(line);
            PCBlock block = new PCBlock(procInfo);          
            ProcessTable.add(block);
            count++;
        }
        ArrayList<PCBlock> ptable = ProcessTable.getTable();
        ptable.stream().forEach((processBlock) -> {
            System.out.println("Process Name: " + processBlock.pName + "\n" +
                    "Process   ID: " + processBlock.pID);
        });
    }
    
    
    
}
