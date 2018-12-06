package components;

import util.Mathf.Mathf3D.Bounds.AABoundingBox;
import util.Mathf.Mathf3D.Matrix4x4;
import util.Mathf.Mathf3D.RotationMatrixHolder;
import util.Mathf.Mathf3D.Transform;
import util.Mathf.Mathf3D.Vector3D;

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
