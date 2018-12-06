package Rendering.renderUtil;

import Rendering.Materials.Material;
import Rendering.drawers.Draw;
import util.Mathf.Mathf3D.Matrix4x4;
import util.Mathf.Mathf3D.Plane;
import util.Mathf.Mathf3D.Triangle;
import util.Mathf.Mathf3D.Vector3D;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class RenderContext extends Bitmap {
    private List<Plane> fustrumClipPlanes;
    private float[] zBuffer;

    //TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private LightingState lightingState;
    private Vector3D cameraPos;

    public Matrix4x4 MVP;
    public Matrix4x4 projectionToWorld;

    public RenderContext(int width, int height) {
        super(width, height);
    }

    public final void setPixelFromTexture(int x, int y, Bitmap texture, int u, int v, Vector3D shade) {
        int buffIndex = (x + y * width) << 2;
        int tIndex = (u + v * texture.width) << 2;
        byteArray[buffIndex] = texture.byteArray[tIndex];//a
        byteArray[buffIndex + 1] = (byte) ((texture.byteArray[tIndex + 1] & 0xFF) * shade.z); //b
        byteArray[buffIndex + 2] = (byte) ((texture.byteArray[tIndex + 2] & 0xFF) * shade.y); //g
        byteArray[buffIndex + 3] = (byte) ((texture.byteArray[tIndex + 3] & 0xFF) * shade.x);//r
    }

    public final void drawTriangle(Vertex v1, Vertex v2, Vertex v3, Material material) {

        /*List<Vertex> zClippedTriangles = clip(zPlanes, v1, v2, v3);
        if (zClippedTriangles.isEmpty()) return;*/
        VertexOut v1Out = material.getShader().vert(v1, this, material);
        VertexOut v2Out = material.getShader().vert(v2, this, material);
        VertexOut v3Out = material.getShader().vert(v3, this, material);

        //check for geometry shader

        //clip
        List<VertexOut> clippedVertices = clip(fustrumClipPlanes, v1Out, v2Out, v3Out);
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


    private boolean backFaceCull(VertexOut v1, VertexOut v2, VertexOut v3) {
        return Triangle.z_normal(v1.p_proj, v2.p_proj, v3.p_proj) >= 0;//TODO: possibly '>0'. p_proj or p_ws
    }

    private void perspectiveDivide(VertexOut v1, VertexOut v2, VertexOut v3) {
        v1.wDivide();
        v2.wDivide();
        v3.wDivide();
    }

    private List<VertexOut> clip(List<Plane> clipPlanes, VertexOut v1, VertexOut v2, VertexOut v3) {
        return Clipper.clipTriangle(clipPlanes, v1, v2, v3);
    }

    public void setFustrumClipPlanes(List<Plane> fustrumClipPlanes) {
        this.fustrumClipPlanes = fustrumClipPlanes;
    }

    public float[] getzBuffer() {
        return zBuffer;
    }

    public float getzBufferVal(int x, int y) {
        return zBuffer[y * width + x];
    }

    public void setzBufferVal(int x, int y, float value) {
        this.zBuffer[y * width + x] = value;
    }

    public void resetZBuffer() {
//        Arrays.fill(zBuffer, Float.POSITIVE_INFINITY);
        for (int i = 0; i < zBuffer.length; i++) {
            zBuffer[i] = Float.POSITIVE_INFINITY;
        }
    }

    public void initZBuffer(int width, int height) {
        zBuffer = new float[width * height];
    }

    public Vector3D screenSpaceToWorldSpace(Vector3D vector3D) {

        Vector3D res = new Vector3D(
                vector3D.x * vector3D.w,
                vector3D.y * vector3D.w,
                vector3D.z * vector3D.w,
                vector3D.w

        );
        return projectionToWorld.multiply4x4(vector3D);
    }

    public Vector3D worldSpaceToScreenSpace(Vector3D vector3D) {
        Vector3D proj = MVP.multiply4x4(vector3D);
        proj.x = proj.x / proj.w;
        proj.y = proj.y / proj.w;
        proj.z = proj.z / proj.w;
        return proj;
    }

    public LightingState getLightingState() {
        return lightingState;
    }

    public Vector3D getCameraPos() {
        return cameraPos;
    }
}
