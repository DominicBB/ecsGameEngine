package grabbers;

import Rendering.renderUtil.RenderState;
import components.Camera;
import components.Component;
import core.coreSystems.EntityGrabberSystem;
import core.coreSystems.EntitySystem;

import java.util.List;

public class CameraGrabber extends EntityGrabber {
    public CameraGrabber(List<Class<? extends Component>> requiredComponents, EntityGrabberSystem entityGrabberSystem) {
        super(requiredComponents, entityGrabberSystem);
    }

    @Override
    public void onEntityGrabbed(int[] componentIndexs, int entityID) {
        RenderState.camera = (Camera) EntitySystem.getInstance().getComponentOfType(entityID, Camera.class);
    }
}
