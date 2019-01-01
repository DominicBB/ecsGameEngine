package Rendering.renderUtil.interpolation.flat;

public class FlatInterpolants {
    public int xInt;

    public float x, z;
    public float invW;

    public float color_r, color_g, color_b, color_a;
    public float tex_u, tex_v;

    public float spec_u, spec_v;
    public float specularity;

    public FlatLerper_E flatLerper_e;
    public FlatLerper_R flatLerper_r;

    public FlatInterpolants() {
    }

    public FlatInterpolants(float x, float z, float invW, float color_r, float color_g, float color_b, float color_a,
                            float tex_u, float tex_v, float spec_u, float spec_v, float specularity) {
        reset(x, z, invW, color_r, color_g, color_b, color_a, tex_u, tex_v, spec_u, spec_v, specularity);
    }

    public void reset(float x, float z, float invW, float color_r, float color_g, float color_b, float color_a,
                      float tex_u, float tex_v, float spec_u, float spec_v, float specularity) {
        this.x = x;
        this.z = z;
        this.invW = invW;
        this.color_r = color_r;
        this.color_g = color_g;
        this.color_b = color_b;
        this.color_a = color_a;
        this.tex_u = tex_u;
        this.tex_v = tex_v;
        this.spec_u = spec_u;
        this.spec_v = spec_v;
        this.specularity = specularity;
    }

    /*public void setFlatLerper_e(FlatLerper_E flatLerper_e) {
        this.flatLerper_e = flatLerper_e;
    }

    public void setFlatLerper_r(FlatLerper_R flatLerper_r) {
        this.flatLerper_r = flatLerper_r;
    }*/
}
