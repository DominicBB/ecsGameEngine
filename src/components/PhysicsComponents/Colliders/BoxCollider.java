package components.PhysicsComponents.Colliders;

import util.Mathf.Mathf3D.Bounds.AABoundingBox;


public class BoxCollider extends Collider {
    private AABoundingBox AABoundingBox;
    public BoxCollider(AABoundingBox AABoundingBox){
       this.AABoundingBox = AABoundingBox;
    }


}
