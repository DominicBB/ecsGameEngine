package listners;

import components.Component;
import systems.GameSystem;
import systems.SystemCommunicator;

import java.util.List;

public class SingleEntityListner extends EntityListner {
    public SingleEntityListner(List<Class<? extends Component>> requiredComponents, GameSystem gameSystem) {
        super(requiredComponents, gameSystem);
        SystemCommunicator.onEntityListnerCreate(this);
    }
}
