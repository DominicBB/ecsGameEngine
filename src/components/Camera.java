package components;

import util.geometry.Vector3D;

public class Camera extends Component {
    public Vector3D position;
    public Vector3D lookDir;
    public Vector3D upDir;
    public Vector3D rightDir;
    public Vector3D target;

    public float fFar;
    public float fNear;
    public float fNorm;
    public float fFov;
    public float fYaw;
    private double fov;

    public Camera() {
        setup();
    }

    private void setup() {
        lookDir = new Vector3D(0f, 0f, 1f);
        position = new Vector3D(0f, 0f, 0f);
        upDir = new Vector3D(0, 1, 0);
        rightDir = new Vector3D(1, 0, 0);

        fFar = 1000f;
        fNear = 0.1f;
        fNorm = fFar / (fFar - fNear);
        fov = Math.toRadians(-140);
        fFov = -1f;/*(float) (1f / (Math.tan(fov / 2)));*/
        fYaw = 0.0f;
    }
}
