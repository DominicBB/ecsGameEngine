package core;

import Rendering.renderUtil.Bitmaps.BitmapABGR;
import Rendering.renderUtil.Bitmaps.BitmapBGR;
import Rendering.renderUtil.RenderState;
import core.coreSystems.InputSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * The display of the engine
 */
public class Window extends JFrame implements Runnable {
    private final Canvas drawing;
    private int fps;
    public static final int defaultWidth = 1920;
    public static final int defaultHeight = 1080;

    //    private VolatileImage vdisplayImage;
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
        fps = frames;
    }

    public void update() {
        //requestFocus();
        graphics.drawImage(bufferManager.getFrontBuffer().bufferedImage, 0, 0, null);
        bufferStrategy.show();
    }

    public void setFrameDimention(Dimension frameDimention) {
        this.setMinimumSize(frameDimention);
        this.drawing.setMinimumSize(frameDimention);
    }

    public void signal() {
        rasterHasSignaled = true;
        bufferManager.swap();
        synchronized (this) {
            notify();
        }
        RenderState.colorBuffer = bufferManager.getBackBuffer().bitmapBGR;
    }

    private volatile boolean rasterHasSignaled;
    private boolean isRunning;

    @Override
    public void run() {
        requestFocus();
        while (isRunning) {
            try {
                synchronized (this) {
                    while (!rasterHasSignaled) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rasterHasSignaled = false;
            update();
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
