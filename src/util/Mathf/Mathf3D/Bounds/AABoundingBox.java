package util.Mathf.Mathf3D.Bounds;

import util.Mathf.Mathf3D.Vector3D;

public class AABoundingBox extends Bounds {

    private Vector3D halfSize;
    private Vector3D center;
    private Vector3D size;



    public AABoundingBox(Vector3D center, Vector3D size) {
        this.halfSize = size.divide(2f);

        this.size = size;
        this.center = center;
    }

    /*public AABoundingBox(float xMin, float xMax, float yMin, float yMax, float zMin, float zMax) {
        maxExtents = new Vector3D(xMax, yMax, zMax);
        minExtents = new Vector3D(xMin, yMin, zMin);
    }*/

    private AABoundingBox() {
        center = Vector3D.newZeros();
        size = Vector3D.newZeros();
        halfSize = Vector3D.newZeros();
    }

    public static AABoundingBox zeros() {
        return new AABoundingBox();
    }

    @Override
    public boolean contains(Vector3D position) {
        Vector3D minExtents = getMinExtents();
        Vector3D maxExtents = getMaxExtents();
        return position.x >= minExtents.x && position.x <= maxExtents.x
                && position.y >= minExtents.y && position.y <= maxExtents.y
                && position.z >= minExtents.z && position.z <= maxExtents.z;
    }

    @Override
    public Vector3D getCenter() {
        return center;
    }

    public void setCenter(Vector3D center) {
        this.center = center;
    }

    public Vector3D getMaxExtents() {
        return center.plus(halfSize);
    }

    public Vector3D getMinExtents() {
        return center.minus(halfSize);
    }

    public Vector3D getHalfSize() {
        return halfSize;
    }

    public Vector3D getSize() {
        return size;
    }
}
