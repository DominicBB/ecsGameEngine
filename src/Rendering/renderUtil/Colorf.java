package Rendering.renderUtil;

import util.Mathf.Mathf;
import util.Mathf.Mathf3D.Vector3D;
import util.Mathf.Mathf3D.Vector3DInt;

import java.util.function.Function;

public class Colorf {
    public static final Vector3D minColor = new Vector3D(0f, 0f, 0f, 255f);
    public static final Vector3D maxColor = new Vector3D(255f, 255f, 255f, 255f);

    //TESTING STUFF
    private static final int zero = 0;
    private static final int oxff = 255;
    private static final int[] ORS = new int[]{zero, oxff, oxff};

    private static final IntMapper getC = c -> c;
    private static final IntMapper get255 = c -> 0xFF;
//    private static final IntMapper getCOR2 = c -> 0xFF;
    private static final IntMapper[] ORSFInt = new IntMapper[]{getC, get255, get255};
    //TESTING STUFF END

    public static Vector3D clamp(Vector3D color) {
        return Mathf.clamp(minColor, color, maxColor);
    }

    public static void clampNonAlloc(Vector3D color) {
        Mathf.clampNonAlloc(minColor, color, maxColor);
    }

    public static void clampMinNonAlloc(Vector3D color) {
        Mathf.clampMinNonAlloc(minColor, color);
    }

    public static void clampMaxNonAlloc(Vector3D color) {
        Mathf.clampMaxNonAlloc(color, maxColor);
    }

    public static void clampMaxNonAllocBit(Vector3D color, Vector3DInt out) {
        int c = (int) color.x;
        out.x = (ORS[(c >> 8)] | c);
//        out.x = (ORSFInt[(c >> 8)].map(c));

        c = (int) color.y;
        out.y = (ORS[(c >> 8)] | c);
//        out.y = (ORSFInt[(c >> 8)].map(c));


        c = (int) color.z;
        out.z = (ORS[(c >> 8)] | c);
//        out.z = (ORSFInt[(c >> 8)].map(c));


//        c = (int) color.w;
//        out.w = (ORS[(c >> 8)] | c);
//        out.w = (ORSF[(c >> 8)].apply(c));
//        out.w = (ORSFInt[(c >> 8)].map(c));

    }

    public static final Vector3D WHITE = new Vector3D(255f, 255f, 255f);
    public static final Vector3D BLUE = new Vector3D(0f, 0f, 255f);
    public static final Vector3D GREEN = new Vector3D(0f, 255f, 0f);
    public static final Vector3D RED = new Vector3D(255f, 0f, 0f);

}
