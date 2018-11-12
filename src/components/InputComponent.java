package components;

import components.Component;

/**
 * Add this component to an entity and it will recieve all inputs regestered from InputSystem
 */
public class InputComponent extends Component {
    public boolean[] keysPressed;
    public boolean[] mouseButtonPressed;
}
