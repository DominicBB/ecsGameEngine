package rendering.drawers.fill;

import rendering.renderers.Renderer;
import rendering.renderUtil.RenderState;
import rendering.renderUtil.interpolation.RowLerperFactory;
import rendering.renderUtil.interpolation.flat.FlatInterpolants;
import rendering.renderUtil.interpolation.flat.FlatLerper_R;
import rendering.shaders.FlatShader;
import util.mathf.Mathf;
import util.mathf.Mathf3D.Vec4f;

public class Rasterizer_F {
    private final FlatLerper_R flatLerper_r = new FlatLerper_R();
    private final FlatInterpolants ROW_I_INTERPOLANTS = new FlatInterpolants(null);
    Vec4f surfaceColor;
    float spec;

    void rasterizeRow(FlatInterpolants left, FlatInterpolants right, int y, Vec4f fColor, Vec4f util) {
        setRowInterpolants(left);

        int from = Mathf.fastCeil(left.x);
        int to = Mathf.fastCeil(right.x);
        ROW_I_INTERPOLANTS.xInt = from;

        if (to - from == 0) {
            fragShade(y, fColor, util);
            return;
        }

        float invdX = 1f / (right.x - left.x);
        RowLerperFactory.flatLerper(flatLerper_r, RenderState.material, left, right, invdX);

        for (; ROW_I_INTERPOLANTS.xInt < to; ROW_I_INTERPOLANTS.xInt++) {
            fragShade(y, fColor, util);
            flatLerper_r.lerp(ROW_I_INTERPOLANTS);
        }
    }

    private void fragShade(int y, Vec4f fColor, Vec4f util) {
        if (FlatShader.fragNonAlloc(ROW_I_INTERPOLANTS, surfaceColor, spec, RenderState.material, fColor, util, y))
            Renderer.onFragShaded(ROW_I_INTERPOLANTS.xInt, y, fColor, RenderState.material);
    }

    private void setRowInterpolants(FlatInterpolants fI) {
        ROW_I_INTERPOLANTS.reset(fI.x, fI.z, fI.invW,
                fI.tex_u, fI.tex_v, fI.spec_u, fI.spec_v);
    }
}
