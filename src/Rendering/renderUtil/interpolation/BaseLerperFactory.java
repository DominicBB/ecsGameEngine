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

    static float calcFloatStep(float factor, float f1, float f2) {
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
}
