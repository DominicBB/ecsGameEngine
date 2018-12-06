package Physics.physicsSystems;

import components.Component;
import core.coreSystems.EntityListnerSystem;
import core.coreSystems.SystemCommunicator;

import java.util.List;

public abstract class PhysicsSystem extends EntityListnerSystem {

    public PhysicsSystem(List<Class<? extends Component>> requiredComponents) {
        super(requiredComponents);
        SystemCommunicator.registerPhysicsSystem(this);
    }
}
