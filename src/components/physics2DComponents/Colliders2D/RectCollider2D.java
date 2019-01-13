package components.physics2DComponents.Colliders2D;

import util.mathf.Mathf2D.Bounds2D.AABoundingRect;
import util.mathf.Mathf2D.Vec2f;

public class RectCollider2D extends Collider2D{
    public AABoundingRect AABoundingRect;

   public RectCollider2D(AABoundingRect AABoundingRect){
       this.AABoundingRect = AABoundingRect;
   }

   public RectCollider2D(Vec2f center, Vec2f size){
       this.AABoundingRect = new AABoundingRect(center, size);
   }
}
