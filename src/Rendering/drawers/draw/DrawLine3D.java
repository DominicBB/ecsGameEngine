package Rendering.drawers.draw;

import Rendering.Materials.Material;
import Rendering.drawers.Draw;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.VertexOut;
import util.Mathf.Mathf3D.Vector3D;

public class DrawLine3D extends Draw {

    /**
     *
     * @param v1
     * @param v2
     * @param material
     * @param renderer
     */
    public static void drawLine(VertexOut v1, VertexOut v2, Material material) {
        // DDA implementation, with z buff
        float dx, dy, dz, step, x, y, z;
        Vector3D dc;
        Vector3D c;
        int i;

        dx = (v2.p_proj.x - v1.p_proj.x);
        dy = (v2.p_proj.y - v1.p_proj.y);
        dz = (v2.p_proj.z - v1.p_proj.z);
//        dc = (v2..minus(c1));

        if (Math.abs(dx) >= Math.abs(dy))
            step = Math.abs(dx);
        else
            step = Math.abs(dy);
        dx = dx / step;
        dy = dy / step;
        dz = dz / step;
//        dc = dc.divide(step);

        x = v1.p_proj.x;
        y = v1.p_proj.y;
        z = v1.p_proj.z;
//        c = c1;
        i = 1;

        while (i <= step) {

            int yi = (int) y;
            int xi = (int) x;
            RenderState.colorBuffer.setPixel(xi, yi, material.getColor());

            x = x + dx;
            y = y + dy;
            z = z + dz;
//            c = c.plus(dc);
            i = i + 1;

        }
    }

    public static void drawLine(Vector3D v1, Vector3D v2, Vector3D color) {
        // DDA implementation, with z buff
        float dx, dy, dz, step, x, y, z;
        int i;

        dx = (v2.x - v1.x);
        dy = (v2.y - v1.y);
        dz = (v2.z - v1.z);


        if (Math.abs(dx) >= Math.abs(dy))
            step = Math.abs(dx);
        else
            step = Math.abs(dy);
        dx = dx / step;
        dy = dy / step;
        dz = dz / step;

        x = v1.x;
        y = v1.y;
        z = v1.z;
        i = 1;

        while (i <= step) {
            if (true /*!Draw.isOutOfBounds(x, y)*/) {
                int yi = (int) y;
                int xi = (int) x;
				/*if (zBuffer[yi][xi] == null || zBuffer[yi][xi].distance < z) {
					zBuffer[yi][xi] = new FloatBuffer(z, c);*/

				/*if (zBuffer[yi][xi] < z) {
					zBuffer[yi][xi] = (int)z;

				}*/
                RenderState.colorBuffer.setPixel(xi, yi, color);

            }
            x = x + dx;
            y = y + dy;
            z = z + dz;
            i = i + 1;

        }
    }
}
