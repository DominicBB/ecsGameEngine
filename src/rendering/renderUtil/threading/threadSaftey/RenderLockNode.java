package rendering.renderUtil.threading.threadSaftey;

import rendering.renderers.Renderer;
import rendering.drawers.fill.TriangleRasterizer;
import rendering.renderUtil.edges.Edge;
import util.mathf.Mathf2D.Bounds2D.AABoundingRect;
import util.mathf.Mathf2D.Vec2f;

import java.util.ArrayList;
import java.util.List;

public class RenderLockNode {
    public final AABoundingRect BR = new AABoundingRect(Vec2f.newZeros(), Vec2f.newZeros());

    /**
     * Triangles that could not be rendered in first pass due to render locks,
     */
    public final List<Edge> TODO = new ArrayList<>();
    /**
     * Triangles with one horizontal edge that need to be rendered
     */
    public final List<Edge> TODODoubleEdge = new ArrayList<>();

    public final long threadID;

    public final Renderer renderer = new Renderer();

    public RenderLockNode(long threadID) {
        this.threadID = threadID;
        TriangleRasterizer triangleRasterizer = renderer.triangleRasterizer;
        triangleRasterizer.setEdgesTODO(TODO);
        triangleRasterizer.setEdgesTODODouble(TODODoubleEdge);
        triangleRasterizer.setBoundingRect(BR);
    }

    public void reset() {
        BR.set(0f, 0f, 0f, 0f);
        TODO.clear();
        TODODoubleEdge.clear();
    }

    /**
     * Draw the triangles that could not be rendered due to render locks
     */
    public void drawTODOs() {
        for (int i = 0, len = TODO.size(); i < len; i += 3) {
            renderer.triangleRasterizer.scanEdge(TODO.get(i), TODO.get(i + 1), TODO.get(i + 2));
        }

        for (int i = 0, len = TODODoubleEdge.size(); i < len; i += 2) {
            renderer.triangleRasterizer.scanEdge(TODODoubleEdge.get(i), TODODoubleEdge.get(i + 1));
        }
//        System.out.println("TODO Size: "+TODO.size()+", Double Size: "+TODODoubleEdge.size());
    }
}
