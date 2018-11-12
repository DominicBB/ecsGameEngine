package systems;

import core.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * Extend this class to start game
 */
public abstract class ECSSystem extends BaseSystem implements Runnable {
    protected EntitySystem entitySystem = EntitySystem.getInstance();
    protected ComponentSystem componentSystem = new ComponentSystem();

    protected RenderSystem renderSystem;

    protected boolean isRunning;
    protected Thread thread;
    private core.Window window;

    private List<GameSystem> gameSystems = new ArrayList<>();

    double updatesPerSecond;

    public ECSSystem() {
        SystemCommunicator.setEcsSystem(this);
        SystemCommunicator.setComponentSystem(componentSystem);
        this.window = new core.Window();
    }

    @Override
    public void run() {
        updatesPerSecond = 60.0;
        double ns = 1000000000.0 / updatesPerSecond;




        int frames = 0;
        long timeMili;

        long startTimeMili = System.currentTimeMillis();
        long frameTimer = startTimeMili;

        float deltaTime;
        long lastTime = System.nanoTime();
        long now;
        double delta = 0;

        boolean render;
        while (isRunning) {
            timeMili = System.currentTimeMillis();

            now = System.nanoTime();
            deltaTime = (float)((now - lastTime)/1000000000.0);
            delta +=  (now - lastTime)/ ns;
            lastTime = now;

            render = false;
            while (delta >= 1) {
                doUpdates(deltaTime);
                delta--;
                render = true;
            }

            if(true /*render*/){
                renderUpdate(deltaTime);
                frames++;

            }

            if (timeMili - frameTimer > 1000) {
                frameTimer += 1000;
                window.setFPS(frames);
                System.out.println(frames);
                frames = 0;
            }
        }
    }

    private void doUpdates(float deltaTime) {
        this.update(deltaTime);
        this.updateGameSystems(deltaTime);
    }

    private void renderUpdate(float deltaTime){
        renderSystem.update(deltaTime);
        window.update(renderSystem.getRenderContext());

    }

    private void updateGameSystems(float deltaTime) {
        for (GameSystem gameSystem : gameSystems) {
            gameSystem.update(deltaTime);
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

}
