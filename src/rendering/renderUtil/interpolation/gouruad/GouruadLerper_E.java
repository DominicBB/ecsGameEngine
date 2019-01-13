package rendering.renderUtil.interpolation.gouruad;

public class GouruadLerper_E extends GouruadLerper_Base{
    public int x;

    public GouruadLerper_E() {
    }

    public GouruadLerper_E(int x, int z, int invW, int specularity, int color_r, int color_g, int color_b,
                           int color_a, int tex_u, int tex_v, int spec_u, int spec_v) {
       reset(x, z, invW, specularity, color_r, color_g, color_b, color_a, tex_u, tex_v, spec_u, spec_v);
    }

    public void reset(int x, int z, int invW, int specularity, int color_r, int color_g, int color_b,
                      int color_a, int tex_u, int tex_v, int spec_u, int spec_v) {
        this.x = x;
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
        interpolants.x += x;
        lerp(interpolants, this);
        /*interpolants.z += z;

        interpolants.color_r += color_r;
        interpolants.color_g += color_g;
        interpolants.color_b += color_b;
        interpolants.color_a += color_a;

        interpolants.tex_u += tex_u;
        interpolants.tex_v += tex_v;

        *//*interpolants.spec_u += spec_u;
        interpolants.spec_v += spec_v;*//*

        interpolants.invW += invW;
        interpolants.specularity += specularity;*/
    }
}
