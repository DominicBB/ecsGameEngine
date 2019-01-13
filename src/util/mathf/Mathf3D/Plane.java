package util.mathf.Mathf3D;

public class Plane {
    public final Vec4f normal;
    public final Vec4f pointOnPlane;
    
    public Plane(Vec4f normal, Vec4f pointOnPlane){
        this.normal = normal;
        this.pointOnPlane = pointOnPlane;
    }
    
    public float distFromPlane(Vec4f point){
        return (normal.x * point.x + normal.y * point.y + normal.z * point.z - normal.dotProduct(pointOnPlane));

    }
}
