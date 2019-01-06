package Physics.physicsUtil;

import components.Physics2DComponents.Colliders2D.Collider2D;
import util.Mathf.Mathf2D.Bounds2D.Bounds2D;
import util.Mathf.Mathf2D.Vec2f;

public class Collision2D {
    private final Bounds2D b1;
    private final Bounds2D b2;
    private final Vec2f difference;
    public Collider2D collider;
    public float distance;


    public Collision2D(Bounds2D bounds2D1, Bounds2D bounds2D2, Vec2f difference){
        this.b1 = bounds2D1;
        this.b2 = bounds2D2;
        this.difference = difference;
    }

}
