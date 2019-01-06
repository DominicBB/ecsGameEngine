package util.Mathf.Mathf3D;

import util.Mathf.Mathfi;

public class Vec4fi {
    public int x;
    public int y;
    public int z;
    public int w;

    public final int D_SHIFT;
    public final int D_FACTOR;

    public Vec4fi(int D_SHIFT) {
        this.D_SHIFT = D_SHIFT;
        this.D_FACTOR = 1 << D_SHIFT;
    }

    public Vec4fi(int x, int y, int z, int w, int D_SHIFT) {
        this(D_SHIFT);
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4fi(Vec4f v, int D_SHIFT) {
        this(D_SHIFT);
        set(v);
    }

    public void set(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void set(Vec4f v) {
        this.x = Mathfi.floatToFixed(v.x, D_FACTOR);
        this.y = Mathfi.floatToFixed(v.y, D_FACTOR);
        this.z = Mathfi.floatToFixed(v.z, D_FACTOR);
        this.w = Mathfi.floatToFixed(v.w, D_FACTOR);
    }

    public void set(Vec4fi v) {
        int shift = v.D_SHIFT - D_SHIFT;
        if (shift >= 0) {
            this.x = v.x >> shift;
            this.y = v.y >> shift;
            this.z = v.z >> shift;
            this.w = v.w >> shift;
            return;
        }
        shift = -shift;
        this.x = v.x << shift;
        this.y = v.y << shift;
        this.z = v.z << shift;
        this.w = v.w << shift;

    }

    public void set_unsafe(Vec4fi v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;

    }

    public void add_safe(Vec4fi v) {
        int shift = v.D_SHIFT - D_SHIFT;
        if (shift >= 0) {
            this.x += (v.x >> shift);
            this.y += (v.y >> shift);
            this.z += (v.z >> shift);
            this.w += (v.w >> shift);
            return;
        }
        shift = -shift;
        this.x += (v.x << shift);
        this.y += (v.y << shift);
        this.z += (v.z << shift);
        this.w += (v.w << shift);
    }

    public void add_unsafe(Vec4fi v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        this.w += v.w;
    }

    public void mul_unsafe(Vec4fi v, int left_val_shift) {
        this.x = Mathfi.multiply(v.x, x, left_val_shift, D_SHIFT);
        this.y = Mathfi.multiply(v.y, y, left_val_shift, D_SHIFT);
        this.z = Mathfi.multiply(v.z, z, left_val_shift, D_SHIFT);
        this.w = Mathfi.multiply(v.w, w, left_val_shift, D_SHIFT);
    }

    public void mul_unsafe(int s, int left_val_shift) {
        this.x = Mathfi.multiply(x, s, left_val_shift, D_SHIFT);
        this.y = Mathfi.multiply(y, s, left_val_shift, D_SHIFT);
        this.z = Mathfi.multiply(z, s, left_val_shift, D_SHIFT);
        this.w = Mathfi.multiply(w, s, left_val_shift, D_SHIFT);
    }

    public void truncate() {
        this.x >>= D_SHIFT;
        this.y >>= D_SHIFT;
        this.z >>= D_SHIFT;
        this.w >>= D_SHIFT;
    }

    public void truncate_conserve_format() {
        this.x = Mathfi.truncateTo_0DShift(x, D_SHIFT);
        this.y = Mathfi.truncateTo_0DShift(y, D_SHIFT);
        this.z = Mathfi.truncateTo_0DShift(z, D_SHIFT);
        this.w = Mathfi.truncateTo_0DShift(w, D_SHIFT);
    }

    public void toInt() {
        this.x >>= D_SHIFT;
        this.y >>= D_SHIFT;
        this.z >>= D_SHIFT;
        this.w >>= D_SHIFT;
    }

    public static Vec4fi newDown(int D_SHIFT) {
        return new Vec4fi(0, -1 << D_SHIFT, 0, 0, D_SHIFT);
    }

    public static Vec4fi newUp(int D_SHIFT) {
        return new Vec4fi(0, 1 << D_SHIFT, 0, 0, D_SHIFT);
    }

    public static Vec4fi newLeft(int D_SHIFT) {
        return new Vec4fi(-1 << D_SHIFT, 0, 0, 0, D_SHIFT);
    }

    public static Vec4fi newRight(int D_SHIFT) {
        return new Vec4fi(1 << D_SHIFT, 0, 0, 0, D_SHIFT);
    }

    public static Vec4fi newOnes(int D_SHIFT) {
        return new Vec4fi(1 << D_SHIFT, 1 << D_SHIFT, 1 << D_SHIFT, 1 << D_SHIFT, D_SHIFT);
    }

    public static Vec4fi newZeros(int D_SHIFT) {
        return new Vec4fi(0, 0, 0, 0, D_SHIFT);
    }

    public static Vec4fi newCopy(Vec4fi toCopy) {
        return new Vec4fi(toCopy.x, toCopy.y, toCopy.z, toCopy.w, toCopy.D_SHIFT);
    }

    public static final Vec4fi ZERO = newZeros(0);
}
