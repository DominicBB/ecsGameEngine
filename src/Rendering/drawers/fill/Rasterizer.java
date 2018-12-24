package Rendering.drawers.fill;

import Rendering.Materials.Material;
import Rendering.Renderers.Renderer;
import Rendering.renderUtil.Edges.Edge;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.interpolation.Interpolants;
import Rendering.renderUtil.interpolation.RowLerperFactory;
import util.Mathf.Mathf;
import util.Mathf.Mathf3D.Vector3D;

class Rasterizer {

    private final RowLerperFactory rowLerperFactory = new RowLerperFactory();
    private final Interpolants ROW_INTERPOLANTS = Interpolants.newEmpty();
    private final Vector3D fragColor = Vector3D.newZeros();
    private final Vector3D fragUtil = Vector3D.newZeros();

    void rasterizeRow(Edge left, Edge right, int y) {
        setLerpValuesTo(left.interpolants, y);

        int from = Mathf.fastCeil(left.interpolants.p_proj.x);
        int to = Mathf.fastCeil(right.interpolants.p_proj.x);

        if (to - from == 0) {
            ROW_INTERPOLANTS.xInt = from;
            fragShade(y);
            return;
        }

        float invdX = 1f / (right.interpolants.p_proj.x - left.interpolants.p_proj.x);
        rowLerperFactory.setLerper(RenderState.material, ROW_INTERPOLANTS, left.interpolants, right.interpolants, invdX);

        for (int x = from; x < to; x++) {
            ROW_INTERPOLANTS.xInt = x;
            fragShade(y);
            ROW_INTERPOLANTS.lerp();
        }
    }

    private void fragShade(int y) {
        if (RenderState.material.getShader().fragNonAlloc(ROW_INTERPOLANTS, RenderState.material, fragColor, fragUtil))
            Renderer.onFragShaded(ROW_INTERPOLANTS.xInt, y, fragColor, RenderState.material);
    }

    private void setLerpValuesTo(Interpolants lp, int y) {
        ROW_INTERPOLANTS.reset(lp.p_proj, y, lp.texCoord, lp.specCoord, lp.specularity,
                lp.surfaceColor, lp.invW);
    }
}
