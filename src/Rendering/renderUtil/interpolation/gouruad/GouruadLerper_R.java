package Rendering.renderUtil.interpolation.gouruad;

public class GouruadLerper_R  {
    public int xInt;
    public float z;
    public float invW;
    public float specularity;

    public float color_r, color_g, color_b, color_a;//final
    public float tex_u, tex_v;//final
    public float spec_u, spec_v; // final

    public GouruadLerper_R() {
    }

    public GouruadLerper_R(int x, float z, float invW, float specularity, float color_r, float color_g, float color_b,
                           float color_a, float tex_u, float tex_v, float spec_u, float spec_v) {
        reset(x, z, invW, specularity, color_r, color_g, color_b, color_a, tex_u, tex_v, spec_u, spec_v);
    }

    public void reset(int x, float z, float invW, float specularity, float color_r, float color_g, float color_b,
                      float color_a, float tex_u, float tex_v, float spec_u, float spec_v) {
        this.xInt = x;
        this.z = z;
        this.invW = invW;
        this.specularity = specularity;
        this.color_r = color_r;
        this.color_g = color_g;
        this.color_b = color_b;
        this.color_a = color_a;
        this.tex_u = tex_u;
        this.tex_v = tex_v;
        this.spec_u = spec_u;
        this.spec_v = spec_v;
    }

    public void lerp(GouruadInterpolants interpolants) {
        interpolants.z += z;

        interpolants.color_r += color_r;
        interpolants.color_g += color_g;
        interpolants.color_b += color_b;
        interpolants.color_a += color_a;

        interpolants.tex_u += tex_u;
        interpolants.tex_v += tex_v;

        interpolants.spec_u += spec_u;
        interpolants.spec_v += spec_v;
        interpolants.specularity += specularity;

        interpolants.invW += invW;
    }
}
