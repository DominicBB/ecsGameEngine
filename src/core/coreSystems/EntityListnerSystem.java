package core.coreSystems;

import components.Component;
import listners.EntityListner;
import listners.SingleEntityListner;

import java.util.List;

public abstract class EntityListnerSystem extends BaseSystem {

    protected EntityListner entityListner;

    public EntityListnerSystem(List<Class<? extends Component>> requiredComponents) {
        entityListner = new SingleEntityListner(requiredComponents, this);
    }

    protected Component getFirstComponentFromFirstEntity(EntityListner listner){
        int entityID = listner.getEntityIDsOfInterest().get(0);
        int componentIndex = listner.getComponentIndexsOfInterest().get(entityID)[0];
        return listner.getComponentOnEntity(entityID, componentIndex);
    }

}
