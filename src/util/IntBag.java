package util;

import java.util.Arrays;
import java.util.Iterator;

public class IntBag implements Iterable<int[][]> {
    private int[][] elements;
    private int size;

    public IntBag(int innerArrayLength) {
        elements = new int[64][innerArrayLength];
    }

    public IntBag(int innerArrayLength, int startSize) {
        elements = new int[startSize][innerArrayLength];
    }


    public int[] get(int entityID) {
        return elements[entityID];
    }

    public void add(int[] indexs) {
        ensureCapacity(size + indexs.length);
        elements[size++] = indexs;
    }

    public void set(int[] indexs, int entityID) {
        ensureCapacity(entityID);
        elements[entityID] = indexs;
    }

    public void remove(int entityID) {
        ensureCapacity(entityID);
        elements[entityID] = null;
    }


    public int capacity() {
        return elements.length;
    }

    public boolean isIndexInBounds(int index) {
        return index < capacity();
    }

    protected void grow(int newCapacity) {
        elements = Arrays.copyOf(elements, newCapacity);
    }

    public void ensureCapacity(int index) {
        if (index >= capacity()) {
            grow(Math.max(capacity() * 2, index + 1));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null || obj instanceof IntBag) {
            Bag bag = (Bag) obj;
            if (bag.size != size) {
                return false;
            }

            for (int i = 0; i < elements.length; i++) {
                if (elements[i] != bag.elements[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return new IntBagIterator(elements);
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < capacity(); i++) {
            elements[i] = null;
        }
    }
}

class IntBagIterator implements Iterator {
    int[][] elements;
    int index;
    int size;

    public IntBagIterator(int[][] elements) {
        this.elements = elements;
        index = 0;
        size = elements.length;
    }

    /**
     * Side effect: if elements[i] == null && index<=size-1; index++
     * @return
     */
    @Override
    public boolean hasNext() {
        if(elements[index] != null){
            return true;
        }

        if(index <= size -1){
            index++;
            return true;
        }
        return false;
    }

    @Override
    public int[] next() {
       return elements[index++];
    }
}
