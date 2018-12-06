package util.Mathf.Mathf3D;


public class Vector3D {
    public float x;
    public float y;
    public float z;
    public float w;

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1f;
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

    public Vector3D absDiff(Vector3D other) {
        return new Vector3D(Math.abs(this.x - other.x), Math.abs(this.y - other.y), Math.abs(this.z - other.z));
    }

    public Vector3D plus(Vector3D other) {
        return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public void add(Vector3D toAdd) {
        this.x = x + toAdd.x;
        this.y = y + toAdd.y;
        this.z = z + toAdd.z;
    }

    public Vector3D mul(float scaler) {
        return new Vector3D(this.x * scaler, this.y * scaler, this.z * scaler);
    }

    public void scale(float scaler) {
        this.x = x * scaler;
        this.y = y * scaler;
        this.z = z * scaler;
    }

    public Vector3D divide(float divider) {
        return new Vector3D(this.x / divider, this.y / divider, this.z / divider);
    }


    public Vector3D normal() {
        float m = this.magnitude();
        return new Vector3D(this.x / m, this.y / m, this.z / m);
    }

    public void normalise() {
        float m = this.magnitude();
        this.x = x / m;
        this.y = y / m;
        this.z = z / m;
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

    public Vector3D componentMul(Vector3D other) {
        return new Vector3D(
                other.x * x,
                other.y * y,
                other.z * z,
                other.w * w
        );
    }

    public void componentAdd(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
    }

    public double cosTheta(Vector3D other) {
        float mag = this.magnitude();
        float oMag = other.magnitude();
        return this.dotProduct(other) / mag / oMag;
    }

    public Vector3D maxValues(Vector3D other) {
        Vector3D maxValues = Vector3D.newZeros();
        maxValues.x = Math.max(x, other.x);
        maxValues.y = Math.max(y, other.y);
        maxValues.z = Math.max(z, other.z);
        return maxValues;
    }

    public Vector3D minValues(Vector3D other) {
        Vector3D minValues = Vector3D.newZeros();
        minValues.x = Math.min(x, other.x);
        minValues.y = Math.min(y, other.y);
        minValues.z = Math.min(z, other.z);
        return minValues;
    }

    public float minVal() {
        float min = x;
        min = Math.min(y, min);
        return Math.min(z, min);
    }

    public float maxVal() {
        float max = x;
        max = Math.max(y, max);
        return Math.max(z, max);
    }

    public String toString() {
        return "[" + x + "," + y + "," + z + "]";
    }

    public static Vector3D newDown() {
        return new Vector3D(0f, -1f, 0f);
    }

    public static Vector3D newUp() {
        return new Vector3D(0f, 1f, 0f);
    }

    public static Vector3D newLeft() {
        return new Vector3D(-1f, 0f, 0f);
    }

    public static Vector3D newRight() {
        return new Vector3D(1f, 0f, 0f);
    }

    public static Vector3D newForward() {
        return new Vector3D(0f, 0f, 1f);
    }

    public static Vector3D newBackward() {
        return new Vector3D(0f, 0f, -1f);
    }

    public static Vector3D newOnes() {
        return new Vector3D(1f, 1f, 1f);
    }

    public static Vector3D newZeros() {
        return new Vector3D(0f, 0f, 0f);
    }


    public static final Vector3D UP = newUp();
    public static final Vector3D DOWN = newDown();
    public static final Vector3D LEFT = newLeft();
    public static final Vector3D RIGHT = newRight();
    public static final Vector3D FORWARD = newForward();
    public static final Vector3D BACKWARD = newBackward();
    public static final Vector3D ZERO = newZeros();

    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void set(Vector3D other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.w = other.w;
    }
}
