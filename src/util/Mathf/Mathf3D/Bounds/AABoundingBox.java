package util.Mathf.Mathf3D.Bounds;

import util.Mathf.Mathf3D.Vec4f;

public class AABoundingBox extends Bounds {

    private Vec4f halfSize;
    private Vec4f center;
    private Vec4f size;



    public AABoundingBox(Vec4f center, Vec4f size) {
        this.halfSize = size.divide(2f);

        this.size = size;
        this.center = center;
    }

    /*public AABoundingBox(float xMin, float xMax, float yMin, float yMax, float zMin, float zMax) {
        maxExtents = new Vec4f(xMax, yMax, zMax);
        minExtents = new Vec4f(xMin, yMin, zMin);
    }*/

    private AABoundingBox() {
        center = Vec4f.newZeros();
        size = Vec4f.newZeros();
        halfSize = Vec4f.newZeros();
    }

    public static AABoundingBox zeros() {
        return new AABoundingBox();
    }

    @Override
    public boolean contains(Vec4f position) {
        Vec4f minExtents = getMinExtents();
        Vec4f maxExtents = getMaxExtents();
        return position.x >= minExtents.x && position.x <= maxExtents.x
                && position.y >= minExtents.y && position.y <= maxExtents.y
                && position.z >= minExtents.z && position.z <= maxExtents.z;
    }

    @Override
    public Vec4f getCenter() {
        return center;
    }

    public void setCenter(Vec4f center) {
        this.center = center;
    }

    public Vec4f getMaxExtents() {
        return center.plus(halfSize);
    }

    public Vec4f getMinExtents() {
        return center.minus(halfSize);
    }

    public Vec4f getHalfSize() {
        return halfSize;
    }

    public Vec4f getSize() {
        return size;
    }
}
