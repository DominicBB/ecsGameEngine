package Rendering.drawers.fill;

import Rendering.Renderers.Renderer;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.interpolation.RowLerperFactory;
import Rendering.renderUtil.interpolation.flat.FlatInterpolants;
import Rendering.renderUtil.interpolation.flat.FlatLerper_R;
import Rendering.shaders.FlatShader;
import util.Mathf.Mathf;
import util.Mathf.Mathf3D.Vector3D;

public class Rasterizer_F {
    private final FlatLerper_R flatLerper_r = new FlatLerper_R();
    private final FlatInterpolants ROW_I_INTERPOLANTS = new FlatInterpolants(null);
    Vector3D surfaceColor;
    float spec;

    void rasterizeRow(FlatInterpolants left, FlatInterpolants right, int y, Vector3D fColor, Vector3D util) {
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

    private void fragShade(int y, Vector3D fColor, Vector3D util) {
        if (FlatShader.fragNonAlloc(ROW_I_INTERPOLANTS, surfaceColor, spec, RenderState.material, fColor, util, y))
            Renderer.onFragShaded(ROW_I_INTERPOLANTS.xInt, y, fColor, RenderState.material);
    }

    private void setRowInterpolants(FlatInterpolants fI) {
        ROW_I_INTERPOLANTS.reset(fI.x, fI.z, fI.invW,
                fI.tex_u, fI.tex_v, fI.spec_u, fI.spec_v);
    }
}
