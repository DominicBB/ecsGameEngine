package Rendering.renderUtil.Edges;

import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.VertexOut;
import Rendering.renderUtil.interpolation.LerperFactory;

public class EdgeFactory {

    private LerperFactory lerperFactory = new LerperFactory();

    public void reuseEdge(Edge edge, VertexOut v1, VertexOut v2, float dy, boolean isOnLeft) {
        edge.reuse(v1, v2, isOnLeft);
        lerperFactory.setLerper(RenderState.material, edge.IInterpolants, v1, v2, 1f / dy);
    }

    public Edge createEdge(VertexOut v1, VertexOut v2, float dy, boolean isOnLeft) {
        Edge edge = new Edge(v1, v2, isOnLeft);
        lerperFactory.createLerper(edge.IInterpolants, v1, v2, 1f / dy);
        return edge;
    }


}
