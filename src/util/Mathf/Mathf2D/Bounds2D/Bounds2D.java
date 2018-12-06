package util.Mathf.Mathf2D.Bounds2D;

import util.Mathf.Mathf2D.Vector2D;

public abstract class Bounds2D {
    public abstract boolean contains(Vector2D position);
    public abstract Vector2D getCenter();
}
