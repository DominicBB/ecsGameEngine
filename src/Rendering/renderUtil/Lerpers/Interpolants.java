package Rendering.renderUtil.Lerpers;


import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class Interpolants {
    public Vector3D p_proj;

    public int yInt = 0;
    public int xInt = 0;

    public Vector2D texCoord;
    public Vector2D specCoord;

    public float specularity;
    public float invW;

    public Vector3D surfaceColor;

    public Vector3D p_ws;
    public Vector3D n_ws;

    private ILerper lerper;

    public Interpolants(Vector3D p_proj, int yInt, Vector2D texCoord, Vector2D specCoord,
                        float specularity, Vector3D surfaceColor, float invW) {
        this.p_proj = p_proj;
        this.yInt = yInt;
        this.texCoord = texCoord;
        this.specCoord = specCoord;
        this.specularity = specularity;
        this.surfaceColor = surfaceColor;
        this.invW = invW;
    }

    public void lerp() {
        lerper.lerp(this);
    }

    /*public Vector3D getP_proj() {
        return p_proj;
    }

    public void setP_proj(Vector3D p_proj) {
        this.p_proj = p_proj;
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
        return texCoord;
    }

    public void setTextCoord(Vector2D texCoord) {
        this.texCoord = texCoord;
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

    public float getInvW() {
        return invW;
    }

    public void setInvW(float invW) {
        this.invW = invW;
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
    }*/

    public void setLerper(ILerper lerper) {
        this.lerper = lerper;
    }

    public void reset() {
        this.p_proj = Vector3D.ZERO;
        this.yInt = 0;
        this.texCoord = Vector2D.ZERO;
        this.specCoord = Vector2D.ZERO;
        this.specularity = 0f;
        this.surfaceColor = Vector3D.ONE;
    }

    public final void reset(Vector3D p_proj, int yStart, Vector2D texCoord, Vector2D specCoord,
                            float spec, Vector3D surfaceColor, float invW) {

        this.p_proj = new Vector3D(p_proj.x, p_proj.y, p_proj.z, p_proj.w);
        this.yInt = yStart;
        this.texCoord = new Vector2D(texCoord.x, texCoord.y);
        this.specCoord = new Vector2D(specCoord.x, specCoord.y);
        this.specularity = spec;
        this.surfaceColor = new Vector3D(surfaceColor.x, surfaceColor.y, surfaceColor.z, surfaceColor.w);
        this.invW = invW;
    }

    public static Interpolants newEmpty() {
        return new Interpolants(
                Vector3D.newOnes(),
                0,
                Vector2D.newOnes(),
                Vector2D.newOnes(),
                0f,
                Vector3D.newOnes(),
                1f
        );
    }
}
