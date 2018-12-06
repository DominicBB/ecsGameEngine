package components;

import core.Window;
import util.Mathf.Mathf3D.Vector3D;

public class Camera extends Component {
    public Vector3D position;
    public Vector3D lookDir;
    public Vector3D upDir;
    public Vector3D rightDir;

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
        lookDir = new Vector3D(0f, 0f, 1f);
        position = new Vector3D(0f, 0f, 0f);
        upDir = new Vector3D(0, 1, 0);
        rightDir = new Vector3D(1, 0, 0);

        zFar = 1000f;
        zNear = 0.1f;
        zRange = zFar - zNear;
        fov = Math.toRadians(-140);
        fFov = (float) Math.tan(fov / 2);
        fFov_AR = fFov / Window.getAspectRatio();
        fYaw = 0.0f;
    }
}
