package rendering.gizmos;

import rendering.drawers.draw.DrawCircle;
import util.Mathf.Mathf2D.Vec2f;
import util.Mathf.Mathf3D.Bounds.AABoundingBox;
import util.Mathf.Mathf3D.Bounds.Bounds;
import util.Mathf.Mathf3D.Vec4f;

public class Gizmos {
    private static final float centerRadius = 20f;

    public static void drawBoundingVolme(Bounds bounds, Vec4f color) {
        if (bounds instanceof AABoundingBox) {
            drawAABB((AABoundingBox) bounds, color);
        }
    }

    private static void drawAABB(AABoundingBox aaBB, Vec4f color) {
        Vec4f center = aaBB.getCenter();
//        RenderState.colorBuffer.setPixelPreClamp(Mathf.fastCeil(center.x), Mathf.fastCeil(center.y), color);
        DrawCircle.drawCircle(new Vec2f(center.x, center.y), centerRadius, color);
//        DrawCircle.fillCircle(new Vec2f(center.x, center.y), centerRadius, color);

       /* int yEnd = Mathf.fastCeil(aaBB.getBottomRight().y);
        int xEnd = Mathf.fastCeil(aaBB.getBottomRight().x);
        for (int y = Mathf.fastCeil(aaBB.getTopLeft().y); y < yEnd; y++) {
            for (int x = Mathf.fastCeil(aaBB.getTopLeft().x); x < xEnd; x++) {

            }
        }*/
    }
}
