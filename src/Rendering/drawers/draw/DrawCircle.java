package Rendering.drawers.draw;

import Rendering.drawers.Draw;
import Rendering.renderUtil.Renderer;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class DrawCircle extends Draw {

    /*private static final float DA_START = 12f;
    private static final float DB_START = 20f;*/

    public static void drawCircle(Vector2D center, float radius, Vector3D color, Renderer renderer) {

        //mid-point circle
        /*float x = center.x, y = radius, d = 5 - 4 * radius,
                dA = DA_START,
                dB = DB_START - radius *8f;

        while (x < y)
            renderer.setPixel(x, y);
        if (d < 0) {
            d += dA;
            dB += 8;
        } else {
            --y;
            d += dB;
            dB += 16;
        }
        ++x;
        dA += 8;*/

        int cX = (int) center.x;
        int cY = (int) center.y;

        int iRad = (int) radius << 1;
        int x = (int) (radius - 1);
        int y = 0;
        int dx = 1;
        int dy = 1;
        int err = dx - iRad;

        while (x >= y) {
            renderer.colorBuffer.setPixel(cX + x, cY + y, color);
            renderer.colorBuffer.setPixel(cX + y, cY + x, color);
            renderer.colorBuffer.setPixel(cX - y, cY + x, color);
            renderer.colorBuffer.setPixel(cX - x, cY + y, color);
            renderer.colorBuffer.setPixel(cX - x, cY - y, color);
            renderer.colorBuffer.setPixel(cX - y, cY - x, color);
            renderer.colorBuffer.setPixel(cX + y, cY - x, color);
            renderer.colorBuffer.setPixel(cX + x, cY - y, color);

            if (err <= 0) {
                y++;
                err += dy;
                dy += 2;
            }

            if (err > 0) {
                x--;
                dx += 2;
                err += dx - iRad;
            }
        }
    }

    public static void fillCircle(Vector2D center, float radius, Vector3D color, Renderer renderer) {
        int cX = (int) center.x;
        int cY = (int) center.y;

        int iRad = (int) radius << 1;
        int x = (int) (radius - 1);
        int y = 0;
        int dx = 1;
        int dy = 1;
        int err = dx - iRad;

        while (x >= y) {
            DrawLine2D.drawLine2DBresenham(cX, cY,cX + x,cY + y, color, renderer);
            DrawLine2D.drawLine2DBresenham(cX, cY,cX + y,cY + x, color, renderer);
            DrawLine2D.drawLine2DBresenham(cX, cY,cX - y,cY + x, color, renderer);
            DrawLine2D.drawLine2DBresenham(cX, cY,cX - x,cY + y, color, renderer);
            DrawLine2D.drawLine2DBresenham(cX, cY,cX - x,cY - y, color, renderer);
            DrawLine2D.drawLine2DBresenham(cX, cY,cX - y,cY - x, color, renderer);
            DrawLine2D.drawLine2DBresenham(cX, cY,cX + y,cY - x, color, renderer);
            DrawLine2D.drawLine2DBresenham(cX, cY,cX + x,cY - y, color, renderer);

            if (err <= 0) {
                y++;
                err += dy;
                dy += 2;
            }

            if (err > 0) {
                x--;
                dx += 2;
                err += dx - iRad;
            }
        }
    }

}
