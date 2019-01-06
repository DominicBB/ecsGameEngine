package Rendering.Renderers;

import Rendering.Clipping.ClippingSystem;
import Rendering.Materials.Material;
import Rendering.drawers.fill.TriangleRasterizer;
import Rendering.renderUtil.Colorf;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.VOutfi;
import Rendering.renderUtil.VertexOut;
import util.Mathf.Mathf3D.Triangle;
import util.Mathf.Mathf3D.Vec4fi;
import util.Mathf.Mathf3D.Vector3DInt;

import java.util.ArrayList;
import java.util.List;

import static Rendering.renderUtil.RenderState.HALF_HEIGHT;
import static Rendering.renderUtil.RenderState.HALF_WIDTH;

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

        VOutfi v1Out, v2Out, v3Out;
        v1Out = prepareForRaster(clippedVertices.get(0));

        int end = clippedVertices.size() - 1;
        for (int i = 1; i < end; i++) {
            v2Out = prepareForRaster(clippedVertices.get(i));
            v3Out = prepareForRaster(clippedVertices.get(i + 1));

            //backfaceCull, HAVE TO BACKFACE CULL HERE
            if (backFaceCullPreClip(v1Out, v2Out, v3Out))
                return;

            triangleRasterizer.fillTriangle(v1Out, v2Out, v3Out);
        }
    }

    public static void onFragShaded(int x, int y, Vec4fi color, Material material) {
        //check for blending etc.
        setFinalColor(x, y, color);
//        setFinalColor(x, y, color, finalColor);
    }

    private static void setFinalColor(int x, int y, Vec4fi color) {
        color.toInt();
        Colorf.clampMaxNonAlloc(color);
        RenderState.colorBuffer.setPixel(x, y, color);
    }

    // this sucks :(
   /* private static void setFinalColor(int x, int y, Vec4f color, Vector3DInt colori) {
        Colorf.clampMaxNonAllocBit(color, colori);
        RenderState.colorBuffer.setPixel(x, y, colori);
    }*/


    static boolean backFaceCullPreClip(VOutfi v1, VOutfi v2, VOutfi v3) {
        return Triangle.z_crossProd(v1.p_proj, v2.p_proj, v3.p_proj) >= 0;//TODO: possibly '>0'. p_proj or p_ws
//        return Triangle.z_crossProd(v1.p_ws, v2.p_ws, v3.p_ws) <= 0;//TODO: possibly '>0'. p_proj or p_ws
    }


    static void moveToScreenSpace(VertexOut v) {
        v.wDivide();
        v.p_proj.x = (v.p_proj.x + 1) * HALF_WIDTH;
        v.p_proj.y = (v.p_proj.y + 1) * HALF_HEIGHT;
    }

    static VOutfi prepareForRaster(VertexOut v) {
        VOutfi nV = v.wDivideNew();
       /* nV.p_proj.x = Rasterfi.multiply(nV.p_proj.x + D_FACTOR, HALF_WIDTH,0);
        nV.p_proj.y = Rasterfi.multiply(nV.p_proj.y + D_FACTOR, HALF_HEIGHT, 0);*/
       if(nV.p_proj.w <0||nV.invW <0){
           return nV;
       }
        return nV;
    }


}
