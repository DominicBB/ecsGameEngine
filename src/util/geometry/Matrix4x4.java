package util.geometry;

import components.Camera;
import components.Scene;

public class Matrix4x4 {

	private float[][] values;

	private Matrix4x4(float[][] fs) {
		values = fs;

	}

	public Matrix4x4() {
	}
	
	//transpose top left 3x3. dot product last row buy other rows
	//pseudo inverse of pointAt
	public static Matrix4x4 lookAtMatrix(Matrix4x4 pointAtMatrix) {
		Matrix4x4 p = pointAtMatrix;
		Vector3D a = new Vector3D(p.values[0][0],p.values[0][1],p.values[0][2]);
		Vector3D b = new Vector3D(p.values[1][0],p.values[1][1],p.values[1][2]);
		Vector3D c = new Vector3D(p.values[2][0],p.values[2][1],p.values[2][2]);
		Vector3D t = new Vector3D(p.values[3][0],p.values[3][1],p.values[3][2]);
		return new Matrix4x4(new float[][] {
			{ p.values[0][0], p.values[1][0], p.values[2][0], 0.0f },
			{ p.values[0][1], p.values[1][1], p.values[2][1], 0.0f }, 
			{ p.values[0][2], p.values[1][2], p.values[2][2], 0.0f },
			{ -t.dotProduct(a), -t.dotProduct(b), -t.dotProduct(c), 1.0f }
			});
	}
	
	
	public Matrix4x4 transpose() {
		if(this.values.length ==4) return transpose4x4();
		return new Matrix4x4(new float[][] {
			{ values[0][0], values[1][0], values[2][0]},
			{ values[0][1], values[1][1], values[2][1]}, 
			{ values[0][2], values[1][2], values[2][2]}
		});
	}
	public Matrix4x4 transpose4x4() {
		return new Matrix4x4(new float[][] {
			{ values[0][0], values[1][0], values[2][0], values[3][0]},
			{ values[0][1], values[1][1], values[2][1], values[3][1]}, 
			{ values[0][2], values[1][2], values[2][2], values[3][2]},
			{ values[0][3], values[1][3], values[2][3], values[3][3]}
		});
	}

	/*public Vector3D multiply(Vector3D y) {

		float x = (y.x * values[0][0] + y.y * values[1][0] + y.z * values[2][0]) + values[3][0];
		float y = (y.x * values[0][1] + y.y * values[1][1] + y.z * values[2][1]) + values[3][1];
		float z = (y.x * values[0][2] + y.y * values[1][2] + y.z * values[2][2]) + values[3][2];
//		float e = (y.x * values[3][0] + y.y * values[3][1] + y.z * values[3][2]) + values[3][3];
		return new Vector3D(x, y, z);

	}

	public Triangle multiply(Triangle t) {
		Triangle triangle = new Triangle(multiply(t.vectors[0]), multiply(t.vectors[1]), multiply(t.vectors[2]),t);
		return triangle;

	}*/

	public Vector3D multiplyProjection(Vector3D v) {
		float x = (v.x * values[0][0] + v.y * values[1][0] + v.z * values[2][0]) + values[3][0];
		float y = (v.x * values[0][1] + v.y * values[1][1] + v.z * values[2][1]) + values[3][1];
		float z = (v.x * values[0][2] + v.y * values[1][2] + v.z * values[2][2]) + values[3][2];
		float w = (v.x * values[0][3] + v.y * values[1][3] + v.z * values[2][3]) + values[3][3];
		if (w != 0) {
			x = x / w;
			y = y / w;
			z = z / w;
		}
		return new Vector3D(x, y, z, w);
	}

	public Vector3D multiplyMoveToView(Vector3D v) {
		float x = (v.x * values[0][0] + v.y * values[1][0] + v.z * values[2][0]) + values[3][0];
		float y = (v.x * values[0][1] + v.y * values[1][1] + v.z * values[2][1]) + values[3][1];
		float z = (v.x * values[0][2] + v.y * values[1][2] + v.z * values[2][2]) + values[3][2];
//		float w = (v.x * values[0][3] + v.y * values[1][3] + v.z * values[2][3]) + values[3][3];
		if (v.w != 0) {
			x = x / v.w;
			y = y / v.w;
			z = z / v.w;
		}
		return new Vector3D(x, y, z, v.w);
	}
	public Triangle multiplyMoveToView(Triangle t) {

		return new Triangle(multiplyMoveToView(t.vectors[0]), multiplyMoveToView(t.vectors[1]),
				multiplyMoveToView(t.vectors[2]),t);

	}


	public Triangle multiplyProjection(Triangle t) {

		return new Triangle(multiplyProjection(t.vectors[0]), multiplyProjection(t.vectors[1]),
				multiplyProjection(t.vectors[2]),t);

	}
	
	public Vector3D multiply4x4(Vector3D v, float k) {
		float x = (v.x * values[0][0] + v.y * values[1][0] + v.z * values[2][0]) + (k*values[3][0]);
		float y = (v.x * values[0][1] + v.y * values[1][1] + v.z * values[2][1]) + (k*values[3][1]);
		float z = (v.x * values[0][2] + v.y * values[1][2] + v.z * values[2][2]) + (k*values[3][2]);
//		float w = (v.x * values[3][0] + v.y * values[3][1] + v.z * values[3][2]) + (k*values[3][3]);
		
		return new Vector3D(x, y, z);
	}

