package systems;

import components.Component;
import core.Entity;
import listners.EntityListner;
import util.Bag;

/**
 * Acts as a communicator between systems
 */
public class SystemCommunicator {
    private static ECSSystem ecsSystem;
    private static ComponentSystem componentSystem;
    private static EntitySystem entitySystem = EntitySystem.getInstance();

    static void setEcsSystem(ECSSystem ecsSystem) {
        SystemCommunicator.ecsSystem = ecsSystem;
    }

    static void registerGameSystem(GameSystem gameSystem) {
        SystemCommunicator.ecsSystem.registerGameSystem(gameSystem);
    }

    public static void registerEntityListner(EntityListner entityListner) {
        SystemCommunicator.componentSystem.registerEntityListner(entityListner);
    }

    public  static Bag<Component> getAllComponentsOnEntity(int id) {
        return SystemCommunicator.ecsSystem.entitySystem.getAllComponentsOnEntity(id);
    }

    public static Component getComponentOnEntity(int entityID, int componentIndex) {
        return SystemCommunicator.ecsSystem.entitySystem.getComponentOnEntity(entityID, componentIndex);
    }

    public static Component[] getComponentsOnEntity(int entityID, int[] componentsIndex) {
        return SystemCommunicator.ecsSystem.entitySystem.getComponentsOnEntity(entityID, componentsIndex);
    }

    public static void setComponentSystem(ComponentSystem componentSystem) {
        SystemCommunicator.componentSystem = componentSystem;
    }

    public static void onEntityCreate(Entity entity) {
        SystemCommunicator.componentSystem.onEntityCreate(entity.getId(), entity.getComponents());
    }

    public static void onComponentAddedToEntity(Entity entity) {
        SystemCommunicator.componentSystem.onComponentAddedToEntity(entity.getId(), entity.getComponents());
    }

    public static void onEntityListnerCreate(EntityListner entityListner) {
        SystemCommunicator.entitySystem.onEntityListnerCreate(entityListner);
    }
}
