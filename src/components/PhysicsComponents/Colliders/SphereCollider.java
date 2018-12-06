package components.PhysicsComponents.Colliders;

import util.Mathf.Mathf3D.Bounds.BoundingSphere;
import util.Mathf.Mathf3D.Vector3D;

public class SphereCollider {
    public BoundingSphere boundingSphere;

    public SphereCollider(BoundingSphere boundingSphere){
        this.boundingSphere = boundingSphere;
    }

    public SphereCollider(Vector3D center, float radius){
        this.boundingSphere = new BoundingSphere(center, radius);
    }
}
