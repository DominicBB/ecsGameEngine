package util.geometry;

import components.Transform;

public class TransformUtil {


	public static void translate(Transform transform, Matrix4x4 translation) {
		transform.translation = transform.translation.compose(translation);
		transform.minimumBoundingBox.min = translation.multiply4x4(transform.minimumBoundingBox.min,1);
		transform.minimumBoundingBox.max = translation.multiply4x4(transform.minimumBoundingBox.max,1);
	}

	public static void scale(Transform transform, Matrix4x4 scale) {
		transform.scale = transform.scale.compose(scale);
		transform.minimumBoundingBox.min = scale.multiply4x4(transform.minimumBoundingBox.min,1);
		transform.minimumBoundingBox.max = scale.multiply4x4(transform.minimumBoundingBox.max,1);
	}

	public static void rotate(Transform transform, RotationMatrixHolder rotation) {
		transform.rotHolder.rotate(rotation);
	}

	public static void rotate(Transform transform, float x, float y, float z) {
		Matrix4x4 rz = Matrix4x4.newZRotation(z);
		Matrix4x4 rx = Matrix4x4.newXRotation(x);
		Matrix4x4 ry = Matrix4x4.newYRotation(y);
		transform.rotHolder.rotate(new RotationMatrixHolder(rz, rx, ry));
		Matrix4x4 rotation = transform.rotHolder.compose();
		transform.minimumBoundingBox.min = rotation.multiply4x4(transform.minimumBoundingBox.min,1);
		transform.minimumBoundingBox.max = rotation.multiply4x4(transform.minimumBoundingBox.max,1);
	}

	public static Matrix4x4 compose(Transform transform) {
//		Matrix4x4 rotComposition = transform.rotHolder.compose();
//		return transform.translation.compose(transform.scale).compose(rotComposition);
		//TODO why do I have to keep changing the order
		Matrix4x4 rotComposition = transform.rotHolder.compose();
		return rotComposition.compose(transform.scale.compose(transform.translation));

	}

	public static Matrix4x4 composeWith(Transform transform1, Transform transform2) {
		Matrix4x4 composedHolder = transform1.rotHolder.composeWith(transform2.rotHolder);
		Matrix4x4 compsedScale = transform1.scale.compose(transform2.scale);
		Matrix4x4 composedTranslation = transform1.translation.compose(transform2.translation);

		return composedHolder.compose(compsedScale).compose(composedTranslation);
		// return composedTranslation.compose(compsedScale).compose(composedHolder);

	}

	public static void updateDirections(Transform transform, Matrix4x4 rotation){
		transform.upDir = rotation.multiply4x4(transform.upDir, 1);
		transform.lookDir = rotation.multiply4x4(transform.lookDir, 1);
		transform.rightDir = rotation.multiply4x4(transform.rightDir, 1);
	}

}
