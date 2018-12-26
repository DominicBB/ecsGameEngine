package Physics.physicsUtil;

import util.Mathf.Mathf3D.Bounds.AABoundingBox;
import util.Mathf.Mathf3D.Bounds.BoundingSphere;
import util.Mathf.Mathf3D.Bounds.Bounds;
import util.Mathf.Mathf3D.Vector3D;

public class Intersect {
    private Intersect() {
    }


    public static Collision intersects(BoundingSphere bs, AABoundingBox aabb) {
        return (intersectsBool(bs,aabb)) ? createCollision(bs, aabb) : null;
    }

    public static boolean intersectsBool(BoundingSphere bs, AABoundingBox aabb) {
        if (aabb.contains(bs.getCenter()))
            return true;

        Vector3D centerDif = bs.getCenter().absDiff(aabb.getCenter());

        if (centerDif.x > (bs.getRadius() + aabb.getHalfSize().x) || centerDif.y > (bs.getRadius() + aabb.getHalfSize().y))
            return false;

        if (centerDif.x <= bs.getRadius() || centerDif.y <= bs.getRadius())
            return true;

        float maxExtendsSquare = (centerDif.x - aabb.getHalfSize().x) * (centerDif.x - aabb.getHalfSize().x) +
                (centerDif.y - aabb.getHalfSize().y) * (centerDif.y - aabb.getHalfSize().y);

        return maxExtendsSquare <= (bs.getRadius() * bs.getRadius());
    }


    public static Collision intersects(AABoundingBox aabb1, AABoundingBox aabb2) {
        return (intersectsBool(aabb1,aabb2)) ? createCollision(aabb1, aabb2) : null;
    }

    public static boolean intersectsBool(AABoundingBox aabb1, AABoundingBox aabb2) {
        Vector3D dif1 = aabb2.getMinExtents().minus(aabb1.getMaxExtents());
        Vector3D dif2 = aabb1.getMinExtents().minus(aabb2.getMaxExtents());
        Vector3D maxDif = dif1.maxValues(dif2);
        float maxVal = maxDif.maxVal();
        return maxVal <= 0;
    }


    public static Collision intersects(BoundingSphere bs1, BoundingSphere bs2) {
        return (intersectsBool(bs1,bs2)) ? createCollision(bs1, bs2) : null;
    }

    public static boolean intersectsBool(BoundingSphere bs1, BoundingSphere bs2) {
        float maxCollisionDist = Math.abs(bs1.getRadius() - bs2.getRadius());
        float centerDist = bs1.getCenter().minus(bs2.getCenter()).magnitude();
        return centerDist <= maxCollisionDist;
    }

    private static Collision createCollision(Bounds b1, Bounds b2) {
        return new Collision(b1,b2, b2.getCenter().minus(b1.getCenter()));
    }
}
