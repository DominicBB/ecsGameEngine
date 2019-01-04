package components;

import core.display.Window;
import util.Mathf.Mathf3D.Transform;

public class Camera extends Component {
    public Transform transform;

    public float zFar;
    public float zNear;
    public float zRange;
    public float fFov;
    public float fFov_AR;
    private double fov;

    public Camera() {
        setup();
    }

    private void setup() {
        transform = new Transform();
        zFar = 1000f;
        zNear = 1f;
        zRange = zFar - zNear;
        fov = Math.toRadians(50f);
        fFov = 1f / (float) Math.tan(fov / 2);
        fFov_AR = fFov / Window.getAspectRatio();
    }
}
