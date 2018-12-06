package Rendering.renderUtil.Lerpers;


import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class LerpValues {
    Vector3D pos_proj;

    private int yInt = 0;
    private int xInt = 0;

    private Vector2D textCoord;
    private Vector2D specCoord;

    private float specularity;

    private Vector3D surfaceColor;

    private Vector3D p_ws;
    private Vector3D n_ws;

    private ILerper lerper;

    public LerpValues(Vector3D pos_proj, int yInt, Vector2D textCoord, Vector2D specCoord,
                      float specularity, Vector3D surfaceColor) {
        this.pos_proj = pos_proj;
        this.yInt = yInt;
        this.textCoord = textCoord;
        this.specCoord = specCoord;
        this.specularity = specularity;
        this.surfaceColor = surfaceColor;
    }

    public void lerp() {
        lerper.lerp(this);
    }

    public Vector3D getPos_proj() {
        return pos_proj;
    }

    public void setPos_proj(Vector3D pos_proj) {
        this.pos_proj = pos_proj;
    }

    public int getyInt() {
        return yInt;
    }

    public void setyInt(int yInt) {
        this.yInt = yInt;
    }

    public int getxInt() {
        return xInt;
    }

    public void setxInt(int xInt) {
        this.xInt = xInt;
    }

    public Vector2D getTexCoord() {
        return textCoord;
    }

    public void setTextCoord(Vector2D textCoord) {
        this.textCoord = textCoord;
    }

    public Vector2D getSpecCoord() {
        return specCoord;
    }

    public void setSpecCoord(Vector2D specCoord) {
        this.specCoord = specCoord;
    }

    public float getSpecularity() {
        return specularity;
    }

    public void setSpecularity(float specularity) {
        this.specularity = specularity;
    }

    public Vector3D getSurfaceColor() {
        return surfaceColor;
    }

    public void setSurfaceColor(Vector3D surfaceColor) {
        this.surfaceColor = surfaceColor;
    }

    public Vector3D getN_ws() {
        return n_ws;
    }

    public void setN_ws(Vector3D n_ws) {
        this.n_ws = n_ws;
    }

    public Vector3D getP_ws() {
        return p_ws;
    }

    public void setP_ws(Vector3D p_ws) {
        this.p_ws = p_ws;
    }

    public void setLerper(ILerper gouruadLerper) {
        this.lerper = lerper;
    }

    public void reset() {
        this.pos_proj = Vector3D.ZERO;
        this.yInt = 0;
        this.textCoord = Vector2D.ZERO;
        this.specCoord = Vector2D.ZERO;
        this.specularity = 0f;
        this.surfaceColor = Vector3D.ZERO;
    }

    public final void reset(Vector3D pos_proj, int yStart, Vector2D texCoord, Vector2D specCoord, float spec, Vector3D surfaceColor) {
        this.pos_proj = pos_proj;
        this.yInt = yStart;
        this.textCoord = texCoord;
        this.specCoord = specCoord;
        this.specularity = spec;
        this.surfaceColor = surfaceColor;
    }

    public static LerpValues newEmpty() {
        return new LerpValues(
                Vector3D.newOnes(),
                0,
                Vector2D.newOnes(),
                Vector2D.newOnes(),
                0f,
                Vector3D.newOnes()
        );
    }
}
