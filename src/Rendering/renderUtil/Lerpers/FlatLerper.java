package Rendering.renderUtil.Lerpers;

import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class FlatLerper implements ILerper {
    public final Vector3D p_proj_step;

    public final Vector2D texCoordStep;
    public final Vector2D specCoordStep;

    public float invWStep;
    public float specStep;

    public FlatLerper() {
        this.p_proj_step = Vector3D.newZeros();

        this.texCoordStep = Vector2D.newZeros();
        this.specCoordStep = Vector2D.newZeros();
        specStep = 0f;
        invWStep = 0f;
    }


    @Override
    public void lerp(Interpolants lp) {
        lp.p_proj.add(p_proj_step);
        lp.texCoord.add(texCoordStep);
        lp.specCoord.add(specCoordStep);
        lp.specularity += specStep;
        lp.invW += invWStep;
    }


}
