package Rendering.renderUtil.Lerpers;

import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public abstract class BaseLerperFactory {
    protected static void calcVec2Step(Vector2D out, float diviser, Vector2D vIn1, Vector2D vIn2) {
        out.set(
                (vIn2.x - vIn1.x) / diviser,
                (vIn2.y - vIn1.y) / diviser
        );
    }

    protected static float calcFloatStep(float diviser, float f1, float f2) {
        return (f2 - f1) / diviser;
    }

    protected static void calcVec3Step(Vector3D out, float diviser, Vector3D vIn1, Vector3D vIn2) {
        out.set(
                (vIn2.x - vIn1.x) / diviser,
                (vIn2.y - vIn1.y) / diviser,
                (vIn2.z - vIn1.z) / diviser,
                (vIn2.w - vIn1.w) / diviser
        );
    }
}
