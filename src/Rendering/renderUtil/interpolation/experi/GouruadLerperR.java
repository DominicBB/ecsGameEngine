package Rendering.renderUtil.interpolation.experi;

import Rendering.renderUtil.interpolation.Interpolants;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

class GouruadLerperR implements IGouruadLerper {
    float zStep;
    float invWStep;
    float specStep;

    Vector3D sColorStep;//final
    Vector2D texCoordStep;//final
    Vector2D specCoordStep; // final


    @Override
    public void lerp(Interpolants interpolants) {

    }
}
