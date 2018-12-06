package core.coreSystems;

public abstract class BaseSystem {
	private static int baseID;

	public abstract void update();

	static int nextID(){
		return baseID++;
	}

}
