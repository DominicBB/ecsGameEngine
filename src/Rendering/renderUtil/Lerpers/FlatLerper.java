package Rendering.renderUtil.Lerpers;

import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class FlatLerper implements ILerper {
    public final Vector3D p_proj_step;

    public final Vector2D texCoordStep;
    public final Vector2D specCoordStep;

    public float invZStep;
    public float specStep;

    public FlatLerper() {
        this.p_proj_step = Vector3D.newZeros();

        this.texCoordStep = Vector2D.newZeros();
        this.specCoordStep = Vector2D.newZeros();
        specStep = 0f;
        invZStep = 0f;
    }


    @Override
    public void lerp(Interpolants lp) {
        lp.getP_proj().add(p_proj_step);
        lp.getTexCoord().add(texCoordStep);
        lp.getSpecCoord().add(specCoordStep);
        lp.setSpecularity(lp.getSpecularity() + specStep);
        lp.setInvW(lp.getInvW() + invZStep);
    }


}
