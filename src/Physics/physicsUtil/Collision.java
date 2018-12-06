package Physics.physicsUtil;

import components.PhysicsComponents.Colliders.Collider;
import util.Mathf.Mathf3D.Bounds.Bounds;
import util.Mathf.Mathf3D.Vector3D;

public class Collision {
    public Collider collider;
    public float distance;
    public final Bounds b1;
    public final Bounds b2;
    public final Vector3D difference;

    public Collision(Bounds b1, Bounds b2, Vector3D difference) {
        this.b1 = b1;
        this.b2 = b2;
        this.difference = difference;
    }
}
