package Rendering.drawers.fill;

import Rendering.Materials.Material;
import Rendering.renderUtil.Edges.Edge;
import Rendering.renderUtil.Lerpers.Interpolants;
import Rendering.renderUtil.Lerpers.RowLerperFactory;
import Rendering.renderUtil.Renderer;
import util.Mathf.Mathf3D.Vector3D;

abstract class Rasterizer {

    private static RowLerperFactory rowLerperFactory = new RowLerperFactory();
    private static final Interpolants ROW_INTERPOLANTS = Interpolants.newEmpty();

    protected static void rasterizeRow(Edge left, Edge right, int y, Material material, Renderer renderer) {
        setLerpValuesTo(left.interpolants);

        float dx = right.interpolants.getP_proj().x - left.interpolants.getP_proj().x;
        int from = (int) Math.ceil(left.interpolants.getP_proj().x);
        int to = (int) Math.ceil(right.interpolants.getP_proj().x);

        if (to - from == 0) {
            ROW_INTERPOLANTS.setxInt(to);
            ROW_INTERPOLANTS.setyInt(y);
            Vector3D color = material.getShader().frag(ROW_INTERPOLANTS, renderer.getzBuffer(), material);
            if (color != null)
                renderer.onFragShaded(to, y, color, material);
            return;
        }

        rowLerperFactory.setLerper(material, ROW_INTERPOLANTS, left.interpolants, right.interpolants, dx);
        ROW_INTERPOLANTS.setyInt(y);

        for (int x = from; x < to; x++) {

            ROW_INTERPOLANTS.setxInt(x);
            Vector3D color = material.getShader().frag(ROW_INTERPOLANTS, renderer.getzBuffer(), material);
            if (color != null)
                renderer.onFragShaded(x, y, color, material);

            ROW_INTERPOLANTS.lerp();
        }
    }

    private static void setLerpValuesTo(Interpolants lp) {
        ROW_INTERPOLANTS.reset(lp.getP_proj(), lp.getyInt(), lp.getTexCoord(), lp.getSpecCoord(), lp.getSpecularity(),
                lp.getSurfaceColor(), lp.getInvW());
    }

}
