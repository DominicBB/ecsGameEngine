package Rendering.renderUtil;

import util.Mathf.Mathf;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class VertexOut {
    public final Vector3D p_proj;
    public final Vector2D texCoord;
    public final Vector2D specCoord;

    public final Vector3D surfaceColor;

    public final Vector3D p_ws;
    public final Vector3D n_ws;

    public final float spec;
    public final float invW;

    public VertexOut(Vector3D p_proj, Vector2D texCoord, Vector2D specCoord, float spec, Vector3D surfaceColor,
                     Vector3D n_ws, Vector3D p_ws, float invW) {
        this.p_proj = p_proj;
        this.texCoord = (texCoord == null) ? Vector2D.newOnes() : texCoord;
        this.specCoord = (specCoord == null) ? Vector2D.newOnes() : specCoord;
        this.spec = spec;
        this.surfaceColor = (surfaceColor == null) ? Vector3D.newOnes() : surfaceColor;
        this.p_ws = (p_ws == null) ? Vector3D.newOnes() : p_ws;
        this.n_ws = (n_ws == null) ? Vector3D.newOnes() : n_ws;
        this.invW = invW;

    }

    public void wDivide() {
        if (p_proj.w == 0)
            return;

        p_proj.x = p_proj.x / p_proj.w;
        p_proj.y = p_proj.y / p_proj.w;
        p_proj.z = p_proj.z / p_proj.w;
    }

    public VertexOut wDivideNew() {

        if (p_proj.w == 0)
            return this;

        Vector3D vec = new Vector3D(
                p_proj.x / p_proj.w,
                p_proj.y / p_proj.w,
                p_proj.z / p_proj.w,
                p_proj.w);

        return new VertexOut(vec, texCoord, specCoord, spec, surfaceColor, n_ws, p_ws, invW);
    }


    public VertexOut lerp(VertexOut destination, float lerpAmt) {
        return new VertexOut(
                p_proj.lerpWithW(destination.p_proj, lerpAmt),
                texCoord.lerp(destination.texCoord, lerpAmt),
                specCoord.lerp(destination.specCoord, lerpAmt),
                Mathf.lerp(spec, destination.spec, lerpAmt),
                surfaceColor.lerpWithW(destination.surfaceColor, lerpAmt),
                n_ws.lerpWithW(destination.n_ws, lerpAmt),
                p_ws.lerpWithW(destination.p_ws, lerpAmt),
                Mathf.lerp(invW, destination.invW, lerpAmt)
        );
    }

}
