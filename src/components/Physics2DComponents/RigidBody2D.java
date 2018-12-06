package components.Physics2DComponents;

import components.PhysicsComponents.BaseRigidBody;
import util.Mathf.Mathf2D.Vector2D;

public class RigidBody2D extends BaseRigidBody {

    public Vector2D velocity;
    public Vector2D currentForce;
    public  Vector2D gravityForce = new Vector2D(0f,-9.8f);
    public Vector2D gravityDirection = Vector2D.newDown();

}
