package rendering.renderUtil.threading.tasks;

import rendering.renderers.Renderer;
import rendering.renderUtil.VertexOut;

public class BatchTriangleTask implements ITask {
    private VertexOut[] vertices;
    private int[] triIndices;

    private int startIndex;
    private int endIndex;
    private int skipAmt;

    private Renderer renderer;

    public void setBatch(VertexOut[] vertices, int[] triIndices, int startIndex, int endIndex, int skipAmt) {
        this.vertices = vertices;
        this.triIndices = triIndices;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.skipAmt = skipAmt;
    }

    @Override
    public void doTask() {
        for (int i = startIndex; i < endIndex; i += skipAmt) {
            renderer.drawTriangle(
                    vertices[triIndices[i]],
                    vertices[triIndices[i + 1]],
                    vertices[triIndices[i + 2]]);
        }
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}
