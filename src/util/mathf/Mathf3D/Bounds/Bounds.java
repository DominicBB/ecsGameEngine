package util.mathf.Mathf3D.Bounds;

import util.mathf.Mathf3D.Vec4f;

public abstract class Bounds {

    public abstract boolean contains(Vec4f position);
    public abstract Vec4f getCenter();

}
