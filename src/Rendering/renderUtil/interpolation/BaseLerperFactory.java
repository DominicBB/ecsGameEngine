package Rendering.renderUtil.interpolation;

import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public abstract class BaseLerperFactory {
    protected static void calcVec2Step(Vector2D out, float factor, Vector2D vIn1, Vector2D vIn2) {
        out.set(
                (vIn2.x - vIn1.x) * factor,
                (vIn2.y - vIn1.y) * factor
        );
    }

    protected static float calcFloatStep(float factor, float f1, float f2) {
        return (f2 - f1) * factor;
    }

    protected static void calcVec3Step(Vector3D out, float factor, Vector3D vIn1, Vector3D vIn2) {
        out.set(
                (vIn2.x - vIn1.x) * factor,
                (vIn2.y - vIn1.y) * factor,
                (vIn2.z - vIn1.z) * factor,
                (vIn2.w - vIn1.w) * factor
        );
    }


    /*protected static float calcSpecLerp(Vector2D specCoordStep, Vector2D s1,Vector2D s2, float factor, boolean hasSpecMap){
        if (hasSpecMap)
            calcVec2Step(specCoordStep, factor, s1, s2);
       return calcFloatStep(factor, l1.getSpecularity(), l2.getSpecularity());
    }*/
}
