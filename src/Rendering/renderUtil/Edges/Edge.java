package Rendering.renderUtil.Edges;

import Rendering.Materials.Material;
import Rendering.renderUtil.Lerpers.Interpolants;
import Rendering.renderUtil.VertexOut;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class Edge {

    /*public VertexOut v1;
    public VertexOut v2;*/

    public final Interpolants interpolants;

    public int yStart;
    public int yEnd;

    public float deltaX;
    public float deltaY;
    public float deltaYceil;
    public int deltaYInt;

    public int handiness;

    protected Material material;

    public Edge(VertexOut v1, VertexOut v2, int handiness, Material material) {
       /* this.v1 = v1;
        this.v2 = v2;*/
        this.handiness = handiness;
        this.material = material;
        setUp(v1, v2);
        this.interpolants = new Interpolants(v1.p_proj, yStart, v1.texCoord, v1.specCoord, v1.spec, v1.surfaceColor, v1.invW);

    }

    private void setUp(VertexOut v1, VertexOut v2) {
        //calculate deltas
        float ceily1 = (float) Math.ceil(v1.p_proj.y);
        float ceily2 = (float) Math.ceil(v2.p_proj.y);

        deltaX = v2.p_proj.x - v1.p_proj.x;
        deltaY = v2.p_proj.y - v1.p_proj.y;
        deltaYInt = (int) v2.p_proj.y - (int) v1.p_proj.y;
        deltaYceil = ceily2 - ceily1;

        yStart = (int) ceily1;
        yEnd = (int) ceily2;
    }


    public final void step() {
        interpolants.lerp();
    }

    public final void reuse(VertexOut v1, VertexOut v2, int handiness) {
        this.handiness = handiness;
        this.interpolants.reset(v1.p_proj, yStart, v1.texCoord, v1.specCoord, v1.spec, v1.surfaceColor, v1.invW);
        setUp(v1, v2);
    }

    private Edge() {
        this.interpolants = new Interpolants(Vector3D.newOnes(),
                0, Vector2D.newOnes(), Vector2D.newOnes(), 0.0f, Vector3D.newOnes(), 1f);
    }

    public static Edge newEmpty() {
        return new Edge();
    }
}
