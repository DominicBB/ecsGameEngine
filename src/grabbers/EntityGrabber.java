package grabbers;

import components.Component;
import core.coreSystems.EntityGrabberSystem;
import core.coreSystems.SystemCommunicator;
import util.Bag;
import util.IntBag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class EntityGrabber {
    protected final Collection<Class<? extends Component>> requiredComponents;

    protected EntityGrabberSystem attachedTo;
    protected IntBag componentIndexsOfInterest;
    protected List<Integer> entityIDsOfInterest = new ArrayList<>();


    public EntityGrabber(List<Class<? extends Component>> requiredComponents, EntityGrabberSystem entityGrabberSystem) {
        this.requiredComponents = requiredComponents;
        componentIndexsOfInterest = new IntBag(requiredComponents.size(), 10);
        attachedTo = entityGrabberSystem;
        SystemCommunicator.registerEntityListner(this);
        SystemCommunicator.onEntityListnerCreate(this);
    }

    public Collection<Class<? extends Component>> getRequiredComponents() {
        return requiredComponents;
    }

    public void addComponentsOfInterest(int[] componentIndexs, int entityID) {
        componentIndexsOfInterest.set(componentIndexs, entityID);
        entityIDsOfInterest.add(entityID);
        onEntityGrabbed(componentIndexs, entityID);
    }

    public abstract void onEntityGrabbed(int[] componentIndexs, int entityID);

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

    public Component[] getRelevantComponents(int entityID){
        return getComponentsOnEntity(entityID,
                getComponentIndexsOfInterest().get(entityID));
    }

    public EntityGrabberSystem getAttachedTo() {
        return attachedTo;
    }

    public List<Integer> getEntityIDsOfInterest() {
        return entityIDsOfInterest;
    }

    public IntBag getComponentIndexsOfInterest() {
        return componentIndexsOfInterest;
    }
}
