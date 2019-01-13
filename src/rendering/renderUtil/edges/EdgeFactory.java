package rendering.renderUtil.edges;

import rendering.renderUtil.VertexOut;

public class EdgeFactory {

    public static void reuseEdge(Edge edge, VertexOut v1, VertexOut v2, float dy, boolean isOnLeft) {
        edge.reuse(v1, v2, isOnLeft, dy);
    }

    public static Edge createEdge(VertexOut v1, VertexOut v2, float dy, boolean isOnLeft) {
        return new Edge(v1, v2, isOnLeft, dy);
    }


}
