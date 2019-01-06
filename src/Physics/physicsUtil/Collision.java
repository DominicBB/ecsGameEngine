package Physics.physicsUtil;

import components.PhysicsComponents.Colliders.Collider;
import util.Mathf.Mathf3D.Bounds.Bounds;
import util.Mathf.Mathf3D.Vec4f;

public class Collision {
    public Collider collider;
    public float distance;
    public final Bounds b1;
    public final Bounds b2;
    public final Vec4f difference;

    public Collision(Bounds b1, Bounds b2, Vec4f difference) {
        this.b1 = b1;
        this.b2 = b2;
        this.difference = difference;
    }
}
