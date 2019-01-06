package Rendering.renderUtil.Edges;

import Rendering.drawers.fill.Rasterfi;
import Rendering.renderUtil.VOutfi;

public class Edge {
    public VOutfi v1, v2;

    public int yStart;
    public int yEnd;

    public int deltaYInt;
    public int dy;
    public boolean isOnLeft;

    Edge(VOutfi v1, VOutfi v2, boolean isOnLeft, int dy) {
        setUp(v1, v2, isOnLeft, dy);
    }

    final void reuse(VOutfi v1, VOutfi v2, boolean isOnLeft, int dy) {
        setUp(v1, v2, isOnLeft, dy);
    }

    private void setUp(VOutfi v1, VOutfi v2, boolean isOnLeft, int dy) {
        this.v1 = v1;
        this.v2 = v2;
        this.dy = dy;
        this.isOnLeft = isOnLeft;
        setYBounds(v1.p_proj.y, v2.p_proj.y);
    }

    private void setYBounds(int y1, int y2) {
        yStart = Rasterfi.ceil_destroy_format(y1);
        yEnd = Rasterfi.ceil_destroy_format(y2);
        deltaYInt = yEnd - yStart;
    }

    private Edge() {
    }

    public static Edge newEmpty() {
        return new Edge();
    }
}
