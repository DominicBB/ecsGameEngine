package util.Mathf;

import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class Mathf {
    public static final float epsilon = 0.00000001f;

    public static float clamp(float min, float value, float max) {
        return (value > max) ? max : (value < min) ? min : value;
    }

    public static Vector3D clamp(Vector3D minComponents, Vector3D value, Vector3D maxComponents) {
        return new Vector3D(
                clamp(minComponents.x, value.x, maxComponents.x),
                clamp(minComponents.y, value.y, maxComponents.y),
                clamp(minComponents.z, value.z, maxComponents.z),
                clamp(minComponents.w, value.w, maxComponents.w)

        );
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
}
