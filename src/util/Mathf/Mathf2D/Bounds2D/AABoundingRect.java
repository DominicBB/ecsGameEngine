package util.Mathf.Mathf2D.Bounds2D;

import util.Mathf.Mathf2D.Vector2D;

public class AABoundingRect extends Bounds2D {

    private Vector2D topLeft;
    private Vector2D bottomRight;
    private Vector2D halfSize;

    public AABoundingRect(Vector2D topLeft, Vector2D bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        halfSize = bottomRight.minus(topLeft).divide(2f);
    }

    @Override
    public boolean contains(Vector2D point) {
        return point.x >= topLeft.x && point.x <= bottomRight.x
                && point.y >= topLeft.y && point.y <= bottomRight.y;
    }

    public void set(float maxY, float minY, float maxX, float minX) {
        this.topLeft.set(minX, maxY);
        this.bottomRight.set(maxX, minY);
        halfSize = bottomRight.minus(topLeft).divide(2f);
    }

    @Override
    public Vector2D getCenter() {
        return topLeft.plus(halfSize);
    }

    public Vector2D getBottomRight() {
        return bottomRight;
    }

    public Vector2D getTopLeft() {
        return topLeft;
    }

    public Vector2D getSize() {
        return halfSize.mul(2f);
    }

    public Vector2D getHalfSize() {
        return halfSize;
    }
}
