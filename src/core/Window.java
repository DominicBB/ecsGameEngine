package core;

import Rendering.renderUtil.RenderState;
import core.coreSystems.InputSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * The display of the engine
 */
public class Window extends JFrame implements Runnable {
    private final Canvas drawing;
    private int fps;
    public static final int defaultWidth = 800;
    public static final int defaultHeight = 800;

    private final BufferManager bufferManager;
    private final BufferStrategy bufferStrategy;
    private final Graphics graphics;
    private final Thread thread;

    private final Dimension frameDim = new Dimension(defaultWidth, defaultHeight);

    public Window(Thread thread) {
        this.thread = new Thread(this);
        drawing = new Canvas();
        drawing.setMinimumSize(frameDim);
        drawing.setVisible(true);

        createWindow();

        drawing.createBufferStrategy(1);
        bufferStrategy = drawing.getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();

        //create buffers
        bufferManager = new BufferManager(defaultWidth, defaultHeight);
        RenderState.colorBuffer = bufferManager.getBackBuffer().bitmapBGR;
    }

    public static float getAspectRatio() {
        return (float) defaultWidth / (float) defaultHeight;
    }

    private void createWindow() {
        this.setMinimumSize(new Dimension(defaultWidth, defaultHeight));
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.toFront();
        this.setFocusable(true);
        this.setFocusableWindowState(true);
        this.setLocation(500, 0);
        setupInputSystem();
        this.add(drawing);
        this.pack();
    }

    private void setupInputSystem() {
        InputSystem inputSystem = new InputSystem();
        this.addKeyListener(inputSystem);
        this.addMouseListener(inputSystem);
    }

    public void setFPS(int frames) {
//        requestFocus();
        fps = frames;
    }

    public void update() {
        graphics.drawImage(bufferManager.getFrontBuffer().bufferedImage, 0, 0, null);
        bufferStrategy.show();
    }

    public void setFrameDimention(Dimension frameDimention) {
        this.setMinimumSize(frameDimention);
        this.drawing.setMinimumSize(frameDimention);
    }

    private long notyfingTime = 0l;
    private long notifyCount = 0l;

    public void signal() {
        rasterHasSignaled = true;
        bufferManager.swap();
        synchronized (this) {
            notifyAll();
        }
        RenderState.colorBuffer = bufferManager.getBackBuffer().bitmapBGR;
    }

    private boolean rasterHasSignaled;
    private boolean isRunning;

    private long waitingTime = 0L;
    private long waitCount = 0L;
    private long lastTime = 0L;

    @Override
    public void run() {
        long timeStamp = 0L;
        while (isRunning) {
            try {
                while (!rasterHasSignaled) {
                    timeStamp = System.currentTimeMillis();
                    ++waitCount;
                    synchronized (this) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitingTime += (System.currentTimeMillis() - timeStamp);
            avgWaitTimePerSec();
            rasterHasSignaled = false;
            update();
        }
    }

    private void avgWaitTimePerSec() {
        if ((System.currentTimeMillis() - lastTime) > 1000) {
            lastTime = System.currentTimeMillis();
            System.out.println("WAITING AVG: " + waitingTime / waitCount);
        }
    }

    public void start() {
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
