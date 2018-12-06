package listners;

import components.Component;
import core.coreSystems.EntityListnerSystem;
import core.coreSystems.GameSystem;
import core.coreSystems.SystemCommunicator;

import java.util.List;

public class SingleEntityListner extends EntityListner {
    public SingleEntityListner(List<Class<? extends Component>> requiredComponents, EntityListnerSystem entityListnerSystem) {
        super(requiredComponents, entityListnerSystem);
        SystemCommunicator.onEntityListnerCreate(this);
    }
}
