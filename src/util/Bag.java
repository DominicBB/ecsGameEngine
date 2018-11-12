package util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;


public class Bag<T> extends BaseBag<T> {
	
	public Bag(Class<T> tClass) {
		super(tClass);
	}

	public T unsafeGet(int index) {
		return elements[index];
	}

	public T get(int index) {
		ensureCapacity(index);
		return elements[index];
	}

	public boolean remove(T elem) {
		int length = size;
		for (int i = 0; i < length; i++) {
			if (elements[i].equals(elem)) {
				elements[i] = elements[--size];
				elements[size] = null;
				return true;
			}
		}
		return false;
	}

	public T remove(int index) {
		T t = elements[index];
		elements[index] = elements[--size];
		elements[size] = null;
		return t;

	}

	public T removeLast() {
		T t = elements[--size];
		elements[size] = null;
		return t;

	}

	public boolean contains(T elem) {
		int length = size;
		for (int i = 0; i < length; i++) {
			if (elements[i].equals(elem)) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void unsafeAdd(T elem) {
		elements[size++] = elem;
	}

	public void add(T elem) {
		if (size == capacity()) {
			grow(capacity() * 2);
		}
		elements[size++] = elem;
	}
	
	public void unsafeSet(T elem, int index) {
		elements[index] = elem;
	}
	
	public void set(T elem, int index) {
		if(index >= capacity()) {
			grow(Math.max(capacity()*2, index));
		}
		elements[index] = elem;
	}

}

