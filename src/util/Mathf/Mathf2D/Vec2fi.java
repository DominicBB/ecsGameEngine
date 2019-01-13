package util.Mathf.Mathf2D;

import rendering.drawers.fill.Rasterfi;
import util.Mathf.Mathfi;

public class Vec2fi {

    public int x;
    public int y;

    public final int D_SHIFT;
    public final int D_FACTOR;

    public Vec2fi(int d_SHIFT) {
        this.D_SHIFT = d_SHIFT;
        this.D_FACTOR = 1 << D_SHIFT;
    }

    public Vec2fi(int x, int y, int d_SHIFT) {
        this(d_SHIFT);
        this.x = x;
        this.y = y;
    }

    public Vec2fi(float x, float y, int d_SHIFT) {
        this(d_SHIFT);
        this.x = Mathfi.floatToFixed(x, D_FACTOR);
        this.y = Mathfi.floatToFixed(y, D_FACTOR);
    }

    public Vec2fi(int x, int y) {
        this(0);
        this.x = x;
        this.y = y;
    }

    public Vec2fi(Vec2f v, int d_SHIFT) {
        this(d_SHIFT);
        this.x = Mathfi.floatToFixed(v.x, D_FACTOR);
        this.y = Mathfi.floatToFixed(v.y, D_FACTOR);
    }

    public Vec2fi minus(Vec2fi other) {
        return new Vec2fi(this.x - other.x, this.y - other.y);
    }

    public Vec2fi absDiff(Vec2fi other) {
        return new Vec2fi(Math.abs(this.x - other.x), Math.abs(this.y - other.y));
    }

    public Vec2fi plus(Vec2fi other) {
        return new Vec2fi(this.x + other.x, this.y + other.y);
    }

    public Vec2fi mul(int scaler) {
        return new Vec2fi(this.x * scaler, this.y * scaler);
    }

    public void scale(int scaler) {
        this.x = x * scaler;
        this.y = y * scaler;
    }

    public Vec2fi divide(int divider) {
        return new Vec2fi(this.x / divider, this.y / divider);
    }

    public int dotProduct(Vec2fi other) {
        return (other.x * other.x) + (other.y * other.x);
    }

    public Vec2fi unit() {
        int m = this.magnitude();
        return new Vec2fi(this.x / m, this.y / m);
    }

    public int magnitude() {
        return (int) Math.sqrt((this.x * this.x) + (this.y * this.y));
    }


    public Vec2fi maxValues(Vec2fi other) {
        Vec2fi maxValues = Vec2fi.newZeros(other.D_SHIFT);
        maxValues.x = Math.max(x, other.x);
        maxValues.y = Math.max(y, other.y);
        return maxValues;
    }

    public Vec2fi minValues(Vec2fi other) {
        Vec2fi minValues = Vec2fi.newZeros(other.D_SHIFT);
        minValues.x = Math.min(x, other.x);
        minValues.y = Math.min(y, other.y);
        return minValues;
    }

    public int minVal() {
        int min = x;
        return Math.min(y, min);
    }

    public int maxVal() {
        int max = x;
        return Math.max(y, max);
    }

    public String toString() {
        return "[" + x + "," + y + "]";
    }

    public static Vec2fi newDown(int D_SHIFT) {
        return new Vec2fi(0, -1 << D_SHIFT, D_SHIFT);
    }

    public static Vec2fi newUp(int D_SHIFT) {
        return new Vec2fi(0, 1 << D_SHIFT, D_SHIFT);
    }

    public static Vec2fi newLeft(int D_SHIFT) {
        return new Vec2fi(-1 << D_SHIFT, 0, D_SHIFT);
    }

    public static Vec2fi newRight(int D_SHIFT) {
        return new Vec2fi(1 << D_SHIFT, 0, D_SHIFT);
    }

    public static Vec2fi newOnes(int D_SHIFT) {
        return new Vec2fi(1 << D_SHIFT, 1 << D_SHIFT, D_SHIFT);
    }

    public static Vec2fi newZeros(int D_SHIFT) {
        return new Vec2fi(0, 0, D_SHIFT);
    }

    public static Vec2fi newCopy(Vec2fi toCopy) {
        return new Vec2fi(toCopy.x, toCopy.y, toCopy.D_SHIFT);
    }

    public static final Vec2fi ZERO = newZeros(0);

    public void componentAdd(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void add_unsafe(Vec2fi other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void add_safe(Vec2fi other) {
        int shift = other.D_SHIFT - D_SHIFT;
        if (shift >= 0) {
            this.x += (other.x >> shift);
            this.y += (other.y >> shift);
        }
        shift = -shift;
        this.x += (other.x << shift);
        this.y += (other.y << shift);

    }

    public void set_unsafe(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set_unsafe(Vec2fi other) {
        this.x = other.x;
        this.y = other.y;
    }

    public void set_safe(Vec2fi other) {
        int shift = other.D_SHIFT - D_SHIFT;
        if (shift >= 0) {
            this.x = other.x >> shift;
            this.y = other.y >> shift;
            return;
        }
        shift = -shift;
        this.x = other.x << shift;
        this.y = other.y << shift;

    }

    public void set(Vec2f vec2f) {
        this.x = Rasterfi.floatToFixed(vec2f.x);
        this.y = Rasterfi.floatToFixed(vec2f.y);
    }

   /* public static void lerp(Vec2fi start, Vec2fi destination, int lerpAmt) {
        start.add_unsafe(destination.minus(start).mul_unsafe(lerpAmt));
    }

    public Vec2fi lerp(Vec2fi destination, int lerpAmt) {
int l_x = Ma(destination.x - x)
        return destination.minus(this).mul_unsafe(lerpAmt).plus(this);
    }*/
}
