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

import java.util.AbstractQueue;
import java.util.HashMap;
import java.util.Iterator;
import joeos.ProcessManagement.Models.PCBlock;

/**
 *
 * @author Joe
 */
public class ReadyQueue extends AbstractQueue{
    
    private Integer[] q;
    private HashMap<Integer, Integer> vals;
    private int size;
    private int maxSize;
    
    public ReadyQueue(int capacity) {
        q = new Integer[capacity];
        vals = new HashMap<>();
        size = 0;
        maxSize = capacity;
    }
    
    @Override
    public Iterator iterator() {
        Iterator<Integer> it = new Iterator<Integer>() {
            private int index;

            @Override
            public boolean hasNext() {
                return index < size && q[index] != null;
            }

            @Override
            public Integer next() {
                return q[index++];
            }
        };
        return it;
    }

    @Override
    public int size() {
        return this.q.length;
    }

    @Override
    public boolean offer(Object e) {
        try {
            PCBlock ref = (PCBlock)e;
            int id = ref.getPID();
            int burst = ref.getCpuBurst();
            if (size < maxSize) {
                q[size] = id;
                size++;
                vals.put(id, burst);
                percUp();
                return true;
            }
            else {
                return false;
            }            
        }
        catch (ClassCastException ex) {
            throw new ClassCastException("Invalid type. " + ex.getLocalizedMessage());
        }
        catch (NullPointerException ex) {
            throw new NullPointerException(ex.getLocalizedMessage());
        }      
    }

    @Override
    public Object poll() {
        if (!isEmpty()) {
            int head = q[0];
            q[0] = q[size - 1];
            q[size - 1] = null;
            size--;
            percDown();
            vals.remove(head);
            return head;
        }
        else {
            return null;
        }
    }

    @Override
    public Object peek() {
        if (!isEmpty()) {
            return q[0];
        }
        else {
            return null;
        }
    }
    
    private void percDown() {
        int pos = 0;
        while (hasLeftChild(pos)) {
            int smallest = leftChild(pos);
            if (hasRightChild(pos)) {
                if (value(rightChild(pos)) < value(smallest)) {
                    smallest = rightChild(pos);
                }
            }
            if (value(smallest) < value(pos)){
                swap(pos, smallest);
            }
            else {
                break;
            }
            pos = smallest;
        }
    }
    
    private void percUp() {
        int pos = size - 1;
        while (hasParent(pos)) {
            int parent = parent(pos);
            if (value(pos) < value(parent)){
                swap(pos, parent);
            }
            else {
                break;
            }
            pos = parent;
        }     
    }
    
    private void swap(int i, int j) {
        int temp = q[i];
        q[i] = q[j];
        q[j] = temp;
    }
    
    private boolean hasLeftChild(int i) {
        return (i * 2) + 1 < size;
    }
    
    private boolean hasRightChild(int i) {
        return (i * 2) + 2 < size;
    }
    
    private boolean hasParent(int i) {
        return i > 0;
    }
    
    private int leftChild(int i) {
        return q[(i * 2) + 1];
    }
    
    private int rightChild(int i) {
        return q[(i * 2) + 1];
    }
    
    private int parent(int i) {
        return q[(i - 1) / 2];
    }
    
    @Override
    public boolean isEmpty() {
        return this.q[0] == null;
    }
    
    public int value(int i) {
        if (!vals.containsKey(i)) {
            return -1;
        }
        return vals.get(i);
    }
    
    public void print() {
        for (Integer q1 : q) {
            System.out.println(q1);
        }
    }
    
    
}
