package Rendering.drawers.draw;

import Rendering.drawers.Draw;
import Rendering.renderUtil.RenderContext;
import util.Mathf.Mathf3D.Vector3D;

public class DrawLine3D extends Draw {

    /**
     * color gradient line
     *
     * @param v1
     * @param v2
     * @param c1
     * @param c2
     * @param renderContext
     */
    public static void drawLine(Vector3D v1, Vector3D v2, Vector3D c1, Vector3D c2, RenderContext renderContext) {
        // DDA implementation, with z buff
        float dx, dy, dz, step, x, y, z;
        Vector3D dc;
        Vector3D c;
        int i;

        dx = (v2.x - v1.x);
        dy = (v2.y - v1.y);
        dz = (v2.z - v1.z);
        dc = (c2.minus(c1));

        if (Math.abs(dx) >= Math.abs(dy))
            step = Math.abs(dx);
        else
            step = Math.abs(dy);
        dx = dx / step;
        dy = dy / step;
        dz = dz / step;
        dc = dc.divide(step);

        x = v1.x;
        y = v1.y;
        z = v1.z;
        c = c1;
        i = 1;

        while (i <= step) {
            if (true /*!Draw.isOutOfBounds(x, y)*/) {
                int yi = (int) y;
                int xi = (int) x;
				/*if (zBuffer[yi][xi] == null || zBuffer[yi][xi].distance < z) {
					zBuffer[yi][xi] = new ZBuffer(z, c);*/

				/*if (zBuffer[yi][xi] < z) {
					zBuffer[yi][xi] = (int)z;

				}*/
                renderContext.setPixel(xi, yi, (byte) c.w, (byte) c.x, (byte) c.y, (byte) c.z);

            }
            x = x + dx;
            y = y + dy;
            z = z + dz;
            c = c.plus(dc);
            i = i + 1;

        }
    }

    public static void drawLine(Vector3D v1, Vector3D v2, Vector3D color, RenderContext renderContext) {
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
					zBuffer[yi][xi] = new ZBuffer(z, c);*/

				/*if (zBuffer[yi][xi] < z) {
					zBuffer[yi][xi] = (int)z;

				}*/
                renderContext.setPixel(xi, yi, color);

            }
            x = x + dx;
            y = y + dy;
            z = z + dz;
            i = i + 1;

        }
    }
}
