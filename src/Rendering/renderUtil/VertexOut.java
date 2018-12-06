package Rendering.renderUtil;

import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class VertexOut {
    //fields
    public final Vector3D p_proj;
    public final Vector2D texCoord;
    public final Vector2D specCoord;

    public final Vector3D surfaceColor;

    public final Vector3D p_ws;
    public final Vector3D n_ws;

    public final float spec;

    public VertexOut(Vector3D p_proj, Vector2D texCoord, Vector2D specCoord, float spec, Vector3D surfaceColor,
                     Vector3D n_ws, Vector3D p_ws) {
        this.p_proj = p_proj;
        this.texCoord = texCoord;
        this.specCoord = specCoord;
        this.spec = spec;
        this.surfaceColor = surfaceColor;
        this.p_ws = p_ws;
        this.n_ws = n_ws;

    }

    public void wDivide() {
        p_proj.x = p_proj.z / p_proj.w;
        p_proj.y = p_proj.z / p_proj.w;
        p_proj.z = p_proj.z / p_proj.w;
    }
}
