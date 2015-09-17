/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joeos.ProcessManagement.Models;

import java.util.ArrayList;

/**
 *
 * @author Joe
 */
public class ProcessTable {
    
    private static final ArrayList<PCBlock> table = new ArrayList<>(100);
    private static int nextID;
    
    public static void init(){
        for(int i = 0; i < 100; i++){
            table.add(null);
        }
    }
    
    public static void add(PCBlock block){
        table.set(block.pID, block);
    }
       
    public static void remove(PCBlock block){
        table.remove(block);
    }
    
    public static ArrayList<PCBlock> getTable(){
        return table;
    }
    
    public static int nextID(){
        if(table.isEmpty()){
            nextID = 0;
            return 0;
        }
        else{
            while(table.get(nextID) != null){
                nextID = (nextID + 1) % 100;
            }
            return nextID;
        }
    }
    
}
