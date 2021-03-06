package Rendering.Clipping;

import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.VertexOut;
import components.Camera;
import core.Window;
import util.Mathf.Mathf3D.Bounds.AABoundingBox;
import util.Mathf.Mathf3D.Matrix4x4;
import util.Mathf.Mathf3D.Plane;
import util.Mathf.Mathf3D.Vector3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClippingSystem {

    final List<Plane> fustrumClipPlanes = createFustrumClipPlanes();
    public static boolean needsClipping;

    public static ClippingMode decideClippingMode(AABoundingBox aabb) {
        return ClippingMode.CLIPPING;/* needsClipping(aabb);*/
    }

    private static ClippingMode needsClipping(AABoundingBox aabb) {
        AABoundingBox projectedBox = new AABoundingBox(
                RenderState.mvp.multiply4x4(aabb.getCenter()),
                aabb.getSize()
        );

        int clipCount = 0;
        Vector3D minExtents = projectedBox.getMinExtents();
        Vector3D maxExtents = projectedBox.getMaxExtents();
        if (minExtents.z < RenderState.camera.zNear) {
            if (maxExtents.z <= RenderState.camera.zNear)
                return ClippingMode.ALLOUTSIDE;
            ++clipCount;
        }

        if (minExtents.x < 0f || maxExtents.x > Window.defaultWidth - 1.5) {
            if (maxExtents.x <= 0f || minExtents.x >= Window.defaultWidth - 1.5)
                return ClippingMode.ALLOUTSIDE;
            ++clipCount;
        }
        if (minExtents.y < 0f || maxExtents.y > Window.defaultHeight - 1.5) {
            if (maxExtents.y <= 0f || minExtents.y >= Window.defaultHeight - 1.5)
                return ClippingMode.ALLOUTSIDE;
            ++clipCount;
        }
//        drawBoundingBox(projectedBox);

        return (clipCount == 0) ? ClippingMode.ALLINSIDE : ClippingMode.CLIPPING;
    }

    public void clipTriangle(List<VertexOut> result, VertexOut v1, VertexOut v2, VertexOut v3) {
        /*if (!needsClipping)
            return new ArrayList<>(Arrays.asList(v1, v2, v3));*/
        TriCullSpaceClip.clipNonAlloc(result, v1, v2, v3);
    }

    private List<Plane> createFustrumClipPlanes() {
        return Arrays.asList(

                /*new Plane(new Vector3D(1.0f, 0, 0), new Vector3D(200f, 0f, 0f)),
                new Plane(new Vector3D(-1.0f, 0, 0), new Vector3D(Window.defaultWidth - 200.5f, 0f, 0f)),

                new Plane(new Vector3D(0f, 1f, 0), new Vector3D(200f, 0, 0f)),
                new Plane(new Vector3D(0f, -1f, 0), new Vector3D(0f, Window.defaultHeight - 200.5f, 0f)),

                new Plane(new Vector3D(0f, 0, 1), new Vector3D(0f, 0f,*//*camera.zNear*//*.1f))*/
                new Plane(new Vector3D(1.0f, 0, 0), new Vector3D(-1f, 0f, 0f)),
                new Plane(new Vector3D(-1.0f, 0, 0), new Vector3D(1f, 0f, 0f)),

                new Plane(new Vector3D(0f, 1f, 0), new Vector3D(-1, 0, 0f)),
                new Plane(new Vector3D(0f, -1f, 0), new Vector3D(0f, 1f, 0f)),

                new Plane(new Vector3D(0f, 0, 1), new Vector3D(0f, 0f,/*camera.zNear*/.1f))

        );
    }

}
