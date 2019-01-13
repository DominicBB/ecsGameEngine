package rendering.renderUtil.edges;

import rendering.renderUtil.VertexOut;
import util.mathf.Mathf;

public class Edge {
    public VertexOut v1, v2;

    public int yStart;
    public int yEnd;

    public int deltaYInt;
    public float dy;
    public boolean isOnLeft;

    public Edge(VertexOut v1, VertexOut v2, boolean isOnLeft, float dy) {
        setUp(v1, v2, isOnLeft, dy);
    }

    public final void reuse(VertexOut v1, VertexOut v2, boolean isOnLeft, float dy) {
        setUp(v1, v2, isOnLeft, dy);

    }

    private void setUp(VertexOut v1, VertexOut v2, boolean isOnLeft, float dy) {
        this.v1 = v1;
        this.v2 = v2;
        this.dy = dy;
        this.isOnLeft = isOnLeft;
        setYBounds(v1, v2);
    }

    private void setYBounds(VertexOut v1, VertexOut v2) {
        yStart = Mathf.fastCeil(v1.p_proj.y);
        yEnd = Mathf.fastCeil(v2.p_proj.y);
        deltaYInt = yEnd - yStart;
    }

    private Edge() {

    }

    public static Edge newEmpty() {
        return new Edge();
    }
}
