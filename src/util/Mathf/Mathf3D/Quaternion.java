package util.Mathf.Mathf3D;

public class Quaternion {
    private float x;
    private float y;
    private float z;
    private float w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(float theta, Vector3D axis) {
        float halfA = theta / 2f;
        w = (float) Math.cos(halfA);
        float sinHalfA = (float) Math.sin(halfA);
        x = axis.x * sinHalfA;
        y = axis.y * sinHalfA;
        z = axis.z * sinHalfA;
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Quaternion normal() {
        float mag = magnitude();
        return this.divide(mag);
    }

    public Quaternion normalise() {
        float mag = magnitude();
        x = x / mag;
        y = y / mag;
        z = z / mag;
        w = w / mag;
        return this;
    }

    public Quaternion conjugate() {
        return new Quaternion(-x, -y, -z, w);
    }

    public Quaternion divide(float d) {
        return new Quaternion(x / d, y / d, z / d, w / d);
    }

    public Quaternion scale(float s) {
        return new Quaternion(x * s, y * s, z * s, w * s);
    }

    public Quaternion plus(Quaternion q) {
        return new Quaternion(x + q.x, y + q.y, z + q.z, w + q.w);
    }

    public Quaternion minus(Quaternion q) {
        return new Quaternion(x - q.x, y - q.y, z - q.z, w - q.w);
    }

    public Quaternion multiply(Quaternion q) {
        return new Quaternion(
                x * q.getW() + w * q.getX() + y * q.getZ() - z * q.getY(),
                y * q.getW() + w * q.getY() + z * q.getX() - x * q.getZ(),
                z * q.getW() + w * q.getZ() + x * q.getY() - y * q.getX(),
                w * q.getW() - x * q.getX() - y * q.getY() - z * q.getZ()
        );
    }

    public Quaternion multiply(Vector3D v) {
        return new Quaternion(
                w * v.x + y * v.z - z * v.y,
                w * v.y + z * v.x - x * v.z,
                w * v.z + x * v.y - y * v.x,
                -x * v.x - y * v.y - z * v.z
        );
    }

    public Vector3D rotate(Vector3D v) {
        Quaternion q = multiply(v);
        return new Vector3D(q.x,q.y,q.z);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }
}
