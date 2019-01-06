package util.Mathf.Mathf3D;


import util.Mathf.Mathf;

public class Vec4f {
    public float x;
    public float y;
    public float z;
    public float w;

    public Vec4f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1f;
    }

    public Vec4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4f(float w) {
        this.w = w;
    }

    public Vec4f minus(Vec4f other) {
        return new Vec4f(this.x - other.x, this.y - other.y, this.z - other.z/*, this.w - other.w*/);
    }

    public Vec4f absDiff(Vec4f other) {
        return new Vec4f(Math.abs(this.x - other.x), Math.abs(this.y - other.y), Math.abs(this.z - other.z));
    }

    public Vec4f plus(Vec4f other) {
        return new Vec4f(this.x + other.x, this.y + other.y, this.z + other.z/*, this.w + other.w*/);
    }

    public void add(Vec4f toAdd) {
        this.x += toAdd.x;
        this.y += toAdd.y;
        this.z += toAdd.z;
//        this.w = w + toAdd.w;
    }

    public Vec4f mul(float scaler) {
        return new Vec4f(this.x * scaler, this.y * scaler, this.z * scaler/*, this.w * scaler*/);
    }

    public void scale(float scaler) {
        this.x *= scaler;
        this.y *= scaler;
        this.z *= scaler;
        this.w *= scaler;
    }

    public Vec4f divide(float divider) {
        return new Vec4f(this.x / divider, this.y / divider, this.z / divider/*, this.w / divider*/);
    }

    public void mutDivide(float divider) {
        this.x = x / divider;
        this.y = y / divider;
        this.z = z / divider;
        /*, this.w =w/ divider;*/

    }


    public Vec4f normal() {
        float invM = 1f / this.magnitude();
        return new Vec4f(this.x * invM, this.y * invM, this.z * invM);
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

    public Vec4f crossProduct(Vec4f other) {
        float x = this.y * other.z - this.z * other.y;
        float y = this.z * other.x - this.x * other.z;
        float z = this.x * other.y - this.y * other.x;
        return new Vec4f(x, y, z);
    }

    public float dotProduct(Vec4f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vec4f componentMul(Vec4f other) {
        return new Vec4f(
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

    public static void componentMulNonAlloc(Vec4f out, Vec4f other) {
        out.x *= other.x;
        out.y *= other.y;
        out.z *= other.z;
        out.w *= other.w;
    }

    public double cosTheta(Vec4f other) {
        float mag = this.magnitude();
        float oMag = other.magnitude();
        return this.dotProduct(other) / mag / oMag;
    }

    public Vec4f maxValues(Vec4f other) {
        Vec4f maxValues = Vec4f.newZeros();
        maxValues.x = Mathf.max(x, other.x);
        maxValues.y = Mathf.max(y, other.y);
        maxValues.z = Mathf.max(z, other.z);
        return maxValues;
    }

    public Vec4f minValues(Vec4f other) {
        Vec4f minValues = Vec4f.newZeros();
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

    public static Vec4f newDown() {
        return new Vec4f(0f, -1f, 0f);
    }

    public static Vec4f newUp() {
        return new Vec4f(0f, 1f, 0f);
    }

    public static Vec4f newLeft() {
        return new Vec4f(-1f, 0f, 0f);
    }

    public static Vec4f newRight() {
        return new Vec4f(1f, 0f, 0f);
    }

    public static Vec4f newForward() {
        return new Vec4f(0f, 0f, 1f);
    }

    public static Vec4f newBackward() {
        return new Vec4f(0f, 0f, -1f);
    }

    public static Vec4f newOnes() {
        return new Vec4f(1f, 1f, 1f);
    }

    public static Vec4f newZeros() {
        return new Vec4f(0f, 0f, 0f);
    }

    public static Vec4f newCopy(Vec4f toCopy) {
        return new Vec4f(toCopy.x, toCopy.y, toCopy.z, toCopy.w);
    }


    public static final Vec4f UP = newUp();
    public static final Vec4f DOWN = newDown();
    public static final Vec4f LEFT = newLeft();
    public static final Vec4f RIGHT = newRight();
    public static final Vec4f FORWARD = newForward();
    public static final Vec4f BACKWARD = newBackward();
    public static final Vec4f ZERO = newZeros();
    public static final Vec4f ONE = newOnes();

    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void set(Vec4f other) {
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

    public static void lerp(Vec4f start, Vec4f destination, float lerpAmt) {
        start.add(destination.minus(start).mul(lerpAmt));
    }

    public static void lerpWithW(Vec4f start, Vec4f destination, float lerpAmt) {
        start.add(destination.minus(start).mul(lerpAmt));
        start.w = Mathf.lerp(start.w, destination.w, lerpAmt);
    }

    public Vec4f lerp(Vec4f destination, float lerpAmt) {
        return destination.minus(this).mul(lerpAmt).plus(this);
    }

    public Vec4f lerpWithW(Vec4f destination, float lerpAmt) {
        Vec4f res = destination.minus(this);
        res.x = res.x * lerpAmt + x;
        res.y = res.y * lerpAmt + y;
        res.z = res.z * lerpAmt + z;
        res.w = Mathf.lerp(w, destination.w, lerpAmt);
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vec4f) {
            Vec4f v = (Vec4f) obj;
            return (x == v.x && y == v.y && z == v.z);
        }
        return false;
    }
}
