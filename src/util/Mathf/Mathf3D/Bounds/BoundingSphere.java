package util.Mathf.Mathf3D.Bounds;

import util.Mathf.Mathf3D.Vector3D;

public class BoundingSphere extends Bounds{

    private Vector3D center;
    private float radius;
    public BoundingSphere(Vector3D center, float radius){
        this.center = center;
        this.radius = radius;
    }
    @Override
    public boolean contains(Vector3D position) {
        float distance = position.minus(center).magnitude();
        return distance <= radius;
    }



    @Override
    public Vector3D getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }
}
