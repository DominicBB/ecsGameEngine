package systems;

import components.Component;
import listners.EntityListner;
import listners.SingleEntityListner;

import java.util.List;

/**
 * All systems that act on Components should extend this class
 */
public abstract class GameSystem extends BaseSystem {
    public static final int ID = nextID();

    protected EntityListner entityListner;

    public GameSystem(List<Class<? extends Component>> requiredComponents, boolean addToUpdateList) {
        entityListner = new SingleEntityListner(requiredComponents, this);
        if(addToUpdateList){
            SystemCommunicator.registerGameSystem(this);
        }

    }

    protected Component getFirstComponentFromFirstEntity(EntityListner listner){
        int entityID = listner.getEntityIDsOfInterest().get(0);
        int componentIndex = listner.getComponentIndexsOfInterest().get(entityID)[0];
        return listner.getComponentOnEntity(entityID, componentIndex);
    }


}
