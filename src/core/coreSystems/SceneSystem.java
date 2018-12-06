package core.coreSystems;

import components.Camera;
import components.Scene;
import listners.EntityListner;
import listners.SingleEntityListner;

import java.util.Arrays;

public class SceneSystem extends GameSystem{

    EntityListner CameraListner = new SingleEntityListner(Arrays.asList(Camera.class), this);

    public SceneSystem(boolean addToUpdateList) {
        super(Arrays.asList(Scene.class));
    }

    @Override
    public void update() {

    }
}
