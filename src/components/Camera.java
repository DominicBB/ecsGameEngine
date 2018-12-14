package components;

import core.Window;
import util.Mathf.Mathf3D.Transform;

public class Camera extends Component{
    public Transform transform;

    public float zFar;
    public float zNear;
    public float zRange;
    public float fFov;
    public float fYaw;
    public float fFov_AR;
    private double fov;

    public Camera() {
        setup();
    }

    private void setup() {
        transform = new Transform();
        zFar = 1000f;
        zNear = 0.1f;
        zRange = zFar - zNear;
        fov = Math.toRadians(140);
        fFov = (float) Math.tan(fov / 2);
        fFov_AR = fFov / Window.getAspectRatio();
        fYaw = 0.0f;
    }
}