	public Vector3D multiply4x4Proj(Vector3D v, float k) {
		float x = (v.x * values[0][0] + v.y * values[1][0] + v.z * values[2][0]) + (k*values[3][0]);
		float y = (v.x * values[0][1] + v.y * values[1][1] + v.z * values[2][1]) + (k*values[3][1]);
		float z = (v.x * values[0][2] + v.y * values[1][2] + v.z * values[2][2]) + (k*values[3][2]);
		float w = (v.x * values[3][0] + v.y * values[3][1] + v.z * values[3][2]) + (k*values[3][3]);

		return new Vector3D(x, y, z, w);
	}

	public Triangle multiply4x4Proj(Triangle t, float k) {
		return new Triangle(multiply4x4Proj(t.vectors[0], 1),multiply4x4Proj(t.vectors[1], 1), multiply4x4Proj(t.vectors[2], 1), t);
	}
	public Triangle multiply4x4(Triangle t, float k) {
		return new Triangle(multiply4x4(t.vectors[0], 1),multiply4x4(t.vectors[1], 1), multiply4x4(t.vectors[2], 1), t);
	}

	public static Matrix4x4 newPointAt(Vector3D f, Vector3D r, Vector3D up, Vector3D offset) {
		return new Matrix4x4(
				new float[][] {
					{ r.x, r.y, r.z, 0.0f },
					{ up.x, up.y, up.z, 0.0f }, 
					{ f.x, f.y, f.z, 0.0f},
					{offset.x, offset.y, offset.z, 1.0f }});
	}
	
	public static Matrix4x4 newLookAt(Vector3D f, Vector3D r, Vector3D up, Vector3D offset) {
		return new Matrix4x4(
				new float[][] {
					{ r.x, up.x, f.x, 0.0f },
					{ r.y, up.y, f.y, 0.0f }, 
					{ r.z, up.z, f.z, 0.0f},
					{-offset.dotProduct(r), -offset.dotProduct(up), -offset.dotProduct(f), 1.0f }});
	}
	
	
	public static Matrix4x4 newProjectionMatrix(Scene s, Camera c) {
		return new Matrix4x4(
		 new float[][] {
			{ s.aRatio* c.fFov, 0.0f, 0.0f, 0.0f },
			{ 0.0f, c.fFov, 0.0f, 0.0f },
			{ 0.0f, 0.0f,  c.fNorm , 1.0f },
			{ 0.0f, 0.0f, -c.fNear * c.fNorm, 0.0f }
				});
	}

	public static Matrix4x4 newTranslation(float tx, float ty, float tz) {
		return new Matrix4x4(
				new float[][] {
					{ 1.0f, 0.0f, 0.0f, 0.0f },
					{ 0.0f, 1.0f, 0.0f, 0.0f }, 
					{ 0.0f, 0.0f, 1.0f, 0.0f },
					{ tx, ty, tz, 1.0f }});
	}
	
	public static Matrix4x4 newScale(float tx, float ty, float tz) {
		return new Matrix4x4(
				new float[][] { 
					{ tx, 0.0f, 0.0f, 0.0f },
					{ 0.0f, ty, 0.0f, 0.0f}, 
					{ 0.0f, 0.0f, tz, 0.0f},
					{ 0.0f, 0.0f, 0.0f, 1.0f }});
	}
	
	
	public static Matrix4x4 newXRotation(float th) {
		float sinth = (float) Math.sin(th);
		float costh = (float) Math.cos(th);
		return new Matrix4x4(new float[][] { 
			{ 1.0f, 0.0f, 0.0f, 0.0f },
			{ 0.0f, costh, -sinth, 0.0f }, 
			{ 0.0f, sinth, costh, 0.0f },
			{ 0.0f, 0.0f, 0.0f, 1.0f }});
	}

	public static Matrix4x4 newYRotation(float th) {
		float sinth = (float) Math.sin(th);
		float costh = (float) Math.cos(th);
		return new Matrix4x4(new float[][] { 
			{ costh, 0.0f, sinth, 0.0f },
			{ 0.0f, 1.0f, 0.0f, 0.0f }, 
			{ -sinth, 0.0f, costh, 0.0f },
			{ 0.0f, 0.0f, 0.0f, 1.0f }});
	}

	public static Matrix4x4 newZRotation(float th) {
		float sinth = (float) Math.sin(th);
		float costh = (float) Math.cos(th);
		return new Matrix4x4(new float[][] { 
			{ costh, -sinth, 0.0f, 0.0f },
			{ sinth, costh, 0.0f, 0.0f },
			{ 0.0f, 0.0f, 1.0f, 0.0f },
			{ 0.0f, 0.0f, 0.0f, 1.0f }
				});
	}
	
	public static Matrix4x4 newIdentityMatrix() {
		return new Matrix4x4(new float[][] {
			{ 1.0f, 0.0f, 0.0f, 0.0f },
			{ 0.0f, 1.0f, 0.0f, 0.0f },
			{ 0.0f, 0.0f, 1.0f, 0.0f },
			{ 0.0f, 0.0f, 0.0f, 1.0f }
		});
		
	}
	
	
	
	public Matrix4x4 compose(Matrix4x4 other) {
		float[][] ans = new float[4][4];
		for(int row = 0; row<4;row++) {
			for(int col = 0; col<4;col++) {
				for(int i = 0; i<4;i++) {
					ans[row][col] += this.values[row][i] * other.values[i][col];

				}
			}
		}
		return new Matrix4x4(ans);
	}
	
	
	
	public String toString() {
		StringBuilder ans = new StringBuilder();
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				ans.append(values[row][col]).append(' ');
			}
			ans.append('\n');
		}
		return ans.toString();
	}
}
