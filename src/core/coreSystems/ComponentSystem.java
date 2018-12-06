package core.coreSystems;

import components.Component;
import listners.EntityListner;
import util.Bag;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores each of the required Components for each GameSystem. On every entity create or new component add,
 * updates each GameSystems entity references if there is a match
 */
public class ComponentSystem extends BaseSystem {
    private List<EntityListner> entityListners = new ArrayList<>();

    void onEntityCreate(int entityID, Bag<Component> components) {
        checkForListnerMatch(entityID, components);
    }
    void onComponentAddedToEntity(int entityID, Bag<Component> components) {
        checkForListnerMatch(entityID, components);
    }

    //TODO yikes, too many loops

    /**
     * Go through every EntityListner and add entityID's and componentIndexs that contain the required components
     * @param entityID
     * @param components
     */
    private void checkForListnerMatch(int entityID, Bag<Component> components) {
        int[] componentIndexs;//what the EntityListner gets
        int sizeOfReqs;
        int i;
        int cI;
        for (EntityListner entityListner : entityListners) {
            sizeOfReqs = entityListner.getRequiredComponents().size();
            componentIndexs = new int[sizeOfReqs];
            i = 0;

            for (Class<? extends Component> requirement : entityListner.getRequiredComponents()) {
                if ((cI = findComponentIndexOfType(components, requirement)) != -1) {
                    componentIndexs[i++] = cI;
                }else{
                    break;
                }
                if (i == (sizeOfReqs)) {
                    entityListner.addComponentsOfInterest(componentIndexs,entityID);
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

    public void registerEntityListner(EntityListner entityListner) {
        entityListners.add(entityListner);
    }
}
