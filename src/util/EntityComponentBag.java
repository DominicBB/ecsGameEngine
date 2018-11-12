package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import components.Component;

public class EntityComponentBag<T extends Component> extends BaseBag<T> {

	public EntityComponentBag(Class<T> tClass) {
		super(tClass);
	}

	@Override
	public T get(int index) {
		return elements[index];
	}

	public T safeGet(int index) {
		ensureCapacity(index);
		return elements[index];
	}

	public List<T> safeGetAll(int[] index) {
		List<T> components = new ArrayList<>(index.length);
		ensureCapacity(size+index.length);
		for (int i = 0; i<index.length; i++){
			components.add(elements[index[i]]);
		}
		return components;
	}

	public int addComponent(T component) {
		ensureCapacity(size+1);
		elements[size] = component;
		return size++;
	}

	public int unsafeAddComponent(T component) {
		elements[size] = component;
		return size++;
	}
	
	public int[] addAll(Collection<T> components) {
		int[] indexs = new int[components.size()];
		int i = 0;
		for(T c: components) {
			indexs[i] = addComponent(c);
			i++;
		}
		return indexs;
	}
	
	public int[] unsafeAddAll(Collection<T> components) {
		int[] indexs = new int[components.size()];
		int i = 0;
		for(T c: components) {
			indexs[i] = addComponent(c);
			i++;
		}
		return indexs;
		
	}


	//TODO need to protect against GC?
	public void removeComponent(int index) {
		ensureCapacity(index);
		elements[index] = null;
	}
	//TODO need to protect against GC?
	public void unsafeRemoveComponent(int index) {
		elements[index] = null;
	}
	//TODO need to protect against GC?
	public void removeComponents(int[] indexs) {
		ensureCapacity(size+indexs.length);
		for(int i = 0; i<indexs.length;i++){
			elements[indexs[i]] = null;
		}
	}
	
	public void unsafeRemoveComponents(int[] indexs) {
		for(int i = 0; i<indexs.length;i++){
			elements[indexs[i]] = null;
		}	}

	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}


}


