package components.Physics2DComponents.Colliders2D;

import util.Mathf.Mathf2D.Bounds2D.AABoundingRect;
import util.Mathf.Mathf2D.Vector2D;

public class RectCollider2D extends Collider2D{
    public AABoundingRect AABoundingRect;

   public RectCollider2D(AABoundingRect AABoundingRect){
       this.AABoundingRect = AABoundingRect;
   }

   public RectCollider2D(Vector2D center, Vector2D size){
       this.AABoundingRect = new AABoundingRect(center, size);
   }
}
