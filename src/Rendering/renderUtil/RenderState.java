package Rendering.renderUtil;

import components.Camera;
import util.Mathf.Mathf3D.Matrix4x4;
import util.Mathf.Mathf3D.Transform;
import util.Mathf.Mathf3D.Vector3D;

public final class RenderState {
    public static LightingState lightingState;
    public static Camera camera;

    public static Transform transform;
    public static Matrix4x4 world;
    public static Matrix4x4 mvp;
    //    public static Matrix4x4 objmvp;
    public static Matrix4x4 projectionToWorld;

    public static Vector3D screenSpaceToWorldSpace(Vector3D vector3D) {

        Vector3D res = new Vector3D(
                vector3D.x * vector3D.w,
                vector3D.y * vector3D.w,
                vector3D.z * vector3D.w,
                vector3D.w
        );

        return projectionToWorld.multiply4x4(vector3D);
    }

    public static void createLightingState() {
        lightingState = new LightingState(1f, Vector3D.RIGHT,
                Vector3D.newOnes(), new Vector3D(.1f, .1f, .1f));
    }

    public static Vector3D worldSpaceToScreenSpace(Vector3D vector3D) {
        Vector3D proj = mvp.multiply4x4(vector3D);
        proj.x = proj.x / proj.w;
        proj.y = proj.y / proj.w;
        proj.z = proj.z / proj.w;
        return proj;
    }


}
