package Rendering.drawers.fill;

import Rendering.Renderers.Renderer;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.interpolation.RowLerperFactory;
import Rendering.renderUtil.interpolation.flat.FlatInterpolants;
import Rendering.renderUtil.interpolation.flat.FlatLerper_R;
import Rendering.shaders.FlatShader;
import util.Mathf.Mathf3D.Vec4fi;

public class Rasterizer_F {
    private final FlatLerper_R flatLerper_r = new FlatLerper_R();
    private final FlatInterpolants ROW_I_INTERPOLANTS = new FlatInterpolants(null);
    Vec4fi surfaceColor;
    int spec;

    void rasterizeRow(FlatInterpolants left, FlatInterpolants right, int y, Vec4fi fColor, Vec4fi util) {
        setRowInterpolants(left);

        int from = Rasterfi.ceil_destroy_format(left.x);
        int to = Rasterfi.ceil_destroy_format(right.x);
        ROW_I_INTERPOLANTS.xInt = from;

        if (to - from == 0) {
            fragShade(y, fColor, util);
            return;
        }

        int invdX = Rasterfi.inverse(right.x - left.x);
        RowLerperFactory.flatLerper(flatLerper_r, RenderState.material, left, right, invdX);

        for (; ROW_I_INTERPOLANTS.xInt < to; ROW_I_INTERPOLANTS.xInt++) {
            fragShade(y, fColor, util);
            flatLerper_r.lerp(ROW_I_INTERPOLANTS);
        }
    }

    private void fragShade(int y, Vec4fi fColor, Vec4fi util) {
        if (FlatShader.fragNonAlloc(ROW_I_INTERPOLANTS, surfaceColor, spec, fColor, util, y))
            Renderer.onFragShaded(ROW_I_INTERPOLANTS.xInt, y, fColor, RenderState.material);
    }

    private void setRowInterpolants(FlatInterpolants fI) {
        ROW_I_INTERPOLANTS.reset(fI.x, fI.z, fI.invW,
                fI.tex_u, fI.tex_v, fI.spec_u, fI.spec_v);
    }
}
