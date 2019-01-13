package util.mathf.Mathf2D;


public class Vec2f {
    public float x;
    public float y;

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2f(float x, float y, float w) {
        this.x = x;
        this.y = y;
    }

    public Vec2f minus(Vec2f other) {
        return new Vec2f(this.x - other.x, this.y - other.y);
    }

    public Vec2f absDiff(Vec2f other) {
        return new Vec2f(Math.abs(this.x - other.x), Math.abs(this.y - other.y));
    }

    public Vec2f plus(Vec2f other) {
        return new Vec2f(this.x + other.x, this.y + other.y);
    }

    public Vec2f mul(float scaler) {
        return new Vec2f(this.x * scaler, this.y * scaler);
    }

    public void scale(float scaler) {
        this.x = x * scaler;
        this.y = y * scaler;
    }

    public Vec2f divide(float divider) {
        return new Vec2f(this.x / divider, this.y / divider);
    }

    public float dotProduct(Vec2f other) {
        return (other.x * other.x) + (other.y * other.x);
    }

    public Vec2f unit() {
        float m = this.magnitude();
        return new Vec2f(this.x / m, this.y / m);
    }

    public float magnitude() {
        return (float) Math.sqrt((this.x * this.x) + (this.y * this.y));
    }


    public Vec2f maxValues(Vec2f other) {
        Vec2f maxValues = Vec2f.newZeros();
        maxValues.x = Math.max(x, other.x);
        maxValues.y = Math.max(y, other.y);
        return maxValues;
    }

    public Vec2f minValues(Vec2f other) {
        Vec2f minValues = Vec2f.newZeros();
        minValues.x = Math.min(x, other.x);
        minValues.y = Math.min(y, other.y);
        return minValues;
    }

    public float minVal() {
        float min = x;
        return Math.min(y, min);
    }

    public float maxVal() {
        float max = x;
        return Math.max(y, max);
    }

    public String toString() {
        return "[" + x + "," + y + "]";
    }

    public static Vec2f newDown() {
        return new Vec2f(0f, -1f);
    }

    public static Vec2f newUp() {
        return new Vec2f(0f, 1f);
    }

    public static Vec2f newLeft() {
        return new Vec2f(-1f, 0f);
    }

    public static Vec2f newRight() {
        return new Vec2f(1f, 0f);
    }

    public static Vec2f newOnes() {
        return new Vec2f(1f, 1f);
    }

    public static Vec2f newZeros() {
        return new Vec2f(0f, 0f);
    }

    public static Vec2f newCopy(Vec2f toCopy) {
        return new Vec2f(toCopy.x, toCopy.y);
    }

    public static final Vec2f ZERO = newZeros();

    public void componentAdd(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void add(Vec2f other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vec2f other) {
        this.x = other.x;
        this.y = other.y;
    }

    public static void lerp(Vec2f start, Vec2f destination, float lerpAmt) {
        start.add(destination.minus(start).mul(lerpAmt));
    }

    public Vec2f lerp(Vec2f destination, float lerpAmt) {
        return destination.minus(this).mul(lerpAmt).plus(this);
    }
}
