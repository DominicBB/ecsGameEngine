package core.coreSystems;

import components.Component;

import java.util.List;

/**
 * All core.coreSystems that act on Components should extend this class
 */
public abstract class GameSystem extends EntityGrabberSystem {
    public GameSystem(List<Class<? extends Component>> requiredComponents) {
        super(requiredComponents);
        SystemCommunicator.registerGameSystem(this);
    }
}
