package core;

import Rendering.renderUtil.Bitmaps.BitmapABGR;
import Rendering.renderUtil.Bitmaps.BitmapBGR;
import core.coreSystems.InputSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * The display of the engine
 */
public class Window extends JFrame {
    private final Canvas drawing;
    private int fps;
    public static final int defaultWidth = 1920;
    public static final int defaultHeight = 1080;

    //    private VolatileImage vdisplayImage;
    private final BufferedImage displaying;
    public static byte[] buff1;

    private final BufferStrategy bufferStrategy;
    private final Graphics graphics;

    private final Dimension frameDim = new Dimension(defaultWidth, defaultHeight);

    public Window() {
        drawing = new Canvas();
        drawing.setMinimumSize(frameDim);
        drawing.setVisible(true);

        createWindow();

        drawing.createBufferStrategy(1);
        bufferStrategy = drawing.getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();

        //create buffers
        displaying = new BufferedImage(defaultWidth, defaultHeight, BufferedImage.TYPE_3BYTE_BGR);
        buff1 = ((DataBufferByte) displaying.getRaster().getDataBuffer()).getData();

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

    public void update(BitmapABGR colorBuffer) {
        graphics.drawImage(displaying, 0, 0, null);
        bufferStrategy.show();
    }

    public void setFrameDimention(Dimension frameDimention) {
        this.setMinimumSize(frameDimention);
        this.drawing.setMinimumSize(frameDimention);
    }

}
