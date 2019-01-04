package Rendering.drawers.fill;

import Rendering.Renderers.Renderer;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.interpolation.RowLerperFactory;
import Rendering.renderUtil.interpolation.gouruad.GouruadInterpolants;
import Rendering.renderUtil.interpolation.gouruad.GouruadLerper_R;
import Rendering.shaders.GouraudShader;
import util.Mathf.Mathf;
import util.Mathf.Mathf3D.Vector3D;

class Rasterizer_G {
    private final GouruadLerper_R gouruadLerper_r = new GouruadLerper_R();
    private final GouruadInterpolants ROW_I_INTERPOLANTS = new GouruadInterpolants(null);
    void rasterizeRow(GouruadInterpolants left, GouruadInterpolants right, int y, Vector3D fColor, Vector3D util) {
        setRowInterpolants(left);

        int from = Mathf.fastCeil(left.x);
        int to = Mathf.fastCeil(right.x);
        ROW_I_INTERPOLANTS.xInt = from;

        if (to - from == 0) {
            fragShade(y, fColor, util);
            return;
        }

        float invdX = 1f / (right.x - left.x);
        RowLerperFactory.gouruadLerper(gouruadLerper_r, RenderState.material, left, right, invdX);

        for (; ROW_I_INTERPOLANTS.xInt < to; ROW_I_INTERPOLANTS.xInt++) {
            fragShade(y, fColor, util);
            gouruadLerper_r.lerp(ROW_I_INTERPOLANTS);
        }
    }

    private void fragShade(int y, Vector3D fColor, Vector3D util) {
        if (GouraudShader.fragNonAlloc(ROW_I_INTERPOLANTS, RenderState.material, fColor, util, y))
            Renderer.onFragShaded(ROW_I_INTERPOLANTS.xInt, y, fColor, RenderState.material);
    }

    private void setRowInterpolants(GouruadInterpolants gI) {
        ROW_I_INTERPOLANTS.reset(gI.x, gI.z, gI.invW, gI.specularity, gI.color_r, gI.color_g, gI.color_b, gI.color_a,
                gI.tex_u, gI.tex_v, gI.spec_u, gI.spec_v);
    }
}
