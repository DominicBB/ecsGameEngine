package util.Mathf.Mathf3D;


import util.Mathf.Mathf;

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
        return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z/*, this.w - other.w*/);
    }

    public Vector3D absDiff(Vector3D other) {
        return new Vector3D(Math.abs(this.x - other.x), Math.abs(this.y - other.y), Math.abs(this.z - other.z));
    }

    public Vector3D plus(Vector3D other) {
        return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z/*, this.w + other.w*/);
    }

    public void add(Vector3D toAdd) {
        this.x += toAdd.x;
        this.y += toAdd.y;
        this.z += toAdd.z;
//        this.w = w + toAdd.w;
    }

    public Vector3D mul(float scaler) {
        return new Vector3D(this.x * scaler, this.y * scaler, this.z * scaler/*, this.w * scaler*/);
    }

    public void scale(float scaler) {
        this.x *= scaler;
        this.y *= scaler;
        this.z *= scaler;
        this.w *= scaler;
    }

    public Vector3D divide(float divider) {
        return new Vector3D(this.x / divider, this.y / divider, this.z / divider/*, this.w / divider*/);
    }

    public void mutDivide(float divider) {
        this.x = x / divider;
        this.y = y / divider;
        this.z = z / divider;
        /*, this.w =w/ divider;*/

    }


    public Vector3D normal() {
        float invM = 1f / this.magnitude();
        return new Vector3D(this.x * invM, this.y * invM, this.z * invM);
    }

    public void normalise() {
        float invM = 1f / this.magnitude();
        this.x = x * invM;
        this.y = y * invM;
        this.z = z * invM;
    }

    public float magnitude() {
        return (float) Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
    }

    public float sqrMagnitude() {
        return (this.x * this.x) + (this.y * this.y) + (this.z * this.z);
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

    public void add(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
    }

    public static void componentMulNonAlloc(Vector3D out, Vector3D other) {
        out.x *= other.x;
        out.y *= other.y;
        out.z *= other.z;
        out.w *= other.w;
    }

    public double cosTheta(Vector3D other) {
        float mag = this.magnitude();
        float oMag = other.magnitude();
        return this.dotProduct(other) / mag / oMag;
    }

    public Vector3D maxValues(Vector3D other) {
        Vector3D maxValues = Vector3D.newZeros();
        maxValues.x = Mathf.max(x, other.x);
        maxValues.y = Mathf.max(y, other.y);
        maxValues.z = Mathf.max(z, other.z);
        return maxValues;
    }

    public Vector3D minValues(Vector3D other) {
        Vector3D minValues = Vector3D.newZeros();
        minValues.x = Mathf.min(x, other.x);
        minValues.y = Mathf.min(y, other.y);
        minValues.z = Mathf.min(z, other.z);
        return minValues;
    }

    public float minVal() {
        float min = x;
        min = Mathf.min(y, min);
        return Mathf.min(z, min);
    }

    public float maxVal() {
        float max = x;
        max = Mathf.max(y, max);
        return Mathf.max(z, max);
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

    public static Vector3D newCopy(Vector3D toCopy) {
        return new Vector3D(toCopy.x, toCopy.y, toCopy.z, toCopy.w);
    }


    public static final Vector3D UP = newUp();
    public static final Vector3D DOWN = newDown();
    public static final Vector3D LEFT = newLeft();
    public static final Vector3D RIGHT = newRight();
    public static final Vector3D FORWARD = newForward();
    public static final Vector3D BACKWARD = newBackward();
    public static final Vector3D ZERO = newZeros();
    public static final Vector3D ONE = newOnes();

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

    public float getComponentValue(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            case 3:
                return w;
            default:
                return w;
        }
    }

    public static void lerp(Vector3D start, Vector3D destination, float lerpAmt) {
        start.add(destination.minus(start).mul(lerpAmt));
    }

    public static void lerpWithW(Vector3D start, Vector3D destination, float lerpAmt) {
        start.add(destination.minus(start).mul(lerpAmt));
        start.w = Mathf.lerp(start.w, destination.w, lerpAmt);
    }

    public Vector3D lerp(Vector3D destination, float lerpAmt) {
        return destination.minus(this).mul(lerpAmt).plus(this);
    }

    public Vector3D lerpWithW(Vector3D destination, float lerpAmt) {
        Vector3D res = destination.minus(this);
        res.x = res.x * lerpAmt + x;
        res.y = res.y * lerpAmt + y;
        res.z = res.z * lerpAmt + z;
        res.w = Mathf.lerp(w, destination.w, lerpAmt);
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector3D) {
            Vector3D v = (Vector3D) obj;
            return (x == v.x && y == v.y && z == v.z);
        }
        return false;
    }
}
