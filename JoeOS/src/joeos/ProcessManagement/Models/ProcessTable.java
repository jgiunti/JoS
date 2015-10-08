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
import java.util.PriorityQueue;

/**
 *
 * @author Joe
 */
public class ProcessTable {
    
    private ArrayList<PCBlock> table;
    private static int _nextID;
    private PCBlock Ready_Q;
    private PCBlock Term_Q;
    private PriorityQueue<PCBlock> sjn;
    
    public ProcessTable() {
        table = new ArrayList<>(100);
        sjn = new PriorityQueue<>();
        _nextID = 0;
    }
    
    public void init(){
        for(int i = 0; i < 100; i++) {
            this.table.add(null); //I am eating the cost of initializing indexes to null so I can use the set method
        }                        //set method requires an element to be in the index
    }
    
    public void add(PCBlock block) {
        this.table.set(block.getPID(), block);
        this.sjn.offer(block);
        if (table.contains(null)){
            nextId();
        }   
        else {
            _nextID = -99;
        }
    }
       
    public void remove(PCBlock block) {
        this.table.remove(block);
    }
    
    public ArrayList<PCBlock> getTable() {
        return this.table;
    }
    
    public boolean isFull() {
        return _nextID == -99;
    }
    
    public boolean isEmpty() {
        return this.sjn.peek() == null;
    }
    
    public static int getNextID() {
        return _nextID;
    }
    
    private int nextId() {
        if (this.table.isEmpty()) {
            _nextID = 0;
            return 0;
        }
        else {
            while(this.table.get(_nextID) != null) {
                _nextID = (_nextID + 1) % 100;
            }
            return _nextID;
        }
    }
      
    public PCBlock nextProcess() {
        return this.sjn.poll();
    }
    
    private void updateReadyQ() {
        Ready_Q = Ready_Q.nextPCB();
    }
    
    public void updateTermQ(PCBlock block) {
        if (Term_Q == null) {
            Term_Q = block;
        }
        else {
            PCBlock temp = Term_Q;
            while(temp.nextPCB() != null) {
                temp = temp.nextPCB();
            }
            temp.setNextPCB(block);        
        }
    }
    
    public void printQ(char qType) {      
        PCBlock temp;
        if (qType == 'r') {
            temp = Ready_Q;
            System.out.println("READY Q: ");
        }
        else {
            temp = Term_Q;
            System.out.println("TERMINATED Q: ");
        }
        while (temp != null) {
            System.out.println("Process Name: " + temp.getPname() + "\n" +
                    "Process   ID: " + temp.getPID());
            temp = temp.nextPCB();
        }
    }
    
    public void clearTermQ() {
        PCBlock temp = Term_Q;
        while (temp != null) {
            _nextID = temp.getPID();
            this.table.set(temp.getPID(), null);
            temp = temp.nextPCB();
        }
        Term_Q = null;
    }
}
