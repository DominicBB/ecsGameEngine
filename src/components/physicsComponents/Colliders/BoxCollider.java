package components.physicsComponents.Colliders;

import util.mathf.Mathf3D.Bounds.AABoundingBox;


public class BoxCollider extends Collider {
    private AABoundingBox AABoundingBox;
    public BoxCollider(AABoundingBox AABoundingBox){
       this.AABoundingBox = AABoundingBox;
    }


}
