package Rendering.renderUtil.Meshes;

import java.util.ArrayList;
import java.util.List;

import Rendering.Materials.Material;
import Rendering.Renderers.Renderer;
import Rendering.Renderers.RendererWireFrame;
import Rendering.gizmos.Gizmos;
import Rendering.renderUtil.*;
import Rendering.shaders.interfaces.IShader;
import util.Mathf.Mathf;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Bounds.AABoundingBox;
import util.Mathf.Mathf3D.Matrix4x4;
import util.Mathf.Mathf3D.Transform;
import util.Mathf.Mathf3D.Vector3D;

public class IndexedMesh {
    public List<Vertex> vertices;
    public List<Integer> triIndices;
    public AABoundingBox aaBoundingBox;

    //    public List<VertexOut> transformedVertices;
    private final VertexOut[] transformedVertices;

    public IndexedMesh(List<Vertex> vertices) {
        this(vertices, new ArrayList<Integer>(), AABoundingBox.zeros());
    }

    public IndexedMesh(List<Vertex> vertices, List<Integer> triIndices, AABoundingBox aaBoundingBox) {
        this.vertices = vertices;
        this.triIndices = triIndices;
        this.aaBoundingBox = computeAABB();
//        this.transformedVertices = new ArrayList<>(vertices.size());
        this.transformedVertices = new VertexOut[vertices.size()];
        initTV();
    }

    private void initTV() {
        for (int i = 0; i < transformedVertices.length; i++) {
            transformedVertices[i] = new VertexOut(Vector3D.newZeros(),
                    Vector2D.newZeros(), Vector2D.newZeros(),
                    0f, Vector3D.newZeros(), Vector3D.newZeros(), Vector3D.newZeros(), 0f);
        }
    }


    public final void draw(Renderer renderer, Material material,
                           Transform transform) {
        Matrix4x4 transMatrix = transform.compose();
        RenderState.mvp = (transMatrix).compose(RenderState.mvp);

        RenderState.world = transMatrix;
        RenderState.transform = transform;



        IShader shader = material.getShader();
        for (int i = 0; i < transformedVertices.length; i++) {
            shader.vertNonAlloc(vertices.get(i), material, transformedVertices[i]);
        }

        final int end = triIndices.size();
        for (int i = 0; i < end; i += 3) {

            renderer.drawTriangle(
                    transformedVertices[(triIndices.get(i))],
                    transformedVertices[(triIndices.get(i + 1))],
                    transformedVertices[(triIndices.get(i + 2))],
                    material);
        }

        Gizmos.drawBoundingVolme(new AABoundingBox(RenderState.modelSpaceToScreenSpace(aaBoundingBox.getCenter()),
                aaBoundingBox.getSize()), Colorf.RED);
//        transformedVertices = new ArrayList<>(vertices.size());
//        transformedVertices.clear();

        /*for (int i = 0; i < end; i += 3) {

            renderer.drawTriangle(
                    vertices.get(triIndices.get(i)),
                    vertices.get(triIndices.get(i + 1)),
                    vertices.get(triIndices.get(i + 2)),
                    material);
        }*/
    }

    public final void drawWireframe(RendererWireFrame renderer, Material material,
                                    Transform transform) {
        Matrix4x4 transMatrix = transform.compose();
        RenderState.mvp = (transMatrix).compose(RenderState.mvp);
        RenderState.world = transMatrix;
        RenderState.transform = transform;

        final int end = triIndices.size();
        for (int i = 0; i < end; i += 3) {

            renderer.wireFrameTriangle(
                    vertices.get(triIndices.get(i)),
                    vertices.get(triIndices.get(i + 1)),
                    vertices.get(triIndices.get(i + 2)),
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
