package Rendering.Renderers;

import Rendering.Clipping.ClippingSystem;
import Rendering.Materials.Material;
import Rendering.drawers.fill.TriangleRasterizer;
import Rendering.renderUtil.Colorf;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.VertexOut;
import util.Mathf.Mathf3D.Triangle;
import util.Mathf.Mathf3D.Vector3D;
import util.Mathf.Mathf3D.Vector3DInt;

import java.util.ArrayList;
import java.util.List;

import static Rendering.renderUtil.RenderState.halfHeight;
import static Rendering.renderUtil.RenderState.halfWidth;

public class Renderer {

    private final ClippingSystem clippingSystem = new ClippingSystem();
    private final List<VertexOut> clippedVertices = new ArrayList<>();
    private final VertexOut[] vertexOuts = new VertexOut[]{VertexOut.newZeros(), VertexOut.newZeros(), VertexOut.newZeros()};
    public final TriangleRasterizer triangleRasterizer = new TriangleRasterizer();
    private static final Vector3DInt finalColor = new Vector3DInt(0, 0, 0, 0);

    public void drawTriangle(VertexOut v1, VertexOut v2, VertexOut v3) {
        //check for geometry shader
        if (RenderState.material.hasGeometryShader()) {
            RenderState.material.getGeometryShader().geom(v1, v2, v3, RenderState.material, vertexOuts);
            clippingSystem.clipTriangle(clippedVertices, vertexOuts[0], vertexOuts[1], vertexOuts[2]);
        } else {
            clippingSystem.clipTriangle(clippedVertices, v1, v2, v3);
        }

        if (clippedVertices.isEmpty()) return;

        VertexOut v2Out, v3Out;
        VertexOut v1Out = clippedVertices.get(0);

        v1Out = moveToScreenSpaceNew(v1Out);

        int end = clippedVertices.size() - 1;
        for (int i = 1; i < end; i++) {
            v2Out = clippedVertices.get(i);
            v3Out = clippedVertices.get(i + 1);
            if (v2Out.invW > 1f || v2Out.invW < 0.009f) System.out.println("invW: " + v2Out.invW);


            //perspective divide
            v2Out = moveToScreenSpaceNew(v2Out);
            v3Out = moveToScreenSpaceNew(v3Out);

            //backfaceCull, HAVE TO BACKFACE CULL HERE
            if (backFaceCullPreClip(v1Out, v2Out, v3Out))
                return;

            triangleRasterizer.fillTriangle(v1Out, v2Out, v3Out);
        }
    }

    public static void onFragShaded(int x, int y, Vector3D color, Material material) {
        //check for blending etc.
        setFinalColor(x, y, color);
//        setFinalColor(x, y, color, finalColor);
    }

    private static void setFinalColor(int x, int y, Vector3D color) {
        Colorf.clampMaxNonAlloc(color);
        RenderState.colorBuffer.setPixel(x, y, color);

    }

    // this sucks :(
    private static void setFinalColor(int x, int y, Vector3D color, Vector3DInt colori) {
        Colorf.clampMaxNonAllocBit(color, colori);
        RenderState.colorBuffer.setPixel(x, y, colori);
    }


    static boolean backFaceCull(VertexOut v1, VertexOut v2, VertexOut v3) {
        return Triangle.z_crossProd(v1.p_proj, v2.p_proj, v3.p_proj) >= 0;//TODO: possibly '>0'. p_proj or p_ws
    }

    static boolean backFaceCullPreClip(VertexOut v1, VertexOut v2, VertexOut v3) {
        return Triangle.z_crossProd(v1.p_proj, v2.p_proj, v3.p_proj) >= 0;//TODO: possibly '>0'. p_proj or p_ws
//        return Triangle.z_crossProd(v1.p_ws, v2.p_ws, v3.p_ws) <= 0;//TODO: possibly '>0'. p_proj or p_ws
    }


    static void moveToScreenSpace(VertexOut v) {
        v.wDivide();
        v.p_proj.x = (v.p_proj.x + 1) * halfWidth;
        v.p_proj.y = (v.p_proj.y + 1) * halfHeight;
    }

    static VertexOut moveToScreenSpaceNew(VertexOut v) {
        VertexOut nV = v.wDivideNew();
        nV.p_proj.x = (nV.p_proj.x + 1) * halfWidth;
        nV.p_proj.y = (nV.p_proj.y + 1) * halfHeight;
        return nV;
    }


}
