package Rendering.renderUtil.interpolation;

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
        lp.p_proj.add(p_proj_step);
        lp.texCoord.add(texCoordStep);

        lp.specCoord.add(specCoordStep);

        lp.specularity += specStep;
        lp.surfaceColor.add(sColorStep);
        lp.invW += invWStep;


        //UNRAVELED
       /* float x, y, z, w;
        x = lp.p_proj.x + p_proj_step.x;
        y = lp.p_proj.y + p_proj_step.y;
        z = lp.p_proj.z + p_proj_step.z;
        w = lp.p_proj.w + p_proj_step.w;
        lp.p_proj.set(x, y, z, w);

        x = lp.texCoord.x + texCoordStep.x;
        y = lp.texCoord.y + texCoordStep.y;
        lp.specularity += specStep;
        lp.invW += invWStep;
        lp.texCoord.set(x, y);


        x = lp.surfaceColor.x + sColorStep.x;
        y = lp.surfaceColor.y + sColorStep.y;
        z = lp.surfaceColor.z + sColorStep.z;
        w = lp.surfaceColor.w + sColorStep.w;
        lp.surfaceColor.set(x, y, z, w);*/



    }

}
