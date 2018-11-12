package components;

import util.geometry.MinimumBoundingBox;
import util.geometry.Matrix4x4;
import util.geometry.RotationMatrixHolder;
import util.geometry.Vector3D;

/**
 * A composed matrix that represents were the entity is in the world
 */
public class Transform extends Component{
	public RotationMatrixHolder rotHolder;
	public Matrix4x4 scale;
	public Matrix4x4 translation;

	public Vector3D position;
	public Vector3D lookDir;
	public Vector3D upDir;
	public Vector3D rightDir;

	public MinimumBoundingBox minimumBoundingBox;

	public float fYaw;


	public Transform() {
		this.rotHolder = new RotationMatrixHolder();
		this.scale = Matrix4x4.newIdentityMatrix();
		this.translation = Matrix4x4.newIdentityMatrix();

		this.lookDir = new Vector3D(0f, 0f, 1f);
		this.position = new Vector3D(0f, 0f, 0f);
		this.upDir = new Vector3D(0, 1, 0);
		this.rightDir = new Vector3D(1, 0, 0);

		this.minimumBoundingBox = new MinimumBoundingBox();

		this.fYaw = 0.0f;
	}

	public Transform(RotationMatrixHolder rotHolder, Matrix4x4 scale, Matrix4x4 translation) {
		this.rotHolder = rotHolder;
		this.scale = scale;
		this.translation = translation;

		//TODO HMMMMMM
		this.lookDir = new Vector3D(0f, 0f, 1f);
		this.position = new Vector3D(0f, 0f, 0f);
		this.upDir = new Vector3D(0, 1, 0);
		this.rightDir = new Vector3D(1, 0, 0);
	}

}
