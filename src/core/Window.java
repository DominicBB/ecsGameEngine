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

/**
 * The display of the engine
 */
public class Window extends JFrame {
    private Canvas drawing;
    private int fps;
    public static final int defaultWidth = 800;
    public static final int defaultHeight = 800;

    private BufferedImage displayImage;
    private byte[] displayImageContents;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    private Dimension frameDim = new Dimension(defaultWidth, defaultHeight);

    public Window() {
        createWindow();
    }

    public static float getAspectRatio() {
        return (float) defaultHeight / (float) defaultWidth;
    }

    private void createWindow() {
        this.setMinimumSize(new Dimension(defaultWidth, defaultHeight));
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.toFront();
        this.setFocusable(true);
        this.setFocusableWindowState(true);
        this.setLocation(1000, 0);
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

        displayImage = new BufferedImage(defaultWidth, defaultHeight, BufferedImage.TYPE_3BYTE_BGR);
        displayImageContents = ((DataBufferByte) displayImage.getRaster().getDataBuffer()).getData();
    }

    public void setFPS(int frames) {
        fps = frames;
    }

    public void update(Bitmap colorBuffer) {
        this.requestFocus();
        colorBuffer.copyTo3BGR(displayImageContents);
        graphics.drawImage(displayImage, 0, 0, null);
        bufferStrategy.show();
    }

    public void setFrameDimention(Dimension frameDimention) {
        this.setMinimumSize(frameDimention);
        this.drawing.setMinimumSize(frameDimention);
    }
}
