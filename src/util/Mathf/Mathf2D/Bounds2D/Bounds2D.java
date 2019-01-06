package util.Mathf.Mathf2D.Bounds2D;

import util.Mathf.Mathf2D.Vec2f;

public abstract class Bounds2D {
    public abstract boolean contains(Vec2f position);
    public abstract Vec2f getCenter();
}
