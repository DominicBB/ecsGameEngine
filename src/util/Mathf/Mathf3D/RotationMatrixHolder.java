package util.Mathf.Mathf3D;

@Deprecated
public class RotationMatrixHolder {

	public Matrix4x4 zRot;
	public Matrix4x4 xRot;
	public Matrix4x4 yRot;

	public RotationMatrixHolder(Matrix4x4 zRot, Matrix4x4 xRot, Matrix4x4 yRot) {
		this.zRot = zRot;
		this.xRot = xRot;
		this.yRot = yRot;
	}

	public RotationMatrixHolder() {
		zRot = Matrix4x4.newZRotation(0);
		xRot = Matrix4x4.newXRotation(0);
		yRot = Matrix4x4.newYRotation(0);
	}

	public void rotate(RotationMatrixHolder rotHolder) {
		this.zRot = this.zRot.compose(rotHolder.zRot);
		this.xRot = this.xRot.compose(rotHolder.xRot);
		this.yRot = this.yRot.compose(rotHolder.yRot);
	}

	public void rotate(Matrix4x4 zRot, Matrix4x4 xRot, Matrix4x4 yRot) {
		this.zRot = this.zRot.compose(zRot);
		this.xRot = this.xRot.compose(xRot);
		this.yRot = this.yRot.compose(yRot);
	}
	
	public Matrix4x4 compose() {
		return yRot.compose(xRot).compose(zRot);

	}

	public Matrix4x4 composeWith(RotationMatrixHolder rotHolder) {
		Matrix4x4 zC = this.zRot.compose(rotHolder.zRot);
		Matrix4x4 xC = this.xRot.compose(rotHolder.xRot);
		Matrix4x4 yC = this.yRot.compose(rotHolder.yRot);

		return yC.compose(xC).compose(zC);

	}
	
	public Matrix4x4 composeWith(Matrix4x4 zRot, Matrix4x4 xRot, Matrix4x4 yRot) {
		Matrix4x4 zC = this.zRot.compose(zRot);
		Matrix4x4 xC = this.xRot.compose(xRot);
		Matrix4x4 yC = this.yRot.compose(yRot);

		return yC.compose(xC).compose(zC);

	}
	
	public Matrix4x4 composeWith(float zRot, float xRot, float yRot) {
		Matrix4x4 zC = this.zRot.compose(Matrix4x4.newZRotation(zRot));
		Matrix4x4 xC = this.xRot.compose(Matrix4x4.newZRotation(xRot));
		Matrix4x4 yC = this.yRot.compose(Matrix4x4.newZRotation(yRot));

		return yC.compose(xC).compose(zC);

	}
}
