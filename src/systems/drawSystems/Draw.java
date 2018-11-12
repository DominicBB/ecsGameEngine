package systems.drawSystems;

import java.awt.*;

import core.Window;
import util.Bitmap;
import util.RenderContext;
import util.geometry.*;
import util.geometry.Polygon;

public abstract class Draw {
    //    	protected static ZBuffer[][] zBuffer;
    protected static float[][] zBuffer;


    public static void fillPolygon(Polygon p, RenderContext renderContext) {

    }

    public static void fillPolygon(Triangle t, RenderContext renderContext, Bitmap texture) {
        TriangleFillSystem.fillTriangle(t, renderContext, texture);
    }

    private static Vector3D white = new Vector3D(255,255,255);
    public static void wireframePolygon(Polygon p, RenderContext renderContext) {
        DrawLine.draw(p.vectors[0], p.vectors[1], white, white, renderContext);
        DrawLine.draw(p.vectors[1], p.vectors[2], white, white, renderContext);
        DrawLine.draw(p.vectors[2], p.vectors[0], white, white, renderContext);
    }

    /*public static void wireframePolygon(Triangle t, RenderContext renderContext) {
        DrawLine.draw(t.vectors[0], t.vectors[1], t.reflectance, renderContext);
        DrawLine.draw(t.vectors[1], t.vectors[2], t.reflectance, renderContext);
        DrawLine.draw(t.vectors[2], t.vectors[0], t.reflectance, renderContext);
    }*/

    public static void wireframePolygon(Triangle t, Vector3D c1, Vector3D c2, RenderContext renderContext) {
        DrawLine.draw(t.vectors[0], t.vectors[1], c1, c2, renderContext);
        DrawLine.draw(t.vectors[1], t.vectors[2], c1, c2, renderContext);
        DrawLine.draw(t.vectors[2], t.vectors[0], c1, c2, renderContext);
    }


    public static void drawLine(Vector3D v1, Vector3D v2, Vector3D c, Vector3D c2, RenderContext renderContext) {
        DrawLine.draw(v1, v2, c, c2, renderContext);
    }

    static boolean isOutOfBounds(float x, float y) {
        return x < 0 || y < 0 || x >= Window.defaultWidth || y >= Window.defaultHeight;
    }

    static Line3D yIncreasingLine3D(Vector3D v1, Vector3D v2) {
        if (v1.y > v2.y) {
            return new Line3D(v2, v1);
        }
        return new Line3D(v1, v2);
    }

    static Edge yIncreasingEdge(Vector3D v1, Vector3D v2, Vector2D t1, Vector2D t2) {
        if (v1.y > v2.y) { //decreasing y line, so flip
            return new Edge(v2, v1, t2, t2, 1);
        }
        return new Edge(v1, v2, t1, t2, 0);
    }


    	/*public static void resetZBuffer(int width, int height) {
		zBuffer = new ZBuffer[width][height];
	}*/

    public static void resetZBuffer(int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                zBuffer[x][y] = Float.POSITIVE_INFINITY;
            }
        }

    }

    public static void setZBuffer(int width, int height) {
        zBuffer = new float[width][height];

    }
}
