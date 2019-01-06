package Rendering.renderUtil.interpolation.flat;

public class FlatLerper_E extends FlatLerper_Base{
    public int x;

    public FlatLerper_E() {
    }

    public FlatLerper_E(int x, int z, int invW, int tex_u, int tex_v, int spec_u, int spec_v) {
        reset(x, z, invW, tex_u, tex_v, spec_u, spec_v);
    }

    public void reset(int x, int z, int invW, int tex_u, int tex_v, int spec_u, int spec_v) {
        this.x = x;
        this.z = z;
        this.invW = invW;
        this.tex_u = tex_u;
        this.tex_v = tex_v;
        this.spec_u = spec_u;
        this.spec_v = spec_v;
    }


    public void lerp(FlatInterpolants interpolants) {
        interpolants.x += x;
        interpolants.z += z;
        interpolants.invW += invW;
        interpolants.tex_u += tex_u;
        interpolants.tex_v += tex_v;
        /*interpolants.spec_u += spec_u;
        interpolants.spec_v += spec_v;*/
    }
}
