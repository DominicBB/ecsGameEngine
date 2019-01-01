package grabbers;

import components.Component;
import core.coreSystems.EntityGrabberSystem;

import java.util.List;

public class SingleEntityGrabber extends EntityGrabber {
    public SingleEntityGrabber(List<Class<? extends Component>> requiredComponents, EntityGrabberSystem entityGrabberSystem) {
        super(requiredComponents, entityGrabberSystem);
        //SystemCommunicator.onEntityGrabberCreate(this);
    }

    @Override
    public void onEntityGrabbed(int[] componentIndexs, int entityID) {

    }
}