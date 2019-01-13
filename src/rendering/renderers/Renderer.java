package rendering.renderers;

import rendering.Materials.Material;
import rendering.clipping.ClippingSystem;
import rendering.drawers.fill.TriangleRasterizer;
import rendering.renderUtil.Colorf;
import rendering.renderUtil.RenderState;
import rendering.renderUtil.VOutfi;
import rendering.renderUtil.VertexOut;
import util.Mathf.Mathf2D.Vec2f;
import util.Mathf.Mathf3D.Triangle;
import util.Mathf.Mathf3D.Vec4fi;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private final ClippingSystem clippingSystem = new ClippingSystem();
    private final List<VertexOut> clippedVertices = new ArrayList<>();
    private final VertexOut[] vertexOuts = new VertexOut[]{VertexOut.newZeros(), VertexOut.newZeros(), VertexOut.newZeros()};
    private final Vec2f v1ScreenSpace = Vec2f.newZeros(), v2ScreenSpace = Vec2f.newZeros(), v3ScreenSpace = Vec2f.newZeros();


    public final TriangleRasterizer triangleRasterizer = new TriangleRasterizer();

    public void drawTriangle(VertexOut v1, VertexOut v2, VertexOut v3) {
        //check for geometry shader
        if (RenderState.material.hasGeometryShader()) {
            RenderState.material.getGeometryShader().geom(v1, v2, v3, RenderState.material, vertexOuts);
            clippingSystem.clipTriangle(clippedVertices, vertexOuts[0], vertexOuts[1], vertexOuts[2]);
        } else {
            clippingSystem.clipTriangle(clippedVertices, v1, v2, v3);
        }

        if (clippedVertices.isEmpty()) return;

        VOutfi  v1Out = prepareForRaster(clippedVertices.get(0), v1ScreenSpace),
                v2Out = prepareForRaster(clippedVertices.get(1), v2ScreenSpace),
                v3Out = prepareForRaster(clippedVertices.get(2), v3ScreenSpace);

        if (backFaceCull(v1ScreenSpace, v2ScreenSpace, v3ScreenSpace))
            return;

        triangleRasterizer.fillTriangle(v1Out, v2Out, v3Out);

        int end = clippedVertices.size() - 1;
        for (int i = 2; i < end; i++) {
            v2Out = prepareForRaster(clippedVertices.get(i));
            v3Out = prepareForRaster(clippedVertices.get(i + 1));
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


    private static boolean backFaceCull(Vec2f v1, Vec2f v2, Vec2f v3) {
        return Triangle.z_crossProd(v1, v2, v3) > 0f;//TODO: possibly '>0'. p_proj or p_ws
//        return Triangle.z_crossProd(v1.p_ws, v2.p_ws, v3.p_ws) <= 0;//TODO: possibly '>0'. p_proj or p_ws
    }

    static VOutfi prepareForRaster(VertexOut v, Vec2f screenSpace_coord) {
        VOutfi nV = v.wDivideNew(screenSpace_coord);
       /* nV.p_proj.x = Rasterfi.multiply_noshift(nV.p_proj.x + D_FACTOR_INV, HALF_WIDTH,0);
        nV.p_proj.y = Rasterfi.multiply_noshift(nV.p_proj.y + D_FACTOR_INV, HALF_HEIGHT, 0);*/
        return nV;
    }

    static VOutfi prepareForRaster(VertexOut v) {
        VOutfi nV = v.wDivideNew();
        return nV;
    }


}
