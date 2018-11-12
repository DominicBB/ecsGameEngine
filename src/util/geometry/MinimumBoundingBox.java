package util.geometry;

public class MinimumBoundingBox {

    public Vector3D min;
    public Vector3D max;

    public MinimumBoundingBox(float xMin, float xMax, float yMin, float yMax, float zMin, float zMax) {
        max = new Vector3D(xMax, yMax, zMax);
        min = new Vector3D(xMin, yMin, zMin);
    }

    public MinimumBoundingBox() {
        min = new Vector3D(0f, 0f, 0f);
        max = new Vector3D(0f, 0f, 0f);
    }


}
