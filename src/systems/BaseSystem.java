package systems;

import components.Component;

import java.util.*;


public abstract class BaseSystem {
	private static int baseID;

	public abstract void update(float deltaTime);

	static int nextID(){
		return baseID++;
	}

}
