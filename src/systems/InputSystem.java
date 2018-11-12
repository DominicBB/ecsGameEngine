package systems;

import components.Component;
import components.InputComponent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.event.MouseInputListener;

/**
 * Collects all input from the Window
 */
public class InputSystem extends GameSystem implements KeyListener, MouseInputListener {

    private boolean[] keysPressed;
    private boolean[] mouseButtonsPressed;

    public InputSystem(boolean addToUpdateList) {
        super(Arrays.asList(InputComponent.class), addToUpdateList);
        keysPressed = new boolean[300];
        mouseButtonsPressed = new boolean[10];
    }

    // mouse
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    // keyboard
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed[e.getKeyCode()] = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed[e.getKeyCode()] = false;
    }

    public boolean[] getKeysPressed() {
        return keysPressed;
    }

    public boolean[] getMouseButtonsPressed() {
        return mouseButtonsPressed;
    }

    @Override
    public void update(float deltaTime) {
        for (int entityID : entityListner.getEntityIDsOfInterest()) {
            int[] componentIndexs = entityListner.getComponentIndexsOfInterest().get(entityID);

            InputComponent inputComponent = (InputComponent) entityListner.getComponentOnEntity(entityID, componentIndexs[0]);
            inputComponent.keysPressed = keysPressed;
            inputComponent.mouseButtonPressed = mouseButtonsPressed;
        }

    }

}
