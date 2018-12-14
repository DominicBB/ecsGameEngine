package Rendering.renderUtil.Lerpers;

import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class GouruadLerper implements ILerper {
    public final Vector3D p_proj_step;
    public final Vector3D sColorStep;
    public final Vector2D texCoordStep;
    public final Vector2D specCoordStep;

    public float specStep;
    public float invWStep;


    public GouruadLerper() {
        this.p_proj_step = Vector3D.newZeros();
        this.sColorStep = Vector3D.newZeros();
        this.texCoordStep = Vector2D.newZeros();
        this.specCoordStep = Vector2D.newZeros();
        specStep = 0f;
        invWStep = 0f;
    }


    @Override
    public final void lerp(Interpolants lp) {
        lp.getP_proj().add(p_proj_step);
        lp.getTexCoord().add(texCoordStep);
        lp.getSpecCoord().add(specCoordStep);
        lp.setSpecularity(lp.getSpecularity() + specStep);
        lp.getSurfaceColor().add(sColorStep);
        lp.setInvW(lp.getInvW() + invWStep);
    }

}
