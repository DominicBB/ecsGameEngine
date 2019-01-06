package Rendering.drawers;

import Rendering.Materials.Material;
import Rendering.Renderers.Renderer;
import Rendering.drawers.draw.DrawLine3D;
import Rendering.renderUtil.VertexOut;
import core.display.Window;
import util.Mathf.Mathf3D.Line3D;
import util.Mathf.Mathf3D.Polygon;
import util.Mathf.Mathf3D.Vec4f;

public abstract class Draw {

    public static void fillPolygon(Polygon p, Renderer renderer) {

    }

   /* public static void fillPolygon(VertexOut v1, VertexOut v2, VertexOut v3, Material material) {
        TriangleRasterizer.fillTriangle(v1, v2, v3, material);
    }*/

    private static Vec4f white = new Vec4f(255, 255, 255);

    public static void wireframePolygon(Polygon p) {
        DrawLine3D.drawLine(p.vectors[0], p.vectors[1], white, white);
        DrawLine3D.drawLine(p.vectors[1], p.vectors[2], white, white);
        DrawLine3D.drawLine(p.vectors[2], p.vectors[0], white, white);
    }

    /*public static void wireframePolygon(Triangle t, Renderer renderContext) {
        DrawLine3D.drawLine(t.vectors[0], t.vectors[1], t.reflectance, renderContext);
        DrawLine3D.drawLine(t.vectors[1], t.vectors[2], t.reflectance, renderContext);
        DrawLine3D.drawLine(t.vectors[2], t.vectors[0], t.reflectance, renderContext);
    }*/

    public static void wireframePolygon(VertexOut v0, VertexOut v1, VertexOut v2, Material material) {
        DrawLine3D.drawLine(v0, v1, material);
        DrawLine3D.drawLine(v1, v2, material);
        DrawLine3D.drawLine(v2, v1, material);
    }

    public static void drawLine(Vec4f v1, Vec4f v2, Vec4f c, Vec4f c2) {
        DrawLine3D.drawLine(v1, v2, c, c2);
    }

    protected final static boolean isOutOfBounds(float x, float y) {
        return x < 0 || y < 0 || x >= Window.defaultWidth || y >= Window.defaultHeight;
    }

    public final static Line3D yIncreasingLine3D(Vec4f v1, Vec4f v2) {
        if (v1.y > v2.y) {
            return new Line3D(v2, v1);
        }
        return new Line3D(v1, v2);
    }

}
