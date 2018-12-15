package Rendering.drawers.fill;

import Rendering.Materials.Material;
import Rendering.renderUtil.Edges.Edge;
import Rendering.renderUtil.Lerpers.Interpolants;
import Rendering.renderUtil.Lerpers.RowLerperFactory;
import Rendering.renderUtil.Renderer;
import util.Mathf.Mathf3D.Vector3D;

abstract class Rasterizer {

    private static final RowLerperFactory rowLerperFactory = new RowLerperFactory();
    private static final Interpolants ROW_INTERPOLANTS = Interpolants.newEmpty();

    protected static void rasterizeRow(Edge left, Edge right, int y, Material material, Renderer renderer) {
        setLerpValuesTo(left.interpolants, y);

        int from = (int) Math.ceil(left.interpolants.p_proj.x);
        int to = (int) Math.ceil(right.interpolants.p_proj.x);

        if (to - from == 0) {
            ROW_INTERPOLANTS.xInt = to;
            Vector3D color = material.getShader().frag(ROW_INTERPOLANTS, renderer.getzBuffer(), material);
            if (color != null)
                renderer.onFragShaded(to, y, color, material);
            return;
        }

        float invdX = 1f / (right.interpolants.p_proj.x - left.interpolants.p_proj.x);
        rowLerperFactory.setLerper(material, ROW_INTERPOLANTS, left.interpolants, right.interpolants, invdX);

        for (int x = from; x < to; x++) {

            ROW_INTERPOLANTS.xInt = x;
            Vector3D color = material.getShader().frag(ROW_INTERPOLANTS, renderer.getzBuffer(), material);
            if (color != null)
                renderer.onFragShaded(x, y, color, material);

            ROW_INTERPOLANTS.lerp();
        }
    }

    private static void setLerpValuesTo(Interpolants lp, int y) {
        ROW_INTERPOLANTS.reset(lp.p_proj, y, lp.texCoord, lp.specCoord, lp.specularity,
                lp.surfaceColor, lp.invW);
    }

}
