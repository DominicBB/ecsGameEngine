package rendering.renderUtil.interpolation.phong;

public class PhongLerper_E extends PhongLerper_Base{


    public void lerp(PhongInterpolants interpolants) {
        interpolants.x += x;
        interpolants.z += z;

        interpolants.tex_u += tex_u;
        interpolants.tex_v += tex_v;

        interpolants.spec_u += spec_u;
        interpolants.spec_v += spec_v;

        interpolants.invW += invW;

        interpolants.p_ws_x += p_ws_x;
        interpolants.p_ws_y += p_ws_y;
        interpolants.p_ws_z += p_ws_z;

        interpolants.n_ws_x += n_ws_x;
        interpolants.n_ws_y += n_ws_y;
        interpolants.n_ws_z += n_ws_z;
    }
}
