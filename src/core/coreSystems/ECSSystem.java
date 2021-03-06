package core.coreSystems;

import Physics.physicsSystems.PhysicsSystem;
import Rendering.renderingSystems.RenderSystem;
import core.EntityFactory;
import core.Window;
import listners.EntityGrabber;

import java.util.ArrayList;
import java.util.List;

/**
 * Extend this class to start game
 */
public abstract class ECSSystem extends BaseSystem implements Runnable {
    protected EntitySystem entitySystem = EntitySystem.getInstance();
    protected ComponentSystem componentSystem = new ComponentSystem();

    private List<PhysicsSystem> physicsSystems = new ArrayList<>();
    private List<GameSystem> gameSystems = new ArrayList<>();
    private List<RenderSystem> renderSystems = new ArrayList<>();

    protected RenderSystem renderSystem;

    protected boolean isRunning;
    protected Thread thread;
    private core.Window window;


    private double updatesPerSecond = 60.0;
    private long frameTimer;
    private int framesCount;

    public ECSSystem() {
        SystemCommunicator.setEcsSystem(this);
        SystemCommunicator.setComponentSystem(componentSystem);
        this.window = new core.Window();
        EntityFactory.entitySystem = entitySystem;
        renderSystem = new RenderSystem();
    }

    @Override
    public void run() {
        Time.start();

        double nsPerUpdate = 1000000000.0 / updatesPerSecond;
        frameTimer = Time.getStartTime();
        framesCount = 0;
        boolean render;

        while (isRunning) {
            render = false;
//            Time.updateTimes();
            Time.updateNumUpdatesToDo(nsPerUpdate);
            while (Time.numUpdatesTodo >= 1) {
                Time.updateTimes();//TODO
//                System.out.println(Time.getDeltaTime()+" DELTA");
//                System.out.println(Time.numUpdatesTodo+" numUpdates");
                physicsUpdate();
                doUpdates();
                Time.numUpdatesTodo--;
                render = true;
            }

            if (render) {
                renderUpdate();
                framesCount++;
            }
            upkeepFps();

        }
    }

    private void physicsUpdate() {

    }

    private void doUpdates() {
        this.update();
        this.updateGameSystems();
    }

    private void renderUpdate() {
        renderSystem.update();
        window.update(renderSystem.getRenderer().colorBuffer);
    }

    private void upkeepFps() {
        if (Time.timeMili - frameTimer > 1000) {
            frameTimer += 1000;
            window.setFPS(framesCount);
            System.out.println(framesCount);
            framesCount = 0;
        }
    }

    private void updateGameSystems() {
        for (GameSystem gameSystem : gameSystems) {
            gameSystem.update();
        }
    }

    protected void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    protected void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Window getWindow() {
        return window;
    }

    protected void setTickRate(double tickRate) {
        updatesPerSecond = tickRate;
    }


    /**
     * Package only game registry
     *
     * @param gameSystem
     */
    void registerGameSystem(GameSystem gameSystem) {
        this.gameSystems.add(gameSystem);
    }

    void registerPhysicsSystem(PhysicsSystem physicsSystem) {
        this.physicsSystems.add(physicsSystem);
    }

    void registerRenderSystem(RenderSystem renderSystem) {
        this.renderSystems.add(renderSystem);

    }
}
