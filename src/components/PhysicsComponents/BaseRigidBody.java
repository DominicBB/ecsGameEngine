package components.PhysicsComponents;

import Physics.PhysicsMaterials.PhysicsMaterial;
import components.Component;

public abstract class BaseRigidBody extends Component {
    public boolean useGravity = true;
    public boolean useGlobalGravity;
    public float weight = 1f;

    public float drag;
    public float angularDrag;

    public PhysicsMaterial physicsMaterial;

}
