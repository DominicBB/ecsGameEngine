package physics.physicsUtil;
import util.mathf.Mathf2D.Vec2f;

public class Physics2D {
    public static final Vec2f DEFUALT_GRAVITY = new Vec2f(0f, -9.8f);
    public static Vec2f globalGravity = new Vec2f(0f, -9.8f);

    public static boolean raycast2D(Vec2f origin, Vec2f direction, float maxDistance) {
        return false;
    }

    public static boolean raycast2D(Vec2f origin, Vec2f direction) {
        return false;
    }
}
