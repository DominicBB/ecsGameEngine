package util.mathf.Mathf3D.Bounds;

import util.mathf.Mathf3D.Vec4f;

public class BoundingSphere extends Bounds{

    private Vec4f center;
    private float radius;
    public BoundingSphere(Vec4f center, float radius){
        this.center = center;
        this.radius = radius;
    }
    @Override
    public boolean contains(Vec4f position) {
        float distance = position.minus(center).magnitude();
        return distance <= radius;
    }



    @Override
    public Vec4f getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }
}
