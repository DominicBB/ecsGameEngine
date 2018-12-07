package Rendering.drawers.draw;

import Rendering.drawers.Draw;
import Rendering.renderUtil.Renderer;
import util.Mathf.Mathf3D.Vector3D;

public class DrawLine2D extends Draw {

    public static void drawLine2D(float x1, float y1, float x2, float y2, Vector3D c, Renderer renderer) {
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
            renderer.colorBuffer.setPixel((int) x, (int) y, c);
            x = x + dx;
            y = y + dy;
            i = i + 1;
        }
    }

    public static void drawLine2DBresenham(int x1, int y1, int x2, int y2, Vector3D c, Renderer renderer) {
        if (Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
            if (x1 > x2)
                drawLineLow(x2, y2, x1, y1, c, renderer);
            else
                drawLineLow(x1, y1, x2, y2, c, renderer);
        } else if (y1 > y2)
            drawLineHigh(x2, y2, x1, y1, c, renderer);
        else
            drawLineHigh(x1, y1, x2, y2, c, renderer);
    }


    private static void drawLineLow(int x1, int y1, int x2, int y2, Vector3D c, Renderer renderer) {
        int dx = x2 - x1;
        int dy = y2 - y1;

        int yStep = 1;

        if (dy < 0) {
            yStep = -1;
            dy = -dy;
        }
        int dx2 = dx << 1;
        int dy2 = dy << 1;

        int D = dy2 - dx;
        int y = y1;

        for (int x = x1; x <= x2; x++) {
            renderer.colorBuffer.setPixel(x, y, c);
            if (D > 0) {
                y += yStep;
                D -= dx2;
            }
            D += dy2;
        }
    }

    private static void drawLineHigh(int x1, int y1, int x2, int y2, Vector3D c, Renderer renderer) {
        int dx = x2 - x1;
        int dy = y2 - y1;

        int xStep = 1;

        if (dx < 0) {
            xStep = -1;
            dx = -dx;
        }

        int dx2 = dx << 1;
        int dy2 = dy << 1;

        int D = dx2 - dy;
        int x = x1;

        for (int y = y1; y <= y2; y++) {
            renderer.colorBuffer.setPixel(x, y, c);
            if (D > 0) {
                x += xStep;
                D -= dy2;
            }
            D += dx2;
        }
    }
}
