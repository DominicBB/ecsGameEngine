package Rendering.renderUtil.Lerpers;

import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class FlatLerper implements ILerper {
    public final Vector3D p_proj_step;

    public final Vector2D texCoordStep;
    public final Vector2D specCoordStep;

    private float specStep;

    public FlatLerper() {
        this.p_proj_step = Vector3D.newZeros();

        this.texCoordStep = Vector2D.newZeros();
        this.specCoordStep = Vector2D.newZeros();
        specStep = 0f;
    }

    public FlatLerper(Vector3D sColorStep, Vector2D texCoordStep, Vector2D specCoordStep, float specStep) {
        this.p_proj_step = Vector3D.newZeros();

        this.texCoordStep = texCoordStep;
        this.specCoordStep = specCoordStep;
        this.specStep = specStep;
    }

    @Override
    public void lerp(LerpValues lp) {
        lp.getTexCoord().add(texCoordStep);
        lp.getSpecCoord().add(specCoordStep);
        lp.setSpecularity(lp.getSpecularity() + specStep);
    }

    @Override
    public void reuse(Vector3D sColorStep, Vector2D texCoordStep, Vector2D specCoordStep, float specStep, Vector3D n_ws_step, Vector3D p_ws_step) {
        this.texCoordStep.set(texCoordStep);
        this.specCoordStep.set(specCoordStep);
        this.specStep = specStep;
    }
}
