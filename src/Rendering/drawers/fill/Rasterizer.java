package Rendering.drawers.fill;

import java.util.List;

import Rendering.Materials.Material;
import Rendering.renderUtil.Edge;
import Rendering.renderUtil.Lerpers.LerpValues;
import Rendering.renderUtil.Lerpers.RowLerperFactory;
import Rendering.renderUtil.RenderContext;
import util.Mathf.Mathf3D.Vector3D;

abstract class Rasterizer {

    private static RowLerperFactory rowLerperFactory = new RowLerperFactory();
    private static final LerpValues lerpValues = LerpValues.newEmpty();

    protected static void rasterizeRow(Edge left, Edge right, int y, Material material, RenderContext renderContext) {
        //TODO: texCorrd and specCood * width and height of png

        lerpValues.reset();

        float dx = right.lerpValues.getPos_proj().x - left.lerpValues.getPos_proj().x;


        rowLerperFactory.createLerpValues(material, lerpValues, left.lerpValues, right.lerpValues, dx);

        int from = (int) Math.ceil(left.lerpValues.getPos_proj().x);
        int to = (int) Math.ceil(right.lerpValues.getPos_proj().x);

        for (int x = from; x < to; x++) {

            lerpValues.setxInt(x);
            Vector3D color = material.getShader().frag(lerpValues, renderContext, material);
            if (color != null)
                renderContext.setPixel(x, y, color);

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
