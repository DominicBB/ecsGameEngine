package core.coreSystems;

import components.Component;
import grabbers.EntityGrabber;
import util.Bag;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores each of the required Components for each GameSystem. On every entity create or new component add,
 * updates each GameSystems entity references if there is a match
 */
public class ComponentSystem implements Updateable {
    private List<EntityGrabber> entityGrabbers = new ArrayList<>();

    void onEntityCreate(int entityID, Bag<Component> components) {
        checkForListnerMatch(entityID, components);
    }
    void onComponentAddedToEntity(int entityID, Bag<Component> components) {
        checkForListnerMatch(entityID, components);
    }

    //TODO yikes, too many loops

    /**
     * Go through every EntityGrabber and add entityID's and componentIndexs that contain the required components
     * @param entityID
     * @param components
     */
    private void checkForListnerMatch(int entityID, Bag<Component> components) {
        int[] componentIndexs;//what the EntityGrabber gets
        int sizeOfReqs;
        int i;
        int cI;
        for (EntityGrabber entityGrabber : entityGrabbers) {
            sizeOfReqs = entityGrabber.getRequiredComponents().size();
            componentIndexs = new int[sizeOfReqs];
            i = 0;

            for (Class<? extends Component> requirement : entityGrabber.getRequiredComponents()) {
                if ((cI = findComponentIndexOfType(components, requirement)) != -1) {
                    componentIndexs[i++] = cI;
                }else{
                    break;
                }
                if (i == (sizeOfReqs)) {
                    entityGrabber.addComponentsOfInterest(componentIndexs,entityID);
                    break;
                }
            }
        }

    }

    //TODO also yikes
    private int findComponentIndexOfType(Bag<Component> components, Class<? extends Component> requirement) {
        int i = 0;
        for (Component component : components) {
            if (component.getClass().equals(requirement)) {
                return i;
            }
            i++;
        }
        return -1;
    }


    @Override
    public void update() {

    }

    public void registerEntityListner(EntityGrabber entityGrabber) {
        entityGrabbers.add(entityGrabber);
    }
}
