package Physics.physicsUtil;

import util.Mathf.Mathf2D.Bounds2D.AABoundingRect;
import util.Mathf.Mathf2D.Bounds2D.BoundingCircle;
import util.Mathf.Mathf2D.Bounds2D.Bounds2D;
import util.Mathf.Mathf2D.Vector2D;

public class Intersect2D {

    private Intersect2D() {
    }

    public static Collision2D intersects(BoundingCircle bc1, BoundingCircle bc2) {
        return (intersectsBool(bc1,bc2)) ? createCollision(bc1, bc2) : null;
    }

    public static boolean intersectsBool(BoundingCircle bc1, BoundingCircle bc2) {
        float maxCollisionDist = bc2.getRadius() + bc1.getRadius();
        float centerDist = bc2.getCenter().minus(bc1.getCenter()).magnitude();

        return (centerDist <= maxCollisionDist) ? true : false;
    }



    public static Collision2D intersects(BoundingCircle bc, AABoundingRect aabr) {
        return (intersectsBool(bc,aabr)) ? createCollision(bc, aabr) : null;
    }

    public static boolean intersectsBool(BoundingCircle bc, AABoundingRect aabr) {
        if (aabr.contains(bc.getCenter()))
            return true;

        Vector2D centerDif = bc.getCenter().absDiff(aabr.getCenter());

        if (centerDif.x > (bc.getRadius() + aabr.getHalfSize().x) || centerDif.y > (bc.getRadius() + aabr.getHalfSize().y))
            return false;

        if (centerDif.x <= bc.getRadius() || centerDif.y <= bc.getRadius())
            return true;

        float maxExtendsSquare = (centerDif.x - aabr.getHalfSize().x) * (centerDif.x - aabr.getHalfSize().x) +
                (centerDif.y - aabr.getHalfSize().y) * (centerDif.y - aabr.getHalfSize().y);

        return (maxExtendsSquare <= (bc.getRadius() * bc.getRadius())) ? true : false;

    }



    public static Collision2D intersects(AABoundingRect aabr1, AABoundingRect aabr2) {
        return (intersectsBool(aabr1,aabr2)) ? createCollision(aabr1, aabr2) : null;
    }

    public static boolean intersectsBool(AABoundingRect aabr1, AABoundingRect aabr2) {
        Vector2D dif1 = aabr2.getMinExtents().minus(aabr1.getMaxExtents());
        Vector2D dif2 = aabr1.getMinExtents().minus(aabr2.getMaxExtents());
        Vector2D maxDif = dif1.maxValues(dif2);
        float maxVal = maxDif.maxVal();
        return (maxVal <= 0) ? true : false;
    }

    private static Collision2D createCollision(Bounds2D b1, Bounds2D b2) {
        return new Collision2D(b1, b2, b1.getCenter().minus(b2.getCenter()));
    }
}
