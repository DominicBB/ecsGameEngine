package Rendering.renderUtil;

import Rendering.Materials.Material;
import Rendering.renderUtil.Lerpers.LerpValues;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;


public class Edge {

    public VertexOut v1;
    public VertexOut v2;

    public final LerpValues lerpValues;

    public int yStart;
    public int yEnd;

    public float deltaX;
    public float deltaY;
    public float deltaYceil;
    public int deltaYInt;

    /*public final float xStep, zStep, wStep;

    public final float specularityStep;

    public final float tuStep, tvStep;
    public final float suStep, svStep;

    public final float sColorx, sColory, sColorz, sColora;*/

    public int handiness;

    protected Material material;

    public Edge(VertexOut v1, VertexOut v2, int handiness, Material material) {
        this.v1 = v1;
        this.v2 = v2;
        this.handiness = handiness;
        this.material = material;
        setUp();
        this.lerpValues = new LerpValues(v1.p_proj, yStart, v1.texCoord, v1.specCoord, v1.spec, v1.surfaceColor);

        /*//calculate steps
        sColorx = (v2.surfaceColor.x - v1.surfaceColor.x) / deltaYceil;
        sColory = (v2.surfaceColor.y - v1.surfaceColor.y) / deltaYceil;
        sColorz = (v2.surfaceColor.z - v1.surfaceColor.z) / deltaYceil;
        sColora = (v2.surfaceColor.w - v1.surfaceColor.w) / deltaYceil;


        suStep = (v2.specCoord.x - v1.specCoord.x) / deltaYceil;
        svStep = (v2.specCoord.y - v1.specCoord.y) / deltaYceil;

        specularityStep = (v2.spec - v1.spec) / deltaYceil;

        xStep = deltaX / deltaYceil;
        zStep = (v2.p_proj.z - v1.p_proj.z) / deltaYceil;
        wStep = (v2.p_proj.w - v1.p_proj.w) / deltaYceil;

        tuStep = (v2.texCoord.x - v1.texCoord.x) / deltaYceil;
        tvStep = (v2.texCoord.y - v1.texCoord.y) / deltaYceil;*/


    }

    private void setUp() {
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
        lerpValues.lerp();
    }

    public final void reuse(VertexOut v1, VertexOut v2, int handiness) {
        this.v1 = v1;
        this.v2 = v2;
        this.handiness = handiness;
        this.lerpValues.reset(v1.p_proj, yStart, v1.texCoord, v1.specCoord, v1.spec, v1.surfaceColor);
        setUp();
    }

    private Edge() {
        this.lerpValues = new LerpValues(Vector3D.newOnes(),
                0, Vector2D.newOnes(), Vector2D.newOnes(), 0.0f, Vector3D.newOnes());
    }

    public static Edge newEmpty() {
        return new Edge();
    }
}
