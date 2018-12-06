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

    public PhongLerper() {
        this.p_proj_step = Vector3D.newZeros();

        this.texCoordStep = Vector2D.newZeros();
        this.specCoordStep = Vector2D.newZeros();
        specStep = 0f;

        n_ws_step = Vector3D.newZeros();
        p_ws_step = Vector3D.newZeros();
    }

    public PhongLerper(Vector3D sColorStep, Vector2D texCoordStep,
                       Vector2D specCoordStep, float specStep, Vector3D n_ws_step, Vector3D p_ws_step) {
        this.p_proj_step = Vector3D.newZeros();

        this.texCoordStep = texCoordStep;
        this.specCoordStep = specCoordStep;
        this.specStep = specStep;
        this.n_ws_step = n_ws_step;
        this.p_ws_step = p_ws_step;
    }


    @Override
    public final void lerp(LerpValues lp) {
        lp.getTexCoord().add(texCoordStep);
        lp.getSpecCoord().add(specCoordStep);
        lp.setSpecularity(lp.getSpecularity() + specStep);
    }

    @Override
    public final void reuse(Vector3D sColorStep, Vector2D texCoordStep,
                            Vector2D specCoordStep, float specStep, Vector3D n_ws_step, Vector3D p_ws_step) {

        this.texCoordStep.set(texCoordStep);
        this.specCoordStep.set(specCoordStep);

        this.specStep = specStep;

        this.n_ws_step.set(n_ws_step);
        this.p_ws_step.set(p_ws_step);

    }
}
