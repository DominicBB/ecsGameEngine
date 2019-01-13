package rendering.renderUtil.interpolation.flat;

import rendering.renderUtil.VOutfi;

public class FlatInterpolants {
    public int xInt;

    public int x, z;
    public int invW;

    public int tex_u, tex_v;

    public int spec_u, spec_v;

    public final FlatLerper_E flatLerper_e;

    public FlatInterpolants(FlatLerper_E flatLerper_e) {
        this.flatLerper_e = flatLerper_e;
    }


    public FlatInterpolants(int x, int z, int invW,
                            int tex_u, int tex_v, int spec_u, int spec_v) {
        this.flatLerper_e = null;
        reset(x, z, invW, tex_u, tex_v, spec_u, spec_v);
    }

    public void reset(VOutfi v) {
        reset(v.p_proj.x, v.p_proj.z, v.invW, v.texCoord.x, v.texCoord.y, v.specCoord.x, v.specCoord.y);
    }

    public void reset(int x, int z, int invW,
                      int tex_u, int tex_v, int spec_u, int spec_v) {
        this.x = x;
        this.z = z;
        this.invW = invW;

        this.tex_u = tex_u;
        this.tex_v = tex_v;
        this.spec_u = spec_u;
        this.spec_v = spec_v;
    }


}
