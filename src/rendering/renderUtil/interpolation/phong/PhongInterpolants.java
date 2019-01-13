package rendering.renderUtil.interpolation.phong;

import rendering.renderUtil.VertexOut;

public class PhongInterpolants {
    public int xInt;

    public float x, z;
    public float invW;

    public float tex_u, tex_v;
    public float spec_u, spec_v;

    public float p_ws_x, p_ws_y, p_ws_z;
    public float n_ws_x, n_ws_y, n_ws_z;

    public final PhongLerper_E phongLerper_e;

    public PhongInterpolants(PhongLerper_E phongLerper_e) {
        this.phongLerper_e = phongLerper_e;
    }


    public void reset(VertexOut v) {
        reset(v.p_proj.x, v.p_proj.z, v.invW, v.texCoord.x, v.texCoord.y, v.specCoord.x, v.specCoord.y, v.p_ws.x,
                v.p_ws.y, v.p_ws.z, v.n_ws.x, v.n_ws.y, v.n_ws.z);
    }

    public void reset(float x, float z, float invW, float tex_u, float tex_v, float spec_u, float spec_v,
                      float p_ws_x, float p_ws_y, float p_ws_z, float n_ws_x, float n_ws_y, float n_ws_z) {
        this.x = x;
        this.z = z;
        this.invW = invW;
        this.tex_u = tex_u;
        this.tex_v = tex_v;
        this.spec_u = spec_u;
        this.spec_v = spec_v;
        this.p_ws_x = p_ws_x;
        this.p_ws_y = p_ws_y;
        this.p_ws_z = p_ws_z;
        this.n_ws_x = n_ws_x;
        this.n_ws_y = n_ws_y;
        this.n_ws_z = n_ws_z;
    }
}
