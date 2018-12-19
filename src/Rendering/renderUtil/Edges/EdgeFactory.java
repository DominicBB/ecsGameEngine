package Rendering.renderUtil.Edges;

import Rendering.Materials.Material;
import Rendering.renderUtil.interpolation.LerperFactory;
import Rendering.renderUtil.VertexOut;

public class EdgeFactory {

    private LerperFactory lerperFactory = new LerperFactory();

    public void reuseEdge(Edge edge, VertexOut v1, VertexOut v2, Material material) {
        int handiness = 0;
        if (v1.p_proj.y > v2.p_proj.y) {
            handiness = 1;
            VertexOut temp = v1;
            v1 = v2;
            v2 = temp;
        }

        edge.reuse(v1, v2, handiness);
        lerperFactory.setLerper(material, edge.interpolants, v1, v2, 1f/ edge.deltaYceil);
    }
}
