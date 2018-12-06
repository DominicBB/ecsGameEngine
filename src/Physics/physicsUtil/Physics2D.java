package Physics.physicsUtil;
import util.Mathf.Mathf2D.Vector2D;

public class Physics2D {
    public static final Vector2D DEFUALT_GRAVITY = new Vector2D(0f, -9.8f);
    public static Vector2D globalGravity = new Vector2D(0f, -9.8f);

    public static boolean raycast2D(Vector2D origin, Vector2D direction, float maxDistance) {
        return false;
    }

    public static boolean raycast2D(Vector2D origin, Vector2D direction) {
        return false;
    }
}
