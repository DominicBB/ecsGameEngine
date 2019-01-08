package Physics.physicsUtil;

import util.Mathf.Mathf2D.Bounds2D.AABoundingRect;
import util.Mathf.Mathf2D.Bounds2D.BoundingCircle;
import util.Mathf.Mathf2D.Bounds2D.Bounds2D;
import util.Mathf.Mathf2D.Vec2f;

public class Intersect2D {

    private Intersect2D() {
    }

    public static Collision2D intersects(BoundingCircle bc1, BoundingCircle bc2) {
        return (intersectsBool(bc1, bc2)) ? createCollision(bc1, bc2) : null;
    }

    public static boolean intersectsBool(BoundingCircle bc1, BoundingCircle bc2) {
        float maxCollisionDist = bc2.getRadius() + bc1.getRadius();
        float centerDist = bc2.getCenter().minus(bc1.getCenter()).magnitude();

        return centerDist <= maxCollisionDist;
    }


    public static Collision2D intersects(BoundingCircle bc, AABoundingRect aabr) {
        return (intersectsBool(bc, aabr)) ? createCollision(bc, aabr) : null;
    }

    public static boolean intersectsBool(BoundingCircle bc, AABoundingRect aabr) {
        if (aabr.contains(bc.getCenter()))
            return true;
        Vec2f centerDif = bc.getCenter().absDiff(aabr.getCenter());
        if (centerDif.x > (bc.getRadius() + aabr.getHalfSize().x) || centerDif.y > (bc.getRadius() + aabr.getHalfSize().y))
            return false;

        if (centerDif.x <= bc.getRadius() || centerDif.y <= bc.getRadius())
            return true;

        float maxExtendsSquare = (centerDif.x - aabr.getHalfSize().x) * (centerDif.x - aabr.getHalfSize().x) +
                (centerDif.y - aabr.getHalfSize().y) * (centerDif.y - aabr.getHalfSize().y);

        return (maxExtendsSquare <= (bc.getRadius() * bc.getRadius()));

    }


    public static Collision2D intersects(AABoundingRect aabr1, AABoundingRect aabr2) {
        return (intersectsBool(aabr1, aabr2)) ? createCollision(aabr1, aabr2) : null;
    }

    public static boolean intersectsBool(AABoundingRect aabr1, AABoundingRect aabr2) {
        Vec2f dif1 = aabr2.getTopLeft().minus(aabr1.getBottomRight());
        Vec2f dif2 = aabr1.getTopLeft().minus(aabr2.getBottomRight());
        Vec2f maxDif = dif1.maxValues(dif2);
        float maxVal = maxDif.maxVal();
        return maxVal <= 0;
    }

    private static Collision2D createCollision(Bounds2D b1, Bounds2D b2) {
        return new Collision2D(b1, b2, b1.getCenter().minus(b2.getCenter()));
    }
}
