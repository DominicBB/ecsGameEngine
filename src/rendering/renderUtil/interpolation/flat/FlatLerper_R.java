package rendering.renderUtil.interpolation.flat;

public class FlatLerper_R extends FlatLerper_Base {

    public FlatLerper_R() {
    }

    public FlatLerper_R(int z, int invW, int tex_u, int tex_v, int spec_u, int spec_v) {
        reset(z, invW, tex_u, tex_v, spec_u, spec_v);
    }

    public void reset(int z, int invW, int tex_u, int tex_v, int spec_u, int spec_v) {
        this.z = z;
        this.invW = invW;
        this.tex_u = tex_u;
        this.tex_v = tex_v;
        this.spec_u = spec_u;
        this.spec_v = spec_v;
    }

    public void lerp(FlatInterpolants interpolants) {
        interpolants.z += z;
        interpolants.invW += invW;
        interpolants.tex_u += tex_u;
        interpolants.tex_v += tex_v;
        /*interpolants.spec_u += spec_u;
        interpolants.spec_v += spec_v;*/
    }
}
