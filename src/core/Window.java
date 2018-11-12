package core;

import systems.InputSystem;
import util.RenderContext;

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

    private void createWindow() {
        this.setMinimumSize(new Dimension(defaultWidth , defaultHeight ));
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
        InputSystem inputSystem = new InputSystem(true);
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

    public void update(RenderContext renderContext) {
        this.requestFocus();

        renderContext.copyTo3BGR(displayImageContents);
        graphics.drawImage(displayImage,0,0,null);
        bufferStrategy.show();
    }

    public void setFrameDimention(Dimension frameDimention) {
        this.setMinimumSize(frameDimention);
        this.drawing.setMinimumSize(frameDimention);
    }
}
