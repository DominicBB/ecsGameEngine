package Rendering.renderUtil;

import util.Mathf.Mathf3D.Matrix4x4;
import util.Mathf.Mathf3D.Vector3D;

public final class RenderState {
    public static LightingState lightingState;
    public static Vector3D cameraPos;

    public static Matrix4x4 MVP;
    public static Matrix4x4 projectionToWorld;


}
