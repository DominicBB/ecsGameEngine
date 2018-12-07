package Rendering.drawers.fill;

import java.util.List;

import Rendering.Materials.Material;
import Rendering.renderUtil.Edge;
import Rendering.renderUtil.Lerpers.LerpValues;
import Rendering.renderUtil.Lerpers.RowLerperFactory;
import Rendering.renderUtil.Renderer;
import util.Mathf.Mathf3D.Vector3D;

abstract class Rasterizer {

    private static RowLerperFactory rowLerperFactory = new RowLerperFactory();
    private static final LerpValues lerpValues = LerpValues.newEmpty();

    protected static void rasterizeRow(Edge left, Edge right, int y, Material material, Renderer renderer) {
        lerpValues.reset();

        float dx = right.lerpValues.getPos_proj().x - left.lerpValues.getPos_proj().x;


        rowLerperFactory.createLerpValues(material, lerpValues, left.lerpValues, right.lerpValues, dx);
        lerpValues.setyInt(y);
        int from = (int) Math.ceil(left.lerpValues.getPos_proj().x);
        int to = (int) Math.ceil(right.lerpValues.getPos_proj().x);

        for (int x = from; x < to; x++) {

            lerpValues.setxInt(x);
            Vector3D color = material.getShader().frag(lerpValues, renderer.getzBuffer(), material);
            if (color != null)
                renderer.onFragShaded(x, y, color, material);

            lerpValues.lerp();
        }
    }


    protected static void removeHorizontal(List<Edge> lines) {

        if (lines.get(0).deltaYInt == 0) {
            /* line = */
            lines.remove(0);
        } else if (lines.get(1).deltaYInt == 0) {
            /* line = */
            lines.remove(1);

        } else if (lines.get(2).deltaYInt == 0) {
            /* line = */
            lines.remove(2);

        }
    }
}
