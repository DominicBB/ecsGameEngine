package Rendering.drawers.fill;

import Rendering.Renderers.Renderer;
import Rendering.renderUtil.Edges.Edge;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.interpolation.IInterpolants;
import Rendering.renderUtil.interpolation.RowLerperFactory;
import util.Mathf.Mathf;
import util.Mathf.Mathf3D.Vector3D;

class Rasterizer {

    private final RowLerperFactory rowLerperFactory = new RowLerperFactory();
    private final IInterpolants ROW_I_INTERPOLANTS = IInterpolants.newEmpty();
    private final Vector3D fragColor = Vector3D.newZeros();
    private final Vector3D fragUtil = Vector3D.newZeros();

    void rasterizeRow(Edge left, Edge right, int y) {
        setRowInterpolants(left.IInterpolants, y);

        int from = Mathf.fastCeil(left.IInterpolants.p_proj.x);
        int to = Mathf.fastCeil(right.IInterpolants.p_proj.x);
        ROW_I_INTERPOLANTS.xInt = from;

        if (to - from == 0) {
            fragShade(y);
            return;
        }

        float invdX = 1f / (right.IInterpolants.p_proj.x - left.IInterpolants.p_proj.x);
        rowLerperFactory.setLerper(RenderState.material, ROW_I_INTERPOLANTS, left.IInterpolants, right.IInterpolants, invdX);

        for (int x = from; x < to; x++) {
            fragShade(y);
            ++ROW_I_INTERPOLANTS.xInt;
            ROW_I_INTERPOLANTS.lerp();
        }
    }

    private void fragShade(int y) {
        if (RenderState.material.getShader().fragNonAlloc(ROW_I_INTERPOLANTS, RenderState.material, fragColor, fragUtil))
            Renderer.onFragShaded(ROW_I_INTERPOLANTS.xInt, y, fragColor, RenderState.material);
    }

    private void setRowInterpolants(IInterpolants lp, int y) {
        ROW_I_INTERPOLANTS.reset(lp.p_proj, y, lp.texCoord, lp.specCoord, lp.specularity,
                lp.surfaceColor, lp.invW);
    }
}
