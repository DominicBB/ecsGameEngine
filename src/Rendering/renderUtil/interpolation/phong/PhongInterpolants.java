package Rendering.renderUtil.interpolation.phong;

import Rendering.renderUtil.VOutfi;

public class PhongInterpolants {
    public int xInt;

    public int x, z;
    public int invW;

    public int tex_u, tex_v;
    public int spec_u, spec_v;

    public int p_ws_x, p_ws_y, p_ws_z;
    public int n_ws_x, n_ws_y, n_ws_z;

    public final PhongLerper_E phongLerper_e;

    public PhongInterpolants(PhongLerper_E phongLerper_e) {
        this.phongLerper_e = phongLerper_e;
    }


    public PhongInterpolants(int x, int z, int invW, int tex_u, int tex_v, int spec_u, int spec_v,
                             int p_ws_x, int p_ws_y, int p_ws_z, int n_ws_x, int n_ws_y, int n_ws_z) {
        phongLerper_e = null;
        reset(x, z, invW, tex_u, tex_v, spec_u, spec_v, p_ws_x, p_ws_y, p_ws_z, n_ws_x, n_ws_y, n_ws_z);
    }

    public void reset(VOutfi v) {
        reset(v.p_proj.x, v.p_proj.z, v.invW, v.texCoord.x, v.texCoord.y, v.specCoord.x, v.specCoord.y, v.p_ws.x,
                v.p_ws.y, v.p_ws.z, v.n_ws.x, v.n_ws.y, v.n_ws.z);
    }

    public void reset(int x, int z, int invW, int tex_u, int tex_v, int spec_u, int spec_v,
                      int p_ws_x, int p_ws_y, int p_ws_z, int n_ws_x, int n_ws_y, int n_ws_z) {
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
