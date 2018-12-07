package Rendering.renderUtil;

import util.Mathf.Mathf;
import util.Mathf.Mathf3D.Vector3D;

public class Colorf {
    public static final Vector3D minColor = Vector3D.newZeros();
    public static final Vector3D maxColor = new Vector3D(255f,255f,255f);

    public static Vector3D clamp(Vector3D color){
        return Mathf.clamp(minColor,color,maxColor);
    }

    public static final Vector3D WHITE = new Vector3D(255f,255f,255f);
    public static final Vector3D BLUE = new Vector3D(0f,0f,255f);
    public static final Vector3D GREEN = new Vector3D(0,255f,0f);
    public static final Vector3D RED = new Vector3D(255f,0f,0f);

}
