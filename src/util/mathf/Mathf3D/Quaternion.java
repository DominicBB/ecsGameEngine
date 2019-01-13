package util.mathf.Mathf3D;

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

    public static Quaternion values(float x, float y, float z, float w) {
        return new Quaternion(x, y, z, w);
    }

    /**
     * @param angle in radians
     * @param axis
     */
    public Quaternion(float angle, Vec4f axis) {
        float halfA = angle / 2f;
        w = (float) Math.cos(halfA);
        float sinHalfA = (float) Math.sin(halfA);
        x = axis.x * sinHalfA;
        y = axis.y * sinHalfA;
        z = axis.z * sinHalfA;
    }

    public static Quaternion angleAxis(float angle, Vec4f axis) {
        return new Quaternion(angle, axis);
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

    public float dotProduct(Quaternion other) {
        return x * other.x + y * other.y + z * other.z + w * other.w;
    }

    public Quaternion multiply(Quaternion q) {
        return new Quaternion(
                x * q.getW() + w * q.getX() + y * q.getZ() - z * q.getY(),
                y * q.getW() + w * q.getY() + z * q.getX() - x * q.getZ(),
                z * q.getW() + w * q.getZ() + x * q.getY() - y * q.getX(),
                w * q.getW() - x * q.getX() - y * q.getY() - z * q.getZ()
        );
    }

    public Quaternion multiply(Vec4f v) {
        return new Quaternion(
                w * v.x + y * v.z - z * v.y,
                w * v.y + z * v.x - x * v.z,
                w * v.z + x * v.y - y * v.x,
                -x * v.x - y * v.y - z * v.z
        );
    }

    public Vec4f rotate(Vec4f v) {
        Quaternion q = multiply(v).multiply(conjugate());
        return new Vec4f(q.x, q.y, q.z);
    }

    public Matrix4x4 toMatrix4x4() {
        return Matrix4x4.newRotation(this);
    }

    public static Quaternion newIdentity() {
        return new Quaternion(0f, 0f, 0f, 1f);
    }

    public static Quaternion newFromRotationMatrix4x4(Matrix4x4 rotMatrix) {
        return null; //TODO:
    }

    private static final float LERP_INSTEAD_THREASHHOLD = 0.995f;

    public static Quaternion slerp(Quaternion from, Quaternion to, float t) {
        from.normalise();
        to.normalise();

        float dot = from.dotProduct(to);

        if (dot < 0.0f) {
            to = to.scale(-1);
            dot = -dot;
        }

        if (dot > LERP_INSTEAD_THREASHHOLD) {
            return lerp(from, to, t);
        }

        float theta_0 = (float) Math.acos(dot);
        float theta = theta_0 * t;
        float sin_theta = (float) Math.sin(theta);
        float sin_theta_0 = (float) Math.sin(theta_0);

        float s0 = (float) Math.cos(theta) - dot * sin_theta / sin_theta_0;
        float s1 = sin_theta / sin_theta_0;

        return from.scale(s0).plus(to.scale(s1));
    }

    public static Quaternion lerp(Quaternion from, Quaternion to, float t) {
        Quaternion result = from.plus(to.minus(from)).scale(t);
        result.normalise();
        return result;
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
