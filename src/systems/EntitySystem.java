package systems;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import components.Component;
import core.Entity;
import listners.EntityListner;
import util.Bag;

/**
 * Managers all the entities in the game
 */
public class EntitySystem extends BaseSystem {
    private static EntitySystem instance = new EntitySystem();
    private int IDCount = 0;

    private Queue<Integer> freeIndexs = new ArrayDeque<>();
    private Bag<Entity> entities = new Bag<>(Entity.class);


    private EntitySystem() {

    }

    public static EntitySystem getInstance() {
        return instance;
    }

    public Entity createEntity() {
        Entity entity = obtain();
        SystemCommunicator.onEntityCreate(entity);
        return entity;
    }

    public Entity createEntity(List<Component> components) {
        Entity entity = obtain();
        entity.addComponents(components);
        SystemCommunicator.onEntityCreate(entity);
        return entity;
    }


    public void cleanEntity(int id) {
        freeIndexs.add(id);
        entities.get(id).getComponents().clear();
    }

    public Bag<Component> getAllComponentsOnEntity(int entityID) {
        return entities.get(entityID).getComponents();
    }

    public Component[] getComponentsOnEntity(int entityID, int[] componentIndexs) {
        Bag<Component> components = entities.get(entityID).getComponents();
        Component[] entityComponents = new Component[componentIndexs.length];
        int index = 0;
        for (int i : componentIndexs) {
            entityComponents[index++] = components.get(i);
        }
        return entityComponents;
    }

    public Component getComponentOnEntity(int entityID, int componentIndex) {
        return entities.get(entityID).getComponent(componentIndex);
    }

    public void removeComponentOfType(int entityID, Class<? extends Component> type) {
        Bag<Component> components = entities.get(entityID).getComponents();
        int index = findComponentOfType(components, type);
        if(index != -1){
            components.remove(index);
            freeIndexs.add(index);
        }
    }

    public Component getComponentOfType(int entityID, Class<? extends Component> type) {
        Bag<Component> components = entities.get(entityID).getComponents();
        int index = findComponentOfType(components, type);
        if(index !=-1){
            return components.unsafeGet(index);
        }
        return null;
    }

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

    private int findComponentOfType(Bag<Component> components, Class<? extends Component> type) {
        int i =0;
        for (Component c : components) {
            if (type.isInstance(c)) {
                return i;
            }
            i++;
        }
        return -1;
    }

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

    public void onEntityListnerCreate(EntityListner entityListner){
        int size = entityListner.getRequiredComponents().size();
        int[] componentIndexs;
        int i;
        int cI;
        for(Entity entity: entities){
            i = 0;
            componentIndexs = new int[size];
            for(Class<? extends Component> c: entityListner.getRequiredComponents()){
                if((cI = findComponentOfType(entity.getComponents(), c) )!=-1){
                    componentIndexs[i++] = cI;
                }else{
                    break;
                }
                if(i == (size)){
                    entityListner.addComponentsOfInterest(componentIndexs, entity.getId());
                    break;
                }


            }

        }
    }




    @Override
    public void update(float deltaTime) {

    }
}
