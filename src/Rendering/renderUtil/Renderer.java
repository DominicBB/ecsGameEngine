package Rendering.renderUtil;

import Rendering.Clipping.ClippingSystem;
import Rendering.Clipping.TriangleClipper;
import Rendering.Materials.Material;
import Rendering.drawers.Draw;
import util.Mathf.Mathf3D.Plane;
import util.Mathf.Mathf3D.Triangle;
import util.FloatBuffer;
import util.Mathf.Mathf3D.Vector3D;

import java.util.List;

public class Renderer {

    //TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public ColorBuffer colorBuffer;
    public FloatBuffer zBuffer;

    private ClippingSystem clippingSystem = new ClippingSystem();

    public Renderer(int width, int height) {
        this.colorBuffer = new ColorBuffer(width, height);
        this.zBuffer = new FloatBuffer(width, height);
    }


    public final void drawTriangle(Vertex v1, Vertex v2, Vertex v3, Material material) {

        /*List<Vertex> zClippedTriangles = clip(zPlanes, v1, v2, v3);
        if (zClippedTriangles.isEmpty()) return;*/
        VertexOut v1Out = material.getShader().vert(v1, material);
        VertexOut v2Out = material.getShader().vert(v2, material);
        VertexOut v3Out = material.getShader().vert(v3, material);

        //check for geometry shader

        //clip
        List<VertexOut> clippedVertices = clippingSystem.clipTriangle(v1Out, v2Out, v3Out );
        if (clippedVertices.isEmpty()) return;

        for (int i = 0; i < clippedVertices.size(); i += 3) {
            v1Out = clippedVertices.get(i);
            v2Out = clippedVertices.get(i + 1);
            v3Out = clippedVertices.get(i + 2);

            //perspective divide
            perspectiveDivide(v1Out, v2Out, v3Out);

            //backface cull
            if (backFaceCull(v1Out, v2Out, v3Out))
                return;

            Draw.fillPolygon(v1Out, v2Out, v3Out,
                    this, material);
        }
    }

    public void onFragShaded(int x, int y, Vector3D color, Material material) {
        //check for blending etc.
        setFinalColor(x, y, color);
    }

    public void setFinalColor(int x, int y, Vector3D color) {
        colorBuffer.setPixel(x, y, color);
    }

    private boolean backFaceCull(VertexOut v1, VertexOut v2, VertexOut v3) {
        return Triangle.z_normal(v1.p_proj, v2.p_proj, v3.p_proj) >= 0;//TODO: possibly '>0'. p_proj or p_ws
    }

    private void perspectiveDivide(VertexOut v1, VertexOut v2, VertexOut v3) {
        v1.wDivide();
        v2.wDivide();
        v3.wDivide();
    }


    public FloatBuffer getzBuffer() {
        return zBuffer;
    }

}
