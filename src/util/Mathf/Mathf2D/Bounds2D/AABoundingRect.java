package util.Mathf.Mathf2D.Bounds2D;

import util.Mathf.Mathf2D.Vector2D;

public class AABoundingRect extends Bounds2D {
    private Vector2D center;
    private Vector2D size;
    private Vector2D halfSize;
    private Vector2D minExtents;
    private final Vector2D maxExtents;


    public AABoundingRect(Vector2D center, Vector2D size) {
        this.center = center;
        this.size = size;
        halfSize = size.divide(2f);
        this.minExtents = center.minus(halfSize);
        this.maxExtents = center.plus(halfSize);
    }

    @Override
    public boolean contains(Vector2D point) {
        return point.x >= minExtents.x && point.x <= maxExtents.x
                && point.y >= minExtents.y && point.y <= maxExtents.y;
    }

    @Override
    public Vector2D getCenter() {
        return center;
    }

    public Vector2D getMaxExtents() {
        return maxExtents;
    }

    public Vector2D getMinExtents() {
        return minExtents;
    }

    public Vector2D getSize() {
        return size;
    }

    public Vector2D getHalfSize() {
        return halfSize;
    }
}
