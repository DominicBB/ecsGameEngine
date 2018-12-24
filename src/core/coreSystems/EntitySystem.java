package core.coreSystems;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import components.Component;
import core.Entity;
import listners.EntityGrabber;
import util.Bag;
/**
 * Managers all the entities in the game
 */
public class EntitySystem implements Updateable{
    private static EntitySystem instance = new EntitySystem();
    private int IDCount = 0;

    private Queue<Integer> freeIndexs = new ArrayDeque<>();
    private Bag<Entity> entities = new Bag<>(Entity.class);


    /**
     * to enforce singleton
     */
    private EntitySystem() { }

    public static EntitySystem getInstance() {
        return instance;
    }

    /**
     * Reuses or creates a new entity with no components.
     * @return
     */
    public Entity createEntity() {
        Entity entity = obtain();
        SystemCommunicator.onEntityCreate(entity);
        return entity;
    }

    /**
     * Reuses or creates a new entity with the specified components
     * @param components
     * @return
     */
    public Entity createEntity(List<Component> components) {
        Entity entity = obtain();
        entity.addComponents(components);
        SystemCommunicator.onEntityCreate(entity);
        return entity;
    }


    //TODO: should remove itself from entityGrabbers
    /**
     * Frees this entity, removing all components. Allowing it to be reused
     * @param id
     */
    public void cleanEntity(int id) {
        freeIndexs.add(id);
        entities.get(id).getComponents().clear();
    }

    //TODO: should probably return a list
    /**
     * Gets all te components from a given entity
     * @param entityID
     * @return
     */
    public Bag<Component> getAllComponentsOnEntity(int entityID) {
        return entities.get(entityID).getComponents();
    }

    /**
     * Gets components that are at the given indices in the entities component bag.
     * @param entityID
     * @param componentIndexs
     * @return
     */
    public Component[] getComponentsOnEntity(int entityID, int[] componentIndexs) {
        Bag<Component> components = entities.get(entityID).getComponents();
        Component[] entityComponents = new Component[componentIndexs.length];
        int index = 0;
        for (int i : componentIndexs) {
            entityComponents[index++] = components.get(i);
        }
        return entityComponents;
    }


    /**
     * Gets the component that is at the given index in the entities component bag.
     * @param entityID
     * @param componentIndex
     * @return the component
     */
    public Component getComponentOnEntity(int entityID, int componentIndex) {
        return entities.get(entityID).getComponent(componentIndex);
    }


    /**
     * Removes the first component of specified type form the entity specified by the entityID
     * @param entityID
     * @param type
     */
    public void removeComponentOfType(int entityID, Class<? extends Component> type) {
        Bag<Component> components = entities.get(entityID).getComponents();
        int index = findComponentIndexOfType(components, type);
        if (index != -1) {
            components.remove(index);
            freeIndexs.add(index);
        }
    }

    /**
     * Gets the first component of specified type form the entity specified by the entityID
     * @param entityID
     * @param type
     * @return the component, null if it does not exist
     */
    public Component getComponentOfType(int entityID, Class<? extends Component> type) {
        Bag<Component> components = entities.get(entityID).getComponents();
        int index = findComponentIndexOfType(components, type);
        if (index != -1) {
            return components.unsafeGet(index);
        }
        return null;
    }

    //TODO: make safe, if entity does not exist
    /**
     * Adds a component to the entity specified by the ID. This is an unsafe method. if entity does not
     * exist it will cause error
     * @param entityID
     * @param component
     * @return
     */
    public int addComponent(int entityID, Component component) {
        Entity entity = entities.get(entityID);
        Bag<Component> components = entity.getComponents();
        component.attachToEntity(entity);

        if (entity.getFreeIndexs().isEmpty()) {
            components.add(component);

            SystemCommunicator.onComponentAddedToEntity(entity);
            return components.size();
        } else {
            int index = entity.getFreeIndexs().poll();
            components.set(component, index);

            SystemCommunicator.onComponentAddedToEntity(entity);
            return index;
        }

    }

    private int findComponentIndexOfType(Bag<Component> components, Class<? extends Component> type) {
        int i = 0;
        for (Component c : components) {
            if (type.isInstance(c)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Gets the next free index from the entity bag. If there are no free indices it
     * will create a new entity
     * @return the entity
     */
    private Entity obtain() {
        if (freeIndexs.size() == 0) {
            Entity e = new Entity(IDCount++);
            entities.add(e);
            return e;
        } else {
            int id = freeIndexs.poll();
            return entities.get(id);
        }
    }

    /**
     * This is called whenever an entityGrabber is created.
     * Adds the any entities that matches the new entityGrabber's component requirements to the
     * entityGrabber's entity bag.
     * @param entityGrabber
     */
    public void onEntityGrabberCreate(EntityGrabber entityGrabber) {
        int size = entityGrabber.getRequiredComponents().size();
        int[] componentIndexs;
        int i;
        int cI;
        for (Entity entity : entities) {
            i = 0;
            componentIndexs = new int[size];
            for (Class<? extends Component> c : entityGrabber.getRequiredComponents()) {
                if ((cI = findComponentIndexOfType(entity.getComponents(), c)) != -1) {
                    componentIndexs[i++] = cI;
                } else {
                    break;
                }
                if (i == (size)) {
                    entityGrabber.addComponentsOfInterest(componentIndexs, entity.getId());
                    break;
                }
            }
        }
    }


    @Override
    public void update() {

    }
}
