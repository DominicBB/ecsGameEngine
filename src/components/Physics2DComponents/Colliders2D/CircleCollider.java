package components.Physics2DComponents.Colliders2D;

import util.Mathf.Mathf2D.Bounds2D.BoundingCircle;
import util.Mathf.Mathf2D.Vec2f;

public class CircleCollider extends Collider2D{
    public BoundingCircle boundingCircle;

    public CircleCollider(BoundingCircle boundingCircle){
        this.boundingCircle = boundingCircle;
    }

    public CircleCollider(Vec2f center, float radius){
        this.boundingCircle = new BoundingCircle(center, radius);
    }
}
