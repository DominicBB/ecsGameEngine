package core;

import components.Component;
import util.Bag;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * There should be no reason to extend this class, If more specialisation is needed use Tags
 */
public class Entity {
	protected final int id;
	protected long tags = 0;

	//TODO should just be a list I think
	protected Bag<Component> components = new Bag<>(Component.class);
	protected Queue<Integer> freeIndexs = new ArrayDeque<>();

	public Entity(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public Bag<Component> getComponents() {
		return components;
	}

	public Queue<Integer> getFreeIndexs() {
		return freeIndexs;
	}

	public Component getComponent(int index) {
		return components.get(index);
	}

	public void addComponents(List<Component> components){
		for(Component component: components){
			this.components.add(component);
			component.attachToEntity(this);
		}
	}
}
