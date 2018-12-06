package util.Mathf.Mathf3D.Bounds;

import util.Mathf.Mathf3D.Vector3D;

public abstract class Bounds {

    public abstract boolean contains(Vector3D position);
    public abstract Vector3D getCenter();

}
