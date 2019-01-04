package Rendering.renderUtil.interpolation.gouruad;

import Rendering.renderUtil.VertexOut;
import Rendering.renderUtil.interpolation.IInterpolants;

public class GouruadInterpolants implements IInterpolants {
    public int xInt;

    public float x, z;
    public float invW;
    public float specularity;

    public float color_r, color_g, color_b, color_a;
    public float tex_u, tex_v;
    public float spec_u, spec_v;

    public final GouruadLerper_E gouruadLerper_E;

    public GouruadInterpolants(GouruadLerper_E gouruadLerper_E) {
        this.gouruadLerper_E = gouruadLerper_E;
    }

    public GouruadInterpolants(float x, float z, float invW, float specularity, float color_r, float color_g,
                               float color_b, float color_a, float tex_u, float tex_v, float spec_u, float spec_v) {
        this.gouruadLerper_E = null;
        reset(x, z, invW, specularity, color_r, color_g, color_b, color_a, tex_u, tex_v, spec_u, spec_v);
    }

    public void reset(VertexOut v) {
        reset(v.p_proj.x, v.p_proj.z, v.invW, v.spec, v.surfaceColor.x, v.surfaceColor.y, v.surfaceColor.z,
                v.surfaceColor.w, v.texCoord.x, v.texCoord.y, v.specCoord.x, v.specCoord.y);
    }

    public void reset(float x, float z, float invW, float specularity, float color_r, float color_g,
                      float color_b, float color_a, float tex_u, float tex_v, float spec_u, float spec_v) {
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
}
