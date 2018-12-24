package Rendering.renderUtil.Edges;

import Rendering.renderUtil.interpolation.Interpolants;
import Rendering.renderUtil.VertexOut;
import util.Mathf.Mathf;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class Edge {

    public final Interpolants interpolants;

    public int yStart;
    public int yEnd;

    public int deltaYInt;
    public boolean isOnLeft;

    public Edge(VertexOut v1, VertexOut v2, boolean isOnLeft) {
        this.isOnLeft = isOnLeft;
        setYBounds(v1, v2);
        this.interpolants = new Interpolants(v1.p_proj, yStart, v1.texCoord, v1.specCoord, v1.spec, v1.surfaceColor, v1.invW);
    }

    private void setYBounds(VertexOut v1, VertexOut v2) {
        yStart = Mathf.fastCeil(v1.p_proj.y);
        yEnd = Mathf.fastCeil(v2.p_proj.y);
        deltaYInt = yEnd - yStart;
    }

    public final void reuse(VertexOut v1, VertexOut v2, boolean isOnLeft) {
        this.isOnLeft = isOnLeft;
        setYBounds(v1, v2);
        this.interpolants.reset(v1.p_proj, yStart, v1.texCoord, v1.specCoord, v1.spec, v1.surfaceColor, v1.invW);
    }

    private Edge() {
        this.interpolants = new Interpolants(Vector3D.newOnes(),
                0, Vector2D.newOnes(), Vector2D.newOnes(), 0.0f, Vector3D.newOnes(), 1f);
    }

    public static Edge newEmpty() {
        return new Edge();
    }
}
