package rendering.renderUtil.meshes;

import rendering.materials.Material;
import rendering.renderers.RendererWireFrame;
import rendering.renderUtil.RenderState;
import rendering.renderUtil.Vertex;
import rendering.renderUtil.VertexOut;
import rendering.shaders.interfaces.IShader;
import util.mathf.Mathf;
import util.mathf.Mathf2D.Vec2f;
import util.mathf.Mathf3D.Bounds.AABoundingBox;
import util.mathf.Mathf3D.Matrix4x4;
import util.mathf.Mathf3D.Transform;
import util.mathf.Mathf3D.Vec4f;

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
            transformedVertices[i] = new VertexOut(Vec4f.newZeros(),
                    Vec2f.newZeros(), Vec2f.newZeros(),
                    0f, Vec4f.newZeros(), Vec4f.newZeros(), Vec4f.newZeros(), 0f);
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
        Vec4f minValues = new Vec4f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        Vec4f maxValues = new Vec4f(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

        int end = vertices.size();
        for (int i = 0; i < end; i++) {
            Mathf.min(minValues, vertices.get(i).vec);
            Mathf.max(maxValues, vertices.get(i).vec);
        }

        Vec4f size = maxValues.minus(minValues);
        Vec4f center = minValues.plus(size.divide(2f));

        return new AABoundingBox(center, size);
    }
}
