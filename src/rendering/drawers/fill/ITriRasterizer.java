package rendering.drawers.fill;

import rendering.renderUtil.edges.Edge;

public interface ITriRasterizer {
    void fillTriangle(Edge e1, Edge e2, Edge e3);

    void fillTriangle(Edge e1, Edge e2);

}
