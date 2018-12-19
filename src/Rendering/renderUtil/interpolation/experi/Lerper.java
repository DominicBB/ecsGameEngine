package Rendering.renderUtil.interpolation.experi;

import Rendering.renderUtil.interpolation.Interpolants;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class Lerper {
    public final Vector3D p_proj_step;
    public final Vector3D sColorStep;

    public final Vector2D texCoordStep;
    public final Vector2D specCoordStep;

    public float specStep;

    public final Vector3D n_ws_step;
    public final Vector3D p_ws_step;
    public float invWStep;

    public Lerper(Vector3D p_proj_step, Vector3D sColorStep, Vector2D texCoordStep, Vector2D specCoordStep,
                  float specStep, Vector3D n_ws_step, Vector3D p_ws_step, float invWStep) {
        this.p_proj_step = p_proj_step;
        this.sColorStep = sColorStep;
        this.texCoordStep = texCoordStep;
        this.specCoordStep = specCoordStep;
        this.specStep = specStep;
        this.n_ws_step = n_ws_step;
        this.p_ws_step = p_ws_step;
        this.invWStep = invWStep;
    }

    public void lerp(Interpolants lp){
        lp.p_proj.add(p_proj_step);
        lp.texCoord.add(texCoordStep);
        lp.specCoord.add(specCoordStep);
        lp.specularity += specStep;
        lp.surfaceColor.add(sColorStep);
        lp.invW += invWStep;

        lp.n_ws.add(n_ws_step);
        lp.p_ws.add(p_ws_step);
    }


}
