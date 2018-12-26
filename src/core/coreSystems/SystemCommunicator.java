package core.coreSystems;

import Physics.physicsSystems.PhysicsSystem;
import Rendering.renderingSystems.RenderSystem;
import components.Component;
import core.Entity;
import grabbers.EntityGrabber;
import util.Bag;

/**
 * Acts as a communicator between core.coreSystems
 */
public class SystemCommunicator {
    private static ECSSystem ecsSystem;
    private static ComponentSystem componentSystem;
    private static EntitySystem entitySystem = EntitySystem.getInstance();

    static void setEcsSystem(ECSSystem ecsSystem) {
        SystemCommunicator.ecsSystem = ecsSystem;
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

    public static void onEntityListnerCreate(EntityGrabber entityGrabber) {
        SystemCommunicator.entitySystem.onEntityGrabberCreate(entityGrabber);
    }


    static void registerGameSystem(GameSystem gameSystem) {
        SystemCommunicator.ecsSystem.registerGameSystem(gameSystem);
    }

    public static void registerEntityListner(EntityGrabber entityGrabber) {
        SystemCommunicator.componentSystem.registerEntityListner(entityGrabber);
    }
    public static void registerPhysicsSystem(PhysicsSystem physicsSystem) {
        SystemCommunicator.ecsSystem.registerPhysicsSystem(physicsSystem);
    }

    public static void registerRenderSystem(RenderSystem renderSystem) {
        SystemCommunicator.ecsSystem.registerRenderSystem(renderSystem);
    }
}
