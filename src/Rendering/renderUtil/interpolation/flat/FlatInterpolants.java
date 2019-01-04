package Rendering.renderUtil.interpolation.flat;

import Rendering.renderUtil.VertexOut;

public class FlatInterpolants {
    public int xInt;

    public float x, z;
    public float invW;

    public float tex_u, tex_v;

    public float spec_u, spec_v;

    public final FlatLerper_E flatLerper_e;

    public FlatInterpolants(FlatLerper_E flatLerper_e) {
        this.flatLerper_e = flatLerper_e;
    }


    public FlatInterpolants(float x, float z, float invW,
                            float tex_u, float tex_v, float spec_u, float spec_v) {
        this.flatLerper_e = null;
        reset(x, z, invW, tex_u, tex_v, spec_u, spec_v);
    }

    public void reset(VertexOut v) {
        reset(v.p_proj.x, v.p_proj.z, v.invW, v.texCoord.x, v.texCoord.y, v.specCoord.x, v.specCoord.y);
    }

    public void reset(float x, float z, float invW,
                      float tex_u, float tex_v, float spec_u, float spec_v) {
        this.x = x;
        this.z = z;
        this.invW = invW;

        this.tex_u = tex_u;
        this.tex_v = tex_v;
        this.spec_u = spec_u;
        this.spec_v = spec_v;
    }


}
