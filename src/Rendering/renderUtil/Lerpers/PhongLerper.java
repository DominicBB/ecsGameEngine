package Rendering.renderUtil.Lerpers;

import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class PhongLerper implements ILerper {
    public final Vector3D p_proj_step;

    public final Vector2D texCoordStep;
    public final Vector2D specCoordStep;

    public float specStep;

    public final Vector3D n_ws_step;
    public final Vector3D p_ws_step;
    public float invZStep;

    public PhongLerper() {
        this.p_proj_step = Vector3D.newZeros();

        this.texCoordStep = Vector2D.newZeros();
        this.specCoordStep = Vector2D.newZeros();
        specStep = 0f;
        invZStep = 0f;

        n_ws_step = Vector3D.newZeros();
        p_ws_step = Vector3D.newZeros();
    }


    @Override
    public final void lerp(Interpolants lp) {
        lp.getP_proj().add(p_proj_step);
        lp.getTexCoord().add(texCoordStep);
        lp.getSpecCoord().add(specCoordStep);
        lp.setSpecularity(lp.getSpecularity() + specStep);
        lp.setInvW(lp.getInvW() + invZStep);

    }

}
