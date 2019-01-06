package util.Mathf;

import util.Mathf.Mathf2D.Vec2f;
import util.Mathf.Mathf3D.Vec4f;
import util.Mathf.Mathf3D.Vec4fi;

public class Mathf {
    public static final float epsilon = 0.00000001f;
    public static final float PI = (float) Math.PI;

    public static float clamp(float min, float value, float max) {
        return (value > max) ? max : (value < min) ? min : value;
    }

    /*public static float clamp(float min, float value, float max) {
        return (value < max) ? (value > min) ? value : min : max;
    }*/

    public static Vec4f clamp(Vec4f minComponents, Vec4f value, Vec4f maxComponents) {
        return new Vec4f(
                clamp(minComponents.x, value.x, maxComponents.x),
                clamp(minComponents.y, value.y, maxComponents.y),
                clamp(minComponents.z, value.z, maxComponents.z),
                clamp(minComponents.w, value.w, maxComponents.w)

        );
    }

    public static void clampNonAlloc(Vec4f minComponents, Vec4f value, Vec4f maxComponents) {
        value.x = clamp(minComponents.x, value.x, maxComponents.x);
        value.y = clamp(minComponents.y, value.y, maxComponents.y);
        value.z = clamp(minComponents.z, value.z, maxComponents.z);
        value.w = clamp(minComponents.w, value.w, maxComponents.w);
    }

    public static void clampMinNonAlloc(Vec4f minComponents, Vec4f value) {
        value.x = (value.x >= minComponents.x) ? value.x : minComponents.x;
        value.y = (value.y >= minComponents.y) ? value.y : minComponents.y;
        value.z = (value.z >= minComponents.z) ? value.z : minComponents.z;
        value.w = (value.w >= minComponents.w) ? value.w : minComponents.w;
    }

    public static void clampMaxNonAlloc(Vec4f value, Vec4f maxComponents) {
        value.x = (value.x <= maxComponents.x) ? value.x : maxComponents.x;
        value.y = (value.y <= maxComponents.y) ? value.y : maxComponents.y;
        value.z = (value.z <= maxComponents.z) ? value.z : maxComponents.z;
        value.w = (value.w <= maxComponents.w) ? value.w : maxComponents.w;
    }

    public static void clampMaxNonAlloc(Vec4fi value, Vec4fi maxComponents) {
        value.x = (value.x <= maxComponents.x) ? value.x : maxComponents.x;
        value.y = (value.y <= maxComponents.y) ? value.y : maxComponents.y;
        value.z = (value.z <= maxComponents.z) ? value.z : maxComponents.z;
        value.w = (value.w <= maxComponents.w) ? value.w : maxComponents.w;
    }

    public static Vec2f clamp(Vec2f minComponents, Vec2f value, Vec2f maxComponents) {
        return new Vec2f(
                clamp(minComponents.x, value.x, maxComponents.x),
                clamp(minComponents.y, value.y, maxComponents.y)
        );
    }


    public static boolean approximately(float f1, float f2) {
        return (abs(f1 - f2) < epsilon);
    }

    public static float abs(float f) {
        return (f < 0.0f) ? -f : f;
    }

    public static float max(float f0, float f1) {
        return (f0 >= f1) ? f0 : f1;
    }

    public static float min(float f0, float f1) {
        return (f0 <= f1) ? f0 : f1;
    }

    public static void min(Vec4f min, Vec4f other) {
        min.x = min(other.x, min.x);
        min.y = min(other.y, min.y);
        min.z = min(other.z, min.z);
        min.w = min(other.w, min.w);
    }

    public static void max(Vec4f max, Vec4f other) {
        max.x = max(other.x, max.x);
        max.y = max(other.y, max.y);
        max.z = max(other.z, max.z);
        max.w = max(other.w, max.w);
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

    private static final int BIG_ENOUGH_INT = 16 * 1024;
    private static final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT;
    private static final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5;

    public static int fastFloor(float f) {
        return (int) (f + BIG_ENOUGH_FLOOR) - BIG_ENOUGH_INT;
    }

    public static int fastRound(float f) {
        return (int) (f + BIG_ENOUGH_ROUND) - BIG_ENOUGH_INT;
    }

    public static int fastCeil(float f) {
        return BIG_ENOUGH_INT - (int) (BIG_ENOUGH_FLOOR - f); // credit: roquen
    }
}
