package rendering.renderUtil.interpolation;

import util.mathf.Mathf2D.Vec2f;
import util.mathf.Mathf3D.Vec4f;

public abstract class BaseLerperFactory {
    protected static void calcVec2Step(Vec2f out, float factor, Vec2f vIn1, Vec2f vIn2) {
        out.set(
                (vIn2.x - vIn1.x) * factor,
                (vIn2.y - vIn1.y) * factor
        );
    }

    static float calcFloatStep(float factor, float f1, float f2) {
        return (f2 - f1) * factor;
    }

    protected static void calcVec3Step(Vec4f out, float factor, Vec4f vIn1, Vec4f vIn2) {
        out.set(
                (vIn2.x - vIn1.x) * factor,
                (vIn2.y - vIn1.y) * factor,
                (vIn2.z - vIn1.z) * factor,
                (vIn2.w - vIn1.w) * factor
        );
    }
}
