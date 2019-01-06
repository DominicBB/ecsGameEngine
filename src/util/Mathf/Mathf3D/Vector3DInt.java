package util.Mathf.Mathf3D;

public class Vector3DInt {
    public int x;
    public int y;
    public int z;
    public int w;

    public Vector3DInt(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void set(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void add_with_w(Vector3DInt v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        this.w += v.w;
    }


}
