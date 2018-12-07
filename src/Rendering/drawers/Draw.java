package Rendering.drawers;

import Rendering.Materials.Material;
import Rendering.drawers.draw.DrawLine3D;
import Rendering.drawers.fill.TriangleRasterizer;
import Rendering.renderUtil.*;
import core.Window;
import util.Mathf.Mathf3D.Line3D;
import util.Mathf.Mathf3D.Polygon;
import util.Mathf.Mathf3D.Triangle;
import util.Mathf.Mathf3D.Vector3D;

public abstract class Draw {

    public static void fillPolygon(Polygon p, Renderer renderer) {

    }

    public static void fillPolygon(VertexOut v1, VertexOut v2, VertexOut v3, Renderer renderer, Material material) {
        TriangleRasterizer.fillTriangle(v1, v2, v3, renderer, material);
    }

    private static Vector3D white = new Vector3D(255, 255, 255);

    public static void wireframePolygon(Polygon p, Renderer renderer) {
        DrawLine3D.drawLine(p.vectors[0], p.vectors[1], white, white, renderer);
        DrawLine3D.drawLine(p.vectors[1], p.vectors[2], white, white, renderer);
        DrawLine3D.drawLine(p.vectors[2], p.vectors[0], white, white, renderer);
    }

    /*public static void wireframePolygon(Triangle t, Renderer renderContext) {
        DrawLine3D.drawLine(t.vectors[0], t.vectors[1], t.reflectance, renderContext);
        DrawLine3D.drawLine(t.vectors[1], t.vectors[2], t.reflectance, renderContext);
        DrawLine3D.drawLine(t.vectors[2], t.vectors[0], t.reflectance, renderContext);
    }*/

    public static void wireframePolygon(Triangle t, Vector3D c1, Vector3D c2, Renderer renderer) {
        DrawLine3D.drawLine(t.vectors[0], t.vectors[1], c1, c2, renderer);
        DrawLine3D.drawLine(t.vectors[1], t.vectors[2], c1, c2, renderer);
        DrawLine3D.drawLine(t.vectors[2], t.vectors[0], c1, c2, renderer);
    }

    public static void drawLine(Vector3D v1, Vector3D v2, Vector3D c, Vector3D c2, Renderer renderer) {
        DrawLine3D.drawLine(v1, v2, c, c2, renderer);
    }

    protected final static boolean isOutOfBounds(float x, float y) {
        return x < 0 || y < 0 || x >= Window.defaultWidth || y >= Window.defaultHeight;
    }

    public final static Line3D yIncreasingLine3D(Vector3D v1, Vector3D v2) {
        if (v1.y > v2.y) {
            return new Line3D(v2, v1);
        }
        return new Line3D(v1, v2);
    }

    public final static Edge yIncreasingEdge(VertexOut v1, VertexOut v2, Material material) {
        if (v1.p_proj.y > v2.p_proj.y) { //decreasing y line, so flip
            return new Edge(v2, v1, 1, material);
        }
        return new Edge(v1, v2, 0, material);
    }
}
