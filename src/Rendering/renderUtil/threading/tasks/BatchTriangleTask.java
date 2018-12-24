package Rendering.renderUtil.threading.tasks;

import Rendering.Renderers.Renderer;
import Rendering.drawers.fill.TriangleRasterizer;
import Rendering.renderUtil.Edges.Edge;
import Rendering.renderUtil.RenderLocks;
import Rendering.renderUtil.VertexOut;

import java.util.List;

public class BatchTriangleTask implements ITask {
    private VertexOut[] vertices;
    private List<Integer> triIndices;

    private int startIndex;
    private int endIndex;

    public Renderer renderer = new Renderer();


    public void setBatch(VertexOut[] vertices, List<Integer> triIndices, int startIndex, int endIndex) {
        this.vertices = vertices;
        this.triIndices = triIndices;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void doTask() {
        List<Edge> edges = RenderLocks.getTODO();
        List<Edge> edgesDouble = RenderLocks.getTODODoubleEdge();
        renderer.triangleRasterizer.setEdgesTODO(edges);
        renderer.triangleRasterizer.setEdgesTODODouble(edgesDouble);
        for (int i = startIndex; i < endIndex; i += 6) {
            renderer.drawTriangle(
                    vertices[triIndices.get(i)],
                    vertices[triIndices.get(i + 1)],
                    vertices[triIndices.get(i + 2)]);
        }

        TriangleRasterizer triangleRasterizer = renderer.triangleRasterizer;
        for (int i = 0, len = edges.size(); i < len; i += 3) {
            triangleRasterizer.scan(edges.get(i), edges.get(i + 1), edges.get(i + 2));
        }

        for (int i = 0, len = edgesDouble.size(); i < len; i += 2) {
            triangleRasterizer.scan(edgesDouble.get(i), edgesDouble.get(i + 1));
        }
    }
}
