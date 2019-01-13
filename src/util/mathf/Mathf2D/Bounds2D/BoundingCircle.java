package util.mathf.Mathf2D.Bounds2D;

import util.mathf.Mathf2D.Vec2f;

public class BoundingCircle extends Bounds2D {
    private Vec2f center;
    private float radius;

    public BoundingCircle(Vec2f center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public boolean contains(Vec2f position) {
        float distance = position.minus(center).magnitude();
        return distance <= radius;
    }

    @Override
    public Vec2f getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }
}
