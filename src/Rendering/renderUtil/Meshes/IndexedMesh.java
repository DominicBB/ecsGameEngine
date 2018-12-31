package Rendering.renderUtil.Meshes;

import Rendering.Materials.Material;
import Rendering.Renderers.RendererWireFrame;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.shaders.interfaces.IShader;
import util.Mathf.Mathf;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Bounds.AABoundingBox;
import util.Mathf.Mathf3D.Matrix4x4;
import util.Mathf.Mathf3D.Transform;
import util.Mathf.Mathf3D.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class IndexedMesh {
    public final List<Vertex> vertices;
    public final VertexOut[] transformedVertices;
    public final int[] triIndices;
    public final int halfTriIndices;
    public AABoundingBox aaBoundingBox;


    //    public List<VertexOut> transformedVertices;

    public IndexedMesh(List<Vertex> vertices) {
        this(vertices, new ArrayList<>(), AABoundingBox.zeros());
    }

    public IndexedMesh(List<Vertex> vertices, List<Integer> triIndices, AABoundingBox aaBoundingBox) {
        this.vertices = vertices;
        this.triIndices = triIndices.stream().mapToInt(Integer::intValue).toArray();
        this.aaBoundingBox = computeAABB();
//        this.transformedVertices = new ArrayList<>(vertices.size());
        this.transformedVertices = new VertexOut[vertices.size()];
        this.halfTriIndices = (triIndices.size() / 2) - 1;
        initTV();
    }

    private void initTV() {
        for (int i = 0; i < transformedVertices.length; i++) {
            transformedVertices[i] = new VertexOut(Vector3D.newZeros(),
                    Vector2D.newZeros(), Vector2D.newZeros(),
                    0f, Vector3D.newZeros(), Vector3D.newZeros(), Vector3D.newZeros(), 0f);
        }
    }

    public void vertexShadeAllVertices() {
        IShader shader = RenderState.material.getShader();
        for (int i = 0; i < transformedVertices.length; i++) {
            shader.vertNonAlloc(vertices.get(i), RenderState.material, transformedVertices[i]);
        }
    }

    public final void drawWireframe(RendererWireFrame renderer, Material material,
                                    Transform transform) {
        Matrix4x4 transMatrix = transform.compose();
        RenderState.mvp = (transMatrix).compose(RenderState.mvp);
        RenderState.world = transMatrix;
        RenderState.transform = transform;

        final int end = triIndices.length;
        for (int i = 0; i < end; i += 3) {

            renderer.wireFrameTriangle(
                    vertices.get(triIndices[i]),
                    vertices.get(triIndices[i + 1]),
                    vertices.get(triIndices[i + 2]),
                    material);
        }
    }

    public AABoundingBox computeAABB() {
        Vector3D minValues = new Vector3D(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        Vector3D maxValues = new Vector3D(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

        int end = vertices.size();
        for (int i = 0; i < end; i++) {
            Mathf.min(minValues, vertices.get(i).vec);
            Mathf.max(maxValues, vertices.get(i).vec);
        }

        Vector3D size = maxValues.minus(minValues);
        Vector3D center = minValues.plus(size.divide(2f));

        return new AABoundingBox(center, size);
    }
}
