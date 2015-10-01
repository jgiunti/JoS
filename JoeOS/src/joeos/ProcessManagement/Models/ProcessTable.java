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
    
    private ArrayList<PCBlock> table;
    private static int _nextID;
    
    public ProcessTable(){
        table = new ArrayList<>(100);
        _nextID = 0;
    }
    
    public void init(){
        for(int i = 0; i < 100; i++){
            this.table.add(null);
        }
    }
    
    public void add(PCBlock block){
        this.table.set(block.pID, block);
        if(table.contains(null)){
            nextId();
        }   
        else{
            _nextID = -99;
        }
    }
       
    public void remove(PCBlock block){
        this.table.remove(block);
    }
    
    public ArrayList<PCBlock> getTable(){
        return this.table;
    }
    
    public boolean isFull(){
        return _nextID == -99;
    }
    
    public static int getNextID(){
        return _nextID;
    }
    
    private int nextId(){
        if(this.table.isEmpty()){
            _nextID = 0;
            return 0;
        }
        else{
            while(this.table.get(_nextID) != null){
                _nextID = (_nextID + 1) % 100;
            }
            return _nextID;
        }
    }
    
}
