package components.PhysicsComponents.Colliders;

import util.Mathf.Mathf3D.Bounds.BoundingSphere;
import util.Mathf.Mathf3D.Vec4f;

public class SphereCollider {
    public BoundingSphere boundingSphere;

    public SphereCollider(BoundingSphere boundingSphere){
        this.boundingSphere = boundingSphere;
    }

    public SphereCollider(Vec4f center, float radius){
        this.boundingSphere = new BoundingSphere(center, radius);
    }
}
