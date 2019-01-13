package rendering.gizmos;

import rendering.drawers.draw.DrawCircle;
import util.mathf.Mathf2D.Vec2f;
import util.mathf.Mathf3D.Bounds.AABoundingBox;
import util.mathf.Mathf3D.Bounds.Bounds;
import util.mathf.Mathf3D.Vec4f;

public class Gizmos {
    private static final float centerRadius = 20f;

    public static void drawBoundingVolme(Bounds bounds, Vec4f color) {
        if (bounds instanceof AABoundingBox) {
            drawAABB((AABoundingBox) bounds, color);
        }
    }

    private static void drawAABB(AABoundingBox aaBB, Vec4f color) {
        Vec4f center = aaBB.getCenter();
//        RenderState.colorBuffer.setPixelPreClamp(mathf.fastCeil(center.x), mathf.fastCeil(center.y), color);
        DrawCircle.drawCircle(new Vec2f(center.x, center.y), centerRadius, color);
//        DrawCircle.fillCircle(new Vec2f(center.x, center.y), centerRadius, color);

       /* int yEnd = mathf.fastCeil(aaBB.getBottomRight().y);
        int xEnd = mathf.fastCeil(aaBB.getBottomRight().x);
        for (int y = mathf.fastCeil(aaBB.getTopLeft().y); y < yEnd; y++) {
            for (int x = mathf.fastCeil(aaBB.getTopLeft().x); x < xEnd; x++) {

            }
        }*/
    }
}
