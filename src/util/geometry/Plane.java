package util.geometry;

public class Plane {
    public final Vector3D normal;
    public final Vector3D pointOnPlane;
    
    public Plane(Vector3D normal, Vector3D pointOnPlane){
        this.normal = normal;
        this.pointOnPlane = pointOnPlane;
    }
    
    public float distFromPlane(Vector3D point){
        return (normal.x * point.x + normal.y * point.y + normal.z * point.z - normal.dotProduct(pointOnPlane));

    }
}
