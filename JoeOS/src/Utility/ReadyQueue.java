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
package Utility;

import java.util.AbstractQueue;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Joe
 * @param <PCBlock>
 */
public class ReadyQueue<PCBlock> extends AbstractQueue{
    
    private Integer[] q;
    private HashMap<Integer, Integer> vals;
    private int size;
    private int maxSize;
    private int head;
    
    public ReadyQueue(int capacity) {
        q = new Integer[capacity];
        vals = new HashMap<>();
        size = 0;
        maxSize = capacity;
        head = 0;
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
            int item = (Integer)e;
            if (size < maxSize) {
                q[size] = item;
                size++;
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
            int first = head;
            percDown();
            return first;
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
        
    }
    
    private void percUp() {
        
    }
    
    private boolean hasLeftChild(int i) {
        return (i * 2) + 1 < size;
    }
    
    private boolean hasRightChild(int i) {
        return (i * 2) + 2 < size;
    }
    
    @Override
    public boolean isEmpty() {
        return this.q.length == 0;
    }
    
    
    
}
