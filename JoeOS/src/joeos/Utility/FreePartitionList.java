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
package joeos.Utility;

import java.util.ArrayList;
import java.util.PriorityQueue;
import joeos.ProcessManagement.Models.FreePartitionNode;
import joeos.ProcessManagement.Models.PCBlock;

/**
 *
 * @author Joe
 */
public class FreePartitionList {
    
    PriorityQueue<FreePartitionNode> fpl;
    private static final int _MAX = 100000;
    
    public FreePartitionList() {
        FreePartitionNode initNode = new FreePartitionNode(0, _MAX);
        fpl = new PriorityQueue<>();
        fpl.offer(initNode);
    }
    
    public boolean allocate(PCBlock block) {
        int procSize = block.getProcSize();
        FreePartitionNode free = search(procSize);
        
        if (free == null) {
            return false;
        }
        else {
            block.setStartLoc(free.startLoc());
            int newStart = free.startLoc() + procSize;
            int newSize = free.size() - procSize;
            deletePart(free);
            
            if (newSize != 0) {
                createFreePart(newStart, newSize);
            }
            return true;
        }
    }
    
    public void deallocate(PCBlock block) {
        createFreePart(block.getStartLoc(), block.getProcSize());
        
        block.setStartLoc(-1);
    }
    
    private FreePartitionNode search(int size) {
        FreePartitionNode found = null;
        for (FreePartitionNode node : fpl) {
            if (node.size() >= size) {
                found = node;
            }
        }
        return found;
    }
    
    private void createFreePart(int startLoc, int size) {
        FreePartitionNode node = new FreePartitionNode(startLoc, size);
        insert(node);
        mergeNodes();
    }
    
    private void insert(FreePartitionNode node) {
        this.fpl.offer(node);
    }
    
    private void mergeNodes() {
        FreePartitionNode prevNode = fpl.peek();
        if (prevNode != null) {
            ArrayList<FreePartitionNode> toDelete = new ArrayList<>();
            ArrayList<FreePartitionNode> toInsert = new ArrayList<>();
            for (FreePartitionNode node : fpl) {
                if ((prevNode.startLoc() + prevNode.size()) == node.startLoc()) {
                    FreePartitionNode newPart = new FreePartitionNode(prevNode.startLoc(), (prevNode.size() + node.size()));
                    toDelete.add(node);
                    toDelete.add(prevNode);
                    toInsert.add(newPart);
                    //deletePart(prevNode);
                    //deletePart(node);
                    //insert(newPart);
                }           
            }
            for (FreePartitionNode node : toDelete) {
                deletePart(node);
            }
            for (FreePartitionNode node : toInsert) {
                insert(node);
            }
        }      
    }
    
    private void deletePart(FreePartitionNode node) {
        this.fpl.remove(node);
    }
}
