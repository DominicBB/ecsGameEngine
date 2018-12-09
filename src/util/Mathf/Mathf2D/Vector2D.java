package util.Mathf.Mathf2D;


public class Vector2D {
    public float x;
    public float y;
    public float w;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
        this.w = 1;
    }

    public Vector2D(float x, float y, float w) {
        this.x = x;
        this.y = y;
        this.w = 1;
    }

    public Vector2D minus(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D absDiff(Vector2D other) {
        return new Vector2D(Math.abs(this.x - other.x), Math.abs(this.y - other.y));
    }

    public Vector2D plus(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D mul(float scaler) {
        return new Vector2D(this.x * scaler, this.y * scaler);
    }

    public void scale(float scaler) {
        this.x = x * scaler;
        this.y = y * scaler;
    }

    public Vector2D divide(float divider) {
        return new Vector2D(this.x / divider, this.y / divider);
    }

    public float dotProduct(Vector2D other) {
        return (other.x * other.x) + (other.y * other.x);
    }

    public Vector2D unit() {
        float m = this.magnitude();
        return new Vector2D(this.x / m, this.y / m);
    }

    public float magnitude() {
        return (float) Math.sqrt((this.x * this.x) + (this.y * this.y));
    }


    public Vector2D maxValues(Vector2D other) {
        Vector2D maxValues = Vector2D.newZeros();
        maxValues.x = Math.max(x, other.x);
        maxValues.y = Math.max(y, other.y);
        return maxValues;
    }

    public Vector2D minValues(Vector2D other) {
        Vector2D minValues = Vector2D.newZeros();
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

    public static Vector2D newDown() {
        return new Vector2D(0f, -1f);
    }

    public static Vector2D newUp() {
        return new Vector2D(0f, 1f);
    }

    public static Vector2D newLeft() {
        return new Vector2D(-1f, 0f);
    }

    public static Vector2D newRight() {
        return new Vector2D(1f, 0f);
    }

    public static Vector2D newOnes() {
        return new Vector2D(1f, 1f);
    }

    public static Vector2D newZeros() {
        return new Vector2D(0f, 0f);
    }

    public static final Vector2D ZERO = newZeros();

    public void componentAdd(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }
}
