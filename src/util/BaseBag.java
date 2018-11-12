package util;

import java.lang.reflect.Array;
import java.util.*;

public abstract class BaseBag<T> implements Iterable<T>{
	protected T[] elements;
	protected int size = 0;

	@SuppressWarnings("unchecked")
	public BaseBag(Class<T> clazz, int initCapacity) {
		elements = (T[]) Array.newInstance(clazz, initCapacity);
	}

	@SuppressWarnings("unchecked")
	public BaseBag(Class<T> tClass) {
        elements =  (T[])Array.newInstance(tClass, 64);
    }

    public abstract T get(int index);

	public abstract boolean isEmpty();

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
		if(index>=capacity()) {
			grow(Math.max(capacity() *2, index+1));
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null || obj instanceof Bag) {
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
	public Iterator<T> iterator() {
		return new BagIterator<T>(elements);
	}

    public int size(){
	    return size;
    }

    public void clear(){
	    for(int i = 0; i<capacity(); i++){
	        elements[i] = null;
        }
    }
}
class BagIterator<T>implements Iterator<T> {

	private final T[] elements;
	private int index = 0;

	public BagIterator(T[] elements){
		this.elements = elements;
	}
	@Override
	public boolean hasNext() {
		if(index >= elements.length){
			return false;
		}
		if(elements[index] == null){
			return false;
		}
		return true;
	}

	@Override
	public T next() {
		if(hasNext()){
			return elements[index++];
		}else{
			throw new NoSuchElementException();
		}
	}
}

