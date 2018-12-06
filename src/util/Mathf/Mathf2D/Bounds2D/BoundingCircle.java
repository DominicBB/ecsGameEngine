package util.Mathf.Mathf2D.Bounds2D;

import util.Mathf.Mathf2D.Vector2D;

public class BoundingCircle extends Bounds2D {
    private Vector2D center;
    private float radius;

    public BoundingCircle(Vector2D center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public boolean contains(Vector2D position) {
        float distance = position.minus(center).magnitude();
        return distance <= radius;
    }

    @Override
    public Vector2D getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }
}
