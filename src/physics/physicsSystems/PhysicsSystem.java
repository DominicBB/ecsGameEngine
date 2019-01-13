package physics.physicsSystems;

import components.Component;
import core.coreSystems.EntityGrabberSystem;
import core.coreSystems.SystemCommunicator;

import java.util.List;

public abstract class PhysicsSystem extends EntityGrabberSystem {

    public PhysicsSystem(List<Class<? extends Component>> requiredComponents) {
        super(requiredComponents);
        SystemCommunicator.registerPhysicsSystem(this);
    }
}
