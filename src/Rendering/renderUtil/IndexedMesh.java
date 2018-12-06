package Rendering.renderUtil;

import java.util.ArrayList;
import java.util.List;

import Rendering.Materials.Material;
import components.TransformComponent;
import util.Mathf.Mathf3D.Bounds.AABoundingBox;
import util.Mathf.Mathf3D.Matrix4x4;
import util.Mathf.Mathf3D.Transform;

public class IndexedMesh {
    public List<Vertex> vertices;
    public List<Integer> triIndices;
    public AABoundingBox aaBoundingBox;

//    public List<Vertex> transformedVertices;

    public IndexedMesh(List<Vertex> vertices) {
        this(vertices, new ArrayList<Integer>(), AABoundingBox.zeros());
    }

    public IndexedMesh(List<Vertex> vertices, List<Integer> triIndices, AABoundingBox aaBoundingBox) {
        this.vertices = vertices;
        this.triIndices = triIndices;
        this.aaBoundingBox = aaBoundingBox;
//        this.transformedVertices = new ArrayList<>(vertices.size());
    }


    public final void draw(RenderContext renderContext, Material material,
                           Transform transform) {
        Matrix4x4 transMatrix = transform.compose();
        Matrix4x4 projTransMatrix = renderContext.MVP.compose(transMatrix);

        /*for (Vertex vertex : vertices) {
            transformedVertices.add(vertex.mulMatrix(projTransMatrix, transMatrix));
        }*/

        for (int i = 0; i < triIndices.size(); i += 3) {

            renderContext.drawTriangle(
                    vertices.get(i),
                    vertices.get(i + 1),
                    vertices.get(i + 2),
                    material);
        }
    }
}
