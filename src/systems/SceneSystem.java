package systems;

import components.Camera;
import components.Component;
import components.Scene;
import listners.EntityListner;
import listners.SingleEntityListner;

import java.util.Arrays;
import java.util.List;

public class SceneSystem extends GameSystem{

    EntityListner CameraListner = new SingleEntityListner(Arrays.asList(Camera.class), this);

    public SceneSystem(boolean addToUpdateList) {
        super(Arrays.asList(Scene.class), addToUpdateList);
    }

    @Override
    public void update(float deltaTime) {

    }
}
