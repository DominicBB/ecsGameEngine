package util.Mathf.Mathf2D.Bounds2D;

import util.Mathf.Mathf2D.Vec2f;

public class AABoundingRect extends Bounds2D {

    private Vec2f topLeft;
    private Vec2f bottomRight;
    private Vec2f halfSize;

    public AABoundingRect(Vec2f topLeft, Vec2f bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        halfSize = bottomRight.minus(topLeft).divide(2f);
    }

    @Override
    public boolean contains(Vec2f point) {
        return point.x >= topLeft.x && point.x <= bottomRight.x
                && point.y >= topLeft.y && point.y <= bottomRight.y;
    }

    public void set(float maxY, float minY, float maxX, float minX) {
        this.topLeft.set(minX, maxY);
        this.bottomRight.set(maxX, minY);
        halfSize = bottomRight.minus(topLeft).divide(2f);
    }

    @Override
    public Vec2f getCenter() {
        return topLeft.plus(halfSize);
    }

    public Vec2f getBottomRight() {
        return bottomRight;
    }

    public Vec2f getTopLeft() {
        return topLeft;
    }

    public Vec2f getSize() {
        return halfSize.mul(2f);
    }

    public Vec2f getHalfSize() {
        return halfSize;
    }
}
