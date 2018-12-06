package listners;

import components.Component;
import core.coreSystems.GameSystem;

import java.util.List;

public class MultiEntityListner extends EntityListner {
    public MultiEntityListner(List<Class<? extends Component>> requiredComponents, GameSystem gameSystem) {
        super(requiredComponents, gameSystem);
    }
}
