package core;

import Rendering.renderUtil.Bitmaps.Bitmap;
import Rendering.renderUtil.Bitmaps.ColorBuffer;
import core.coreSystems.InputSystem;
import Rendering.renderUtil.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.VolatileImage;
import java.util.Arrays;

/**
 * The display of the engine
 */
public class Window extends JFrame {
    private Canvas drawing;
    private int fps;
    public static final int defaultWidth = 1920;
    public static final int defaultHeight = 1080;

    private VolatileImage vdisplayImage;
    private BufferedImage displayImage;
    public static byte[] displayImageContents;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    private Dimension frameDim = new Dimension(defaultWidth, defaultHeight);

    public Window() {
        createWindow();
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
        setUpDrawingArea();
        setupInputSystem();
        this.add(drawing);
        this.pack();

        drawing.createBufferStrategy(1);
        bufferStrategy = drawing.getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
    }

    private void setupInputSystem() {
        InputSystem inputSystem = new InputSystem();
        this.addKeyListener(inputSystem);
        this.addMouseListener(inputSystem);
    }

    private void requestFocusOnField() {
        this.requestFocus();
    }

    private void setUpDrawingArea() {

        drawing = new Canvas();
        drawing.setMinimumSize(frameDim);
        drawing.setVisible(true);

        /*vdisplayImage =createVolatileImage(defaultWidth,defaultHeight);
        getGraphicsConfiguration();*/
        displayImage = new BufferedImage(defaultWidth, defaultHeight, BufferedImage.TYPE_3BYTE_BGR);
        displayImageContents = ((DataBufferByte) displayImage.getRaster().getDataBuffer()).getData();
    }

    public void setFPS(int frames) {
        fps = frames;
    }

    public void update(Bitmap colorBuffer) {
        this.requestFocus();
//        colorBuffer.copyTo3BGR(displayImageContents);
//        colorBuffer.copyTo(displayImageContents);
        graphics.drawImage(displayImage, 0, 0, null);
        bufferStrategy.show();
    }

    public void setFrameDimention(Dimension frameDimention) {
        this.setMinimumSize(frameDimention);
        this.drawing.setMinimumSize(frameDimention);
    }
}
