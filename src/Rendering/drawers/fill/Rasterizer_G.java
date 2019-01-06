package Rendering.drawers.fill;

import Rendering.Renderers.Renderer;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.interpolation.RowLerperFactory;
import Rendering.renderUtil.interpolation.gouruad.GouruadInterpolants;
import Rendering.renderUtil.interpolation.gouruad.GouruadLerper_R;
import Rendering.shaders.GouraudShader;
import util.Mathf.Mathf3D.Vec4fi;

class Rasterizer_G {
    private final GouruadLerper_R gouruadLerper_r = new GouruadLerper_R();
    private final GouruadInterpolants ROW_I_INTERPOLANTS = new GouruadInterpolants(null);

    void rasterizeRow(GouruadInterpolants left, GouruadInterpolants right, int y, Vec4fi fColor, Vec4fi util) {
        setRowInterpolants(left);

        int from = Rasterfi.ceil_destroy_format(left.x);
        int to = Rasterfi.ceil_destroy_format(right.x);
        ROW_I_INTERPOLANTS.xInt = from;

        if (to - from == 0) {
            fragShade(y, fColor, util);
            return;
        }

        int invdX = Rasterfi.inverse(right.x - left.x);
        RowLerperFactory.gouruadLerper(gouruadLerper_r, RenderState.material, left, right, invdX);

        for (; ROW_I_INTERPOLANTS.xInt < to; ROW_I_INTERPOLANTS.xInt++) {
            fragShade(y, fColor, util);
            gouruadLerper_r.lerp(ROW_I_INTERPOLANTS);
        }
    }

    private void fragShade(int y, Vec4fi fColor, Vec4fi util) {
        if (GouraudShader.fragNonAlloc(ROW_I_INTERPOLANTS, fColor, util, y))
            Renderer.onFragShaded(ROW_I_INTERPOLANTS.xInt, y, fColor, RenderState.material);
    }

    private void setRowInterpolants(GouruadInterpolants gI) {
        ROW_I_INTERPOLANTS.reset(gI.x, gI.z, gI.invW, gI.specularity, gI.color_r, gI.color_g, gI.color_b, gI.color_a,
                gI.tex_u, gI.tex_v, gI.spec_u, gI.spec_v);
    }
}
