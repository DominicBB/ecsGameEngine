package Rendering.renderUtil.threading.threadSaftey;

import Rendering.Renderers.Renderer;
import Rendering.drawers.fill.TriangleRasterizer;
import Rendering.renderUtil.Edges.Edge;
import util.Mathf.Mathf2D.Bounds2D.AABoundingRect;
import util.Mathf.Mathf2D.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class RenderLockNode {
    public final AABoundingRect BR = new AABoundingRect(Vector2D.newZeros(), Vector2D.newZeros());

    public final List<Edge> TODO = new ArrayList<>();
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

    public void drawTODOs() {
        for (int i = 0, len = TODO.size(); i < len; i += 3) {
            renderer.triangleRasterizer.scan(TODO.get(i), TODO.get(i + 1), TODO.get(i + 2));
        }

        for (int i = 0, len = TODODoubleEdge.size(); i < len; i += 2) {
            renderer.triangleRasterizer.scan(TODODoubleEdge.get(i), TODODoubleEdge.get(i + 1));
        }
//        System.out.println("TODO Size: "+TODO.size()+", Double Size: "+TODODoubleEdge.size());
    }
}