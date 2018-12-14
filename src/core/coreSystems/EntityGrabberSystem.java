package core.coreSystems;

import components.Component;
import listners.EntityGrabber;
import listners.SingleEntityGrabber;

import java.util.List;

public abstract class EntityGrabberSystem extends BaseSystem {

    protected EntityGrabber entityGrabber;

    public EntityGrabberSystem(List<Class<? extends Component>> requiredComponents) {
        entityGrabber = new SingleEntityGrabber(requiredComponents, this);
    }

    protected Component getFirstComponentFromFirstEntity(EntityGrabber grabber){
        int entityID = grabber.getEntityIDsOfInterest().get(0);
        int componentIndex = grabber.getComponentIndexsOfInterest().get(entityID)[0];
        return grabber.getComponentOnEntity(entityID, componentIndex);
    }

}
