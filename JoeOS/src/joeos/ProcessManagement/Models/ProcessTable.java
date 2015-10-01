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

import java.util.ArrayList;

/**
 *
 * @author Joe
 */
public class ProcessTable {
    
    private ArrayList<PCBlock> table;
    private static int _nextID;
    private PCBlock Ready_Q;
    private PCBlock Term_Q;
    private PCBlock CPU;
    
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
        this.table.set(block.getPID(), block);
        if(Ready_Q == null){
            Ready_Q = block;
        }
        else{
            PCBlock temp = Ready_Q;
            while(temp.nextPCB() != null){
                temp = temp.nextPCB();
            }
            temp.setNextPCB(block);        
        }
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
