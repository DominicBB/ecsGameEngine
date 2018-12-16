package util.Mathf;

import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class Mathf {
    public static final float epsilon = 0.00000001f;
    public static final float PI = (float) Math.PI;

    public static float clamp(float min, float value, float max) {
        return (value > max) ? max : (value < min) ? min : value;
    }

    /*public static float clamp(float min, float value, float max) {
        return (value < max) ? (value > min) ? value : min : max;
    }*/

    public static Vector3D clamp(Vector3D minComponents, Vector3D value, Vector3D maxComponents) {
        return new Vector3D(
                clamp(minComponents.x, value.x, maxComponents.x),
                clamp(minComponents.y, value.y, maxComponents.y),
                clamp(minComponents.z, value.z, maxComponents.z),
                clamp(minComponents.w, value.w, maxComponents.w)

        );
    }

    public static void clampNonAlloc(Vector3D minComponents, Vector3D value, Vector3D maxComponents) {
        value.x = clamp(minComponents.x, value.x, maxComponents.x);
        value.y = clamp(minComponents.y, value.y, maxComponents.y);
        value.z = clamp(minComponents.z, value.z, maxComponents.z);
        value.w = clamp(minComponents.w, value.w, maxComponents.w);
    }

    public static Vector2D clamp(Vector2D minComponents, Vector2D value, Vector2D maxComponents) {
        return new Vector2D(
                clamp(minComponents.x, value.x, maxComponents.x),
                clamp(minComponents.y, value.y, maxComponents.y),
                clamp(minComponents.w, value.w, maxComponents.w)
        );
    }

    public static boolean approximately(float f1, float f2) {
        return (abs(f1 - f2) < epsilon);
    }

    public static float abs(float f) {
        return (f < 0f) ? -f : f;
    }

    public static float max(float f0, float f1) {
        return (f0 >= f1) ? f0 : f1;
    }

    public static float min(float f0, float f1) {
        return (f0 <= f1) ? f0 : f1;
    }

    public static float lerp(float from, float to, float lerpAmt) {
        return from + ((to - from) * lerpAmt);
    }

    public static float toRadians(float angleDeg) {
        return angleDeg / 180.0f * PI;
    }

    public static float unsafeMax(float f0, float f1) {
        return (f0 >= f1) ? f0 : f1;
    }

    private static final int    BIG_ENOUGH_INT   = 16 * 1024;
    private static final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT;
    private static final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5;

    public static int fastFloor(float x) {
        return (int) (x + BIG_ENOUGH_FLOOR) - BIG_ENOUGH_INT;
    }

    public static int fastRound(float x) {
        return (int) (x + BIG_ENOUGH_ROUND) - BIG_ENOUGH_INT;
    }

    public static int fastCeil(float x) {
        return BIG_ENOUGH_INT - (int)(BIG_ENOUGH_FLOOR-x); // credit: roquen
    }
}
