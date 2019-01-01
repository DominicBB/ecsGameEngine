package Rendering.renderUtil.interpolation.phong;

public class PhongInterpolants {
    public int xInt;

    public float x, z;
    public float invW;

    public float tex_u, tex_v;
    public float spec_u, spec_v;

    public float p_ws_x, p_ws_y, p_ws_z;
    public float n_ws_x, n_ws_y, n_ws_z;

    public PhongLerper_E phongLerper_e;
    public PhongLerper_R phongLerper_r;
}
