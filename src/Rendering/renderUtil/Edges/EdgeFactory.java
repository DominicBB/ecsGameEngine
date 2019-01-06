package Rendering.renderUtil.Edges;

import Rendering.renderUtil.VOutfi;

public class EdgeFactory {

    public static void reuseEdge(Edge edge, VOutfi v1, VOutfi v2, int dy, boolean isOnLeft) {
        edge.reuse(v1, v2, isOnLeft, dy);
    }

    public static Edge createEdge(VOutfi v1, VOutfi v2, int dy, boolean isOnLeft) {
        return new Edge(v1, v2, isOnLeft, dy);
    }


}
