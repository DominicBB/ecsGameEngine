package systems.drawSystems;

import java.awt.Color;

import util.RenderContext;
import util.geometry.Vector3D;

class DrawLine extends Draw{

	/**
	 * color gradient line
	 * @param v1
	 * @param v2
	 * @param c1
	 * @param c2
	 * @param renderContext
	 */
	public static void draw(Vector3D v1, Vector3D v2, Vector3D c1, Vector3D c2, RenderContext renderContext) {
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
			if (!Draw.isOutOfBounds(x, y)) {
				int yi = (int) y;
				int xi = (int) x;
				/*if (zBuffer[yi][xi] == null || zBuffer[yi][xi].distance < z) {
					zBuffer[yi][xi] = new ZBuffer(z, c);*/

				/*if (zBuffer[yi][xi] < z) {
					zBuffer[yi][xi] = (int)z;

				}*/
				renderContext.setPixel(xi, yi,(byte) c.w, (byte)c.x, (byte)c.y, (byte)c.z);

			}
			x = x + dx;
			y = y + dy;
			z = z + dz;
			c = c.plus(dc);
			i = i + 1;

		}
	}

	public static void drawLine2D(float x1, float y1, float x2, float y2, Color c, RenderContext renderContext) {
		// DDA implementation
		float dx, dy, step, x, y;
		int i;

		dx = (x2 - x1);
		dy = (y2 - y1);

		if (Math.abs(dx) >= Math.abs(dy))
			step = Math.abs(dx);
		else
			step = Math.abs(dy);
		dx = dx / step;
		dy = dy / step;
		x = x1;
		y = y1;
		i = 1;

		while (i <= step) {
			renderContext.setPixel((int)x, (int)y,(byte) c.getAlpha(), (byte)c.getRed(), (byte)c.getGreen(), (byte)c.getBlue());
			x = x + dx;
			y = y + dy;
			i = i + 1;
		}
	}
}
