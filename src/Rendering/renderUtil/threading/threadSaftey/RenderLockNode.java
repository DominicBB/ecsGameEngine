package Rendering.renderUtil.threading.threadSaftey;

import Rendering.renderUtil.Edges.Edge;
import util.Mathf.Mathf2D.Bounds2D.AABoundingRect;
import util.Mathf.Mathf2D.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class RenderLockNode {
    public final AABoundingRect BR = new AABoundingRect(Vector2D.newZeros(), Vector2D.newZeros());

    public final List<Edge> TODO = new ArrayList<>();
    public final List<Edge> TODODoubleEdge = new ArrayList<>();

    private final long threadID;

    public RenderLockNode(long threadID) {
        this.threadID = threadID;
    }

    public void reset(){
        BR.set(0f,0f,0f,0f);
        TODO.clear();
        TODODoubleEdge.clear();
    }
}
