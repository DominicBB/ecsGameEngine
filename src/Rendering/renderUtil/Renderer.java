package Rendering.renderUtil;

import Rendering.Clipping.ClippingSystem;
import Rendering.Materials.Material;
import Rendering.drawers.Draw;
import Rendering.renderUtil.Bitmaps.BitmapABGR;
import Rendering.renderUtil.Bitmaps.ColorBuffer;
import core.Window;
import util.Mathf.Mathf3D.Matrix4x4;
import util.Mathf.Mathf3D.Triangle;
import util.FloatBuffer;
import util.Mathf.Mathf3D.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    //TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public BitmapABGR colorBuffer;
    public FloatBuffer zBuffer;

    private ClippingSystem clippingSystem = new ClippingSystem();
    private final List<VertexOut> clippedVertices = new ArrayList<>();
    private final VertexOut[] vertexOuts = new VertexOut[3];
    private final Matrix4x4 screenSpaceM;

    public Renderer(int width, int height) {
        this.colorBuffer = new ColorBuffer(width, height);
        this.zBuffer = new FloatBuffer(width, height);
        screenSpaceM = constructScreenSpaceM(width, height);
    }

    private Matrix4x4 constructScreenSpaceM(int width, int height) {
        return (Matrix4x4.newScale(0.5f * width, 0.5f * height, 1))
                .compose(Matrix4x4.newTranslation(1, 1, 0));
        /*return (Matrix4x4.newTranslation(1f,1f,0f))
                .compose(Matrix4x4.newScale(0.5f * width, 0.5f * height, 1));*/
    }

    public void drawTriangle(VertexOut v1, VertexOut v2, VertexOut v3, Material material) {

        /*IShader shader = material.getShader();
        vertexOuts[0] = shader.vert(v1, material);
        vertexOuts[1] = shader.vert(v2, material);
        vertexOuts[2] = shader.vert(v3, material);*/
        vertexOuts[0] = v1;
        vertexOuts[1] = v2;
        vertexOuts[2] = v3;

        //check for geometry shader
        if (material.hasGeometryShader()) {
            material.getGeometryShader().geom(vertexOuts, material);
        }

        //backfaceCull
        if (backFaceCullPreClip(vertexOuts[0], vertexOuts[1], vertexOuts[2]))
            return;

        //clip
        clippingSystem.clipTriangle(clippedVertices, vertexOuts[0], vertexOuts[1], vertexOuts[2]);
        if (clippedVertices.isEmpty()) return;


        VertexOut v2Out, v3Out;
        VertexOut v1Out = clippedVertices.get(0);

        v1Out = moveToScreenSpaceNew(v1Out);

        int end = clippedVertices.size() - 1;
        for (int i = 1; i < end; i++) {
            v2Out = clippedVertices.get(i);
            v3Out = clippedVertices.get(i + 1);

            //perspective divide
            v2Out = moveToScreenSpaceNew(v2Out);
            v3Out = moveToScreenSpaceNew(v3Out);

            Draw.fillPolygon(v1Out, v2Out, v3Out,
                    this, material);

        }
    }

    public void wireFrameTriangle(Vertex v1, Vertex v2, Vertex v3, Material material) {


        VertexOut v1Out = new VertexOut(RenderState.mvp.multiply4x4(v1.vec),
                v1.texCoord, v1.specCoord, 1f, Vector3D.newOnes(), v1.normal, v1.vec, 1f);
        VertexOut v2Out = new VertexOut(RenderState.mvp.multiply4x4(v2.vec),
                v2.texCoord, v2.specCoord, 1f, Vector3D.newOnes(), v2.normal, v2.vec, 1f);
        VertexOut v3Out = new VertexOut(RenderState.mvp.multiply4x4(v3.vec),
                v3.texCoord, v3.specCoord, 1f, Vector3D.newOnes(), v3.normal, v3.vec, 1f);

        //clip
        clippingSystem.clipTriangle(clippedVertices, v1Out, v2Out, v3Out);
        if (clippedVertices.isEmpty()) return;

        /*for(VertexOut vertexOut: clippedVertices){
            moveToScreenSpace(vertexOut);
        }*/
        v1Out = clippedVertices.get(0);
        v1Out = moveToScreenSpaceNew(v1Out);

        int end = clippedVertices.size() - 1;
        for (int i = 1; i < end; i++) {
            v2Out = clippedVertices.get(i);
            v3Out = clippedVertices.get(i + 1);

            //perspective divide
            v2Out = moveToScreenSpaceNew(v2Out);
            v3Out = moveToScreenSpaceNew(v3Out);

            //backface cull
            if (backFaceCull(v1Out, v2Out, v3Out))
                return;

            Draw.wireframePolygon(v1Out, v2Out, v3Out,
                    this, material);
        }
    }

    public void onFragShaded(int x, int y, Vector3D color, Material material) {
        //check for blending etc.
        setFinalColor(x, y, color);
    }

    public void setFinalColor(int x, int y, Vector3D color) {
        Colorf.clampNonAlloc(color);
//        colorBuffer.setPixel(x, y, /*Colorf.clamp(color)*/ color);
        setPixel(x, y, color);
    }

    private void setPixel(int x, int y, Vector3D color) {
        int i = (y * Window.defaultWidth + x) * 3;

//        Window.buff1[i] = (byte) (((int) color.w) & 0xFF);
        Window.buff1[i] = (byte) (((int) color.z) & 0xFF);
        Window.buff1[i + 1] = (byte) (((int) color.y) & 0xFF);
        Window.buff1[i + 2] = (byte) (((int) color.x) & 0xFF);


    }

    private boolean backFaceCull(VertexOut v1, VertexOut v2, VertexOut v3) {
        return Triangle.z_normal(v1.p_proj, v2.p_proj, v3.p_proj) >= 0;//TODO: possibly '>0'. p_proj or p_ws
    }

    private boolean backFaceCullPreClip(VertexOut v1, VertexOut v2, VertexOut v3) {
        return Triangle.z_normal(v1.p_proj, v2.p_proj, v3.p_proj) >= 0;//TODO: possibly '>0'. p_proj or p_ws
    }

    private final float halfWidth = (Window.defaultWidth - 1f) * 0.5f;
    private final float halfHeight = (Window.defaultHeight - 1f) * 0.5f;

    private void moveToScreenSpace(VertexOut v) {
        v.wDivide();
        v.p_proj.x = (v.p_proj.x + 1) * halfWidth;
        v.p_proj.y = (v.p_proj.y + 1) * halfHeight;
    }

    private VertexOut moveToScreenSpaceNew(VertexOut v) {
        VertexOut nV = v.wDivideNew();
        nV.p_proj.x = (nV.p_proj.x + 1) * halfWidth;
        nV.p_proj.y = (nV.p_proj.y + 1) * halfHeight;
        return nV;
    }


    public FloatBuffer getzBuffer() {
        return zBuffer;
    }

}
