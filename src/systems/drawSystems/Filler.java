package systems.drawSystems;

import java.util.List;

import util.Bitmap;
import util.RenderContext;
import util.geometry.Vector3D;

abstract class Filler extends Draw {

    protected static RenderContext renderContext;

    protected static void fillRow(Edge left, Edge right, int y, Bitmap t, Vector3D shade) {
//        float z = left.z;
        float w = left.w;
        float u = left.u * (t.getWidth() -1);
        float u2 = right.u * (t.getWidth() -1);

        float v = left.v * (t.getHeight()-1);
        float v2 = right.v * (t.getHeight() -1);

//        Vector3D c1 = left.c;

        float dx = right.x - left.x;

        float uStep = (u2 - u) / dx;
        float vStep = (v2 - v) / dx;
        float wStep = (right.w - left.w) / dx;
//        float zStep = (right.z - z) / (dx);
//        Vector3D cStep = (right.c.minus(c1)).divide(dx);

        int from = (int) Math.ceil(left.x);
        int to = (int) Math.ceil(right.x);

        for (int x = from; x < to; x++) {

            /*if (Draw.isOutOfBounds(x, y))
                continue;*/

            if (w > zBuffer[y][x])
                continue;
            zBuffer[y][x] = w;

//            renderContext.setPixel(x, y, (byte) c1.w, (byte) c1.x, (byte) c1.y, (byte) c1.z);
            renderContext.setPixelFromTexture(x, y, t, (int) (u), (int) (v), shade);

            w += wStep;
            u += uStep;
            v += vStep;
//            c1 = c1.plus(cStep);

        }
    }


    protected static List<Edge> removeHorizontal(List<Edge> lines) {

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
        return lines;
    }
}
