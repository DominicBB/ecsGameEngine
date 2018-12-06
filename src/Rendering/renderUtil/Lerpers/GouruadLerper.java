package Rendering.renderUtil.Lerpers;

import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class GouruadLerper implements ILerper {
    public final Vector3D p_proj_step;
    public final Vector3D sColorStep;
    public final Vector2D texCoordStep;
    public final Vector2D specCoordStep;

    public float specStep;


    public GouruadLerper() {
        this.p_proj_step = Vector3D.newZeros();
        this.sColorStep = Vector3D.newZeros();
        this.texCoordStep = Vector2D.newZeros();
        this.specCoordStep = Vector2D.newZeros();
        specStep = 0f;
    }

    public GouruadLerper(Vector3D sColorStep, Vector2D texCoordStep,
                         Vector2D specCoordStep, float specStep, Vector3D n_ws_step, Vector3D p_ws_step) {
        this.p_proj_step = Vector3D.newZeros();
        this.sColorStep = sColorStep;
        this.texCoordStep = texCoordStep;
        this.specCoordStep = specCoordStep;
        this.specStep = specStep;

    }


    @Override
    public final void lerp(LerpValues lp) {
        lp.getTexCoord().add(texCoordStep);
        lp.getSpecCoord().add(specCoordStep);
        lp.setSpecularity(lp.getSpecularity() + specStep);
        lp.getSurfaceColor().add(sColorStep);
    }

    @Override
    public final void reuse(Vector3D sColorStep, Vector2D texCoordStep,
                            Vector2D specCoordStep, float specStep, Vector3D n_ws_step, Vector3D p_ws_step) {

        this.sColorStep.set(sColorStep);
        this.texCoordStep.set(texCoordStep);
        this.specCoordStep.set(specCoordStep);

        this.specStep = specStep;

    }
}
