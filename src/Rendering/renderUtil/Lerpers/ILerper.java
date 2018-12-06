package Rendering.renderUtil.Lerpers;

import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public interface ILerper {
    void lerp(LerpValues lerpValues);

    void reuse(Vector3D sColorStep, Vector2D texCoordStep,
               Vector2D specCoordStep, float specStep, Vector3D n_ws_step, Vector3D p_ws_step);
}
