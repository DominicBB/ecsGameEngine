package util.Mathf.Mathf3D;

public class Vec4fiImmut {
    public final int x;
    public final int y;
    public final int z;
    public final int w;

    public final int D_SHIFT;
    public final int D_FACTOR;

    public Vec4fiImmut(int x, int y, int z, int w, int D_SHIFT) {
        this.D_SHIFT = D_SHIFT;
        this.D_FACTOR = 1 << D_SHIFT;
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4fiImmut(Vec4fi v, int D_SHIFT) {
        this.D_SHIFT = D_SHIFT;
        this.D_FACTOR = 1 << D_SHIFT;
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    public Vec4fi add_safe(Vec4fi v) {
        int shift = v.D_SHIFT - D_SHIFT;
        if (shift >= 0) {
            return createVec4fi_sr_add(v, shift);
        }
        shift = -shift;
        return createVec4fi_sl_add(v, shift);
    }


    public Vec4fi add_unsafe(Vec4fi v) {
        return createVec4fi_add(v);
    }


    private Vec4fi createVec4fi_sl_add(Vec4fi v, int shift) {
        int n_x, n_y, n_z, n_w;
        n_x = this.x + (v.x << shift);
        n_y = this.y + (v.y << shift);
        n_z = this.z + (v.z << shift);
        n_w = this.w + (v.w << shift);
        return new Vec4fi(n_x, n_y, n_z, n_w, D_SHIFT);
    }

    private Vec4fi createVec4fi_sr_add(Vec4fi v, int shift) {
        int n_x, n_y, n_z, n_w;
        n_x = this.x + (v.x >> shift);
        n_y = this.y + (v.y >> shift);
        n_z = this.z + (v.z >> shift);
        n_w = this.w + (v.w >> shift);
        return new Vec4fi(n_x, n_y, n_z, n_w, D_SHIFT);
    }

    private Vec4fi createVec4fi_add(Vec4fi v) {
        int n_x, n_y, n_z, n_w;
        n_x = this.x + (v.x);
        n_y = this.y + (v.y);
        n_z = this.z + (v.z);
        n_w = this.w + (v.w);
        return new Vec4fi(n_x, n_y, n_z, n_w, D_SHIFT);
    }
}


