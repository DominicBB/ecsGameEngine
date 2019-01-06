package components.Physics2DComponents;

import components.PhysicsComponents.BaseRigidBody;
import util.Mathf.Mathf2D.Vec2f;

public class RigidBody2D extends BaseRigidBody {

    public Vec2f velocity;
    public Vec2f currentForce;
    public Vec2f gravityForce = new Vec2f(0f,-9.8f);
    public Vec2f gravityDirection = Vec2f.newDown();

}
