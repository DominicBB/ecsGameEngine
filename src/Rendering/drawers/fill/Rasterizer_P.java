package Rendering.drawers.fill;

import Rendering.Renderers.Renderer;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.interpolation.RowLerperFactory;
import Rendering.renderUtil.interpolation.phong.PhongInterpolants;
import Rendering.renderUtil.interpolation.phong.PhongLerper_R;
import Rendering.shaders.PhongShader;
import util.Mathf.Mathf;
import util.Mathf.Mathf3D.Vector3D;

public class Rasterizer_P {
    private final PhongLerper_R phongLerper_r = new PhongLerper_R();
    private final PhongInterpolants ROW_I_INTERPOLANTS = new PhongInterpolants(null);

    void rasterizeRow(PhongInterpolants left, PhongInterpolants right, int y, Vector3D fColor, Vector3D util) {
        setRowInterpolants(left);

        int from = Mathf.fastCeil(left.x);
        int to = Mathf.fastCeil(right.x);
        ROW_I_INTERPOLANTS.xInt = from;

        if (to - from == 0) {
            fragShade(y, fColor, util);
            return;
        }

        float invdX = 1f / (right.x - left.x);
        RowLerperFactory.phongLerper(phongLerper_r, RenderState.material, left, right, invdX);

        for (; ROW_I_INTERPOLANTS.xInt < to; ROW_I_INTERPOLANTS.xInt++) {
            fragShade(y, fColor, util);
            phongLerper_r.lerp(ROW_I_INTERPOLANTS);
        }
    }

    private void fragShade(int y, Vector3D fColor, Vector3D util) {
        if (PhongShader.fragNonAlloc(ROW_I_INTERPOLANTS, RenderState.material, fColor, util, y))
            Renderer.onFragShaded(ROW_I_INTERPOLANTS.xInt, y, fColor, RenderState.material);
    }

    private void setRowInterpolants(PhongInterpolants pI) {
        ROW_I_INTERPOLANTS.reset(pI.x, pI.z, pI.invW, pI.tex_u, pI.tex_v, pI.spec_u, pI.spec_v, pI.p_ws_x, pI.p_ws_y,
                pI.p_ws_z, pI.n_ws_x, pI.n_ws_y, pI.n_ws_z);
    }
}
