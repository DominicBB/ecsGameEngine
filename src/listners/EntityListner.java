package listners;

import components.Component;
import systems.GameSystem;
import systems.SystemCommunicator;
import util.Bag;
import util.IntBag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class EntityListner {
    protected final Collection<Class<? extends Component>> requiredComponents;

    protected GameSystem attachedTo;
    protected IntBag componentIndexsOfInterest;
    protected List<Integer> entityIDsOfInterest = new ArrayList<>();


    public EntityListner(List<Class<? extends Component>> requiredComponents, GameSystem gameSystem) {
        this.requiredComponents = requiredComponents;
        componentIndexsOfInterest = new IntBag(requiredComponents.size(), 10);
        attachedTo = gameSystem;
        SystemCommunicator.registerEntityListner(this);
        SystemCommunicator.onEntityListnerCreate(this);
    }

    public Collection<Class<? extends Component>> getRequiredComponents() {
        return requiredComponents;
    }

    public void addComponentsOfInterest(int[] componentIndexs, int entityID) {
        componentIndexsOfInterest.set(componentIndexs, entityID);
        entityIDsOfInterest.add(entityID);
    }

    public Bag<Component> getAllComponentsOnEntity(int entityID){
        return SystemCommunicator.getAllComponentsOnEntity(entityID);
    }

    public Component getComponentOnEntity(int entityID, int componentIndex){
        return SystemCommunicator.getComponentOnEntity(entityID, componentIndex);
    }

    public Component[] getComponentsOnEntity(int entityID, int[] componentIndex){
        return SystemCommunicator.getComponentsOnEntity(entityID, componentIndex);
    }

    public void removeEntityOfInterest(int id) {
        componentIndexsOfInterest.remove(id);
        //TODO remove entityIDsOfInterest
    }

    public GameSystem getAttachedTo() {
        return attachedTo;
    }

    public List<Integer> getEntityIDsOfInterest() {
        return entityIDsOfInterest;
    }

    public IntBag getComponentIndexsOfInterest() {
        return componentIndexsOfInterest;
    }
}
