package Rendering.renderUtil.interpolation.experi;

import Rendering.renderUtil.interpolation.Interpolants;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

class GouruadLerperE implements IGouruadLerper {
    float invWStep;
    float specStep;

    Vector3D p_proj_step;
    Vector3D sColorStep;//final

    Vector2D texCoordStep;//final
    Vector2D specCoordStep; // final
    @Override
    public void lerp(Interpolants interpolants) {

    }
}
