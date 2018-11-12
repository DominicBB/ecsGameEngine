package util.geometry;

public class Vector3D {
	public float x;
	public float y;
	public float z;
	public float w;

	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = 1;
	}

	public Vector3D(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

    public Vector3D(float w) {
		this.w = w;
    }

    public Vector3D minus(Vector3D other) {
		return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
	}

	public Vector3D plus(Vector3D other) {
		return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
	}
	
	public Vector3D scale(float scaler) {
		return new Vector3D(this.x *scaler, this.y *scaler, this.z *scaler);
	}

	public Vector3D divide(float divider) {
		return new Vector3D(this.x /divider, this.y /divider, this.z /divider);
	}


	public Vector3D unit() {
		float m = this.magnitude();
		return new Vector3D(this.x / m, this.y / m, this.z / m);
	}

	public float magnitude() {
		return (float) Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
	}

	public Vector3D crossProduct(Vector3D other) {
		float x = this.y * other.z - this.z * other.y;
		float y = this.z * other.x - this.x * other.z;
		float z = this.x * other.y - this.y * other.x;
		return new Vector3D(x, y, z);
	}

	public float dotProduct(Vector3D other) {
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}

	public double cosTheta(Vector3D other) {
		float mag = this.magnitude();
		float oMag = other.magnitude();
		return this.dotProduct(other) / mag / oMag;
	}
	
	public String toString() {
		return "["+x+","+y+","+z+"]";
	}
}
