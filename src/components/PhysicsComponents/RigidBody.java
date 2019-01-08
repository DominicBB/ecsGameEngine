package components.PhysicsComponents;

import util.Mathf.Mathf3D.Vec4f;

public class RigidBody extends BaseRigidBody {

    public Vec4f velocity;
    public Vec4f acceleration;
    public Vec4f currentForce;
    public Vec4f gravityForce = new Vec4f(0f,0f,-9.8f);
    public Vec4f gravityDirection = Vec4f.newDown();


}
