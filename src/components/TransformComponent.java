package components;

import util.Mathf.Mathf3D.Transform;

/**
 * A composed matrix that represents were the entity is in the world
 */
public class TransformComponent extends Component{

	public Transform transform;
	public TransformComponent() {
		this.transform = new Transform();
	}

	public TransformComponent(Transform transform) {
		this.transform = transform;
	}

}
