package Rendering.gizmos;

import Rendering.drawers.draw.DrawCircle;
import Rendering.renderUtil.RenderState;
import util.Mathf.Mathf;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Bounds.AABoundingBox;
import util.Mathf.Mathf3D.Bounds.Bounds;
import util.Mathf.Mathf3D.Vector3D;

public class Gizmos {
    private static final float centerRadius = 20f;

    public static void drawBoundingVolme(Bounds bounds, Vector3D color) {
        if (bounds instanceof AABoundingBox) {
            drawAABB((AABoundingBox) bounds, color);
        }
    }

    private static void drawAABB(AABoundingBox aaBB, Vector3D color) {
        Vector3D center = aaBB.getCenter();
//        RenderState.colorBuffer.setPixelPreClamp(Mathf.fastCeil(center.x), Mathf.fastCeil(center.y), color);
        DrawCircle.drawCircle(new Vector2D(center.x, center.y), centerRadius, color);
//        DrawCircle.fillCircle(new Vector2D(center.x, center.y), centerRadius, color);

       /* int yEnd = Mathf.fastCeil(aaBB.getMaxExtents().y);
        int xEnd = Mathf.fastCeil(aaBB.getMaxExtents().x);
        for (int y = Mathf.fastCeil(aaBB.getMinExtents().y); y < yEnd; y++) {
            for (int x = Mathf.fastCeil(aaBB.getMinExtents().x); x < xEnd; x++) {

            }
        }*/
    }
}
