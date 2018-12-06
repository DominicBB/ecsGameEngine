package components.PhysicsComponents;

import util.Mathf.Mathf3D.Vector3D;

public class RigidBody extends BaseRigidBody {

    public Vector3D velocity;
    public Vector3D acceleration;
    public Vector3D currentForce;
    public Vector3D gravityForce = new Vector3D(0f,0f,-9.8f);
    public Vector3D gravityDirection = Vector3D.newDown();


}
