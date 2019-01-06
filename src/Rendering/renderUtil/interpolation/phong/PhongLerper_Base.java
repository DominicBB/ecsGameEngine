package Rendering.renderUtil.interpolation.phong;

public abstract class PhongLerper_Base {
    public int x, z;
    public int invW;

    public int tex_u, tex_v;
    public int spec_u, spec_v;

    public int p_ws_x, p_ws_y, p_ws_z;
    public int n_ws_x, n_ws_y, n_ws_z;
}
