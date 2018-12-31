package Rendering.renderUtil;

import util.Mathf.Mathf;
import util.Mathf.Mathf3D.Vector3D;
import util.Mathf.Mathf3D.Vector3DInt;

public class Colorf {
    public static final Vector3D minColor = new Vector3D(0f, 0f, 0f, 255f);
    public static final Vector3D maxColor = new Vector3D(255f, 255f, 255f, 255f);

    //TESTING STUFF
    private static final int zero = 0;
    private static final int oxff = 255;
    private static final int[] ORS = new int[]{zero, oxff, oxff, oxff};

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
       /* int r = (int) color.x;
        int g = (int) color.y;
        int b = (int) color.z;

        int rOR = ORS[(r >> 8)];
        int gOR = ORS[(g >> 8)];
        int bOR = ORS[(b >> 8)];*/


        int c = (int) color.x;
        out.x = (ORS[(c >> 8)] | c);
//        out.x = (ORSFInt[(c >> 8)].map(c));
//        out.x = rOR | r;

        c = (int) color.y;
//        c = Mathf.fastFloor(color.y);
        out.y = (ORS[(c >> 8)] | c);
//        out.y = (ORSFInt[(c >> 8)].map(c));
//        out.y = gOR | g;

        c = (int) color.z;
//        c = Mathf.fastFloor(color.z);
        out.z = (ORS[(c >> 8)] | c);
//        out.z = (ORSFInt[(c >> 8)].map(c));
//        out.z = bOR | b;

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
