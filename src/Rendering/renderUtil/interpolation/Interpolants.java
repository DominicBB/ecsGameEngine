package Rendering.renderUtil.interpolation;


import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class Interpolants {
    public final Vector3D p_proj;

    public int yInt = 0;
    public int xInt = 0;

    public final Vector2D texCoord;
    public final Vector2D specCoord;

    public float specularity;
    public float invW;

    public final Vector3D surfaceColor;

    public final Vector3D p_ws;
    public final Vector3D n_ws;

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
        this.p_ws = Vector3D.newZeros();
        this.n_ws = Vector3D.newZeros();
    }

    public void lerp() {
        lerper.lerp(this);
    }

    public void setLerper(ILerper lerper) {
        this.lerper = lerper;
    }

    public final void reset(Vector3D p_proj, int yStart, Vector2D texCoord, Vector2D specCoord,
                            float spec, Vector3D surfaceColor, float invW) {
        this.p_proj.set(p_proj);
        this.yInt = yStart;
        this.texCoord.set(texCoord);
        this.specCoord.set(specCoord);
        this.specularity = spec;
        this.surfaceColor.set(surfaceColor);
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
