package grabbers;

import components.Component;
import core.coreSystems.GameSystem;

import java.util.List;

public class MultiEntityGrabber extends EntityGrabber {
    public MultiEntityGrabber(List<Class<? extends Component>> requiredComponents, GameSystem gameSystem) {
        super(requiredComponents, gameSystem);
    }

    @Override
    public void onEntityGrabbed(int[] componentIndexs, int entityID) {

    }
}
