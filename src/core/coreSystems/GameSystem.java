package core.coreSystems;

import components.Component;
import listners.EntityListner;
import listners.SingleEntityListner;

import java.util.List;

/**
 * All core.coreSystems that act on Components should extend this class
 */
public abstract class GameSystem extends EntityListnerSystem {
    public GameSystem(List<Class<? extends Component>> requiredComponents) {
        super(requiredComponents);
        SystemCommunicator.registerGameSystem(this);
    }
}
