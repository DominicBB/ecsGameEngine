package rendering.renderUtil;

import util.Mathf.Mathf;
import util.Mathf.Mathf3D.Vec4f;
import util.Mathf.Mathf3D.Vec4fi;
import util.Mathf.Mathf3D.Vec4fiImmut;

public class Colorf {
    public static final Vec4f minColor = new Vec4f(0f, 0f, 0f, 255f);
    public static final Vec4f maxColor = new Vec4f(255f, 255f, 255f, 255f);
    public static final Vec4fiImmut maxColorfp = new Vec4fiImmut(255, 255, 255, 255, 0);

    //TESTING STUFF
   /* private static final int zero = 0;
    private static final int oxff = 255;
    private static final int[] ORS = new int[]{zero, oxff, oxff, oxff};*/

    private static final IntMapper toSelf = new MapToSelf();
    private static final IntMapper to255 = new MapTo255();
    //    private static final IntMapper getCOR2 = c -> 0xFF;
    private static final IntMapper[] clampTo255 = new IntMapper[]{toSelf, to255, to255, to255, to255};
    private static final int SHIFT_AMT = 8;
    //TESTING STUFF END

    public static Vec4f clamp(Vec4f color) {
        return Mathf.clamp(minColor, color, maxColor);
    }

    public static void clampNonAlloc(Vec4f color) {
        Mathf.clampNonAlloc(minColor, color, maxColor);
    }

    public static void clampMinNonAlloc(Vec4f color) {
        Mathf.clampMinNonAlloc(minColor, color);
    }

    public static void clampMaxNonAlloc(Vec4f color) {
        Mathf.clampMaxNonAlloc(color, maxColor);
    }

    public static void clampMaxNonAlloc(Vec4fi color) {
        color.x = (color.x <= maxColorfp.x) ? color.x : maxColorfp.x;
        color.y = (color.y <= maxColorfp.y) ? color.y : maxColorfp.y;
        color.z = (color.z <= maxColorfp.z) ? color.z : maxColorfp.z;
//        color.w = (color.w <= maxColorfp.w) ? color.w : maxColorfp.w;
    }

    public static void clampMaxNonAllocBit(Vec4fi color) {
       /* int r = (int) color.x;
        int g = (int) color.y;
        int b = (int) color.z;

        int rOR = ORS[(r >> 8)];
        int gOR = ORS[(g >> 8)];
        int bOR = ORS[(b >> 8)];*/


//        out.x = (ORS[(c >> 8)] | c);
        color.x = clampTo255[(color.x >> SHIFT_AMT)].map(color.x);
//        out.x = rOR | r;

//        out.y = (ORS[(c >> 8)] | c);
        color.y = clampTo255[(color.y >> SHIFT_AMT)].map(color.y);
//        out.y = gOR | g;

//        out.z = (ORS[(c >> 8)] | c);
        color.z = clampTo255[(color.z >> SHIFT_AMT)].map(color.z);
//        out.z = bOR | b;

//        c = (int) color.w;
//        out.w = (ORS[(c >> 8)] | c);
//        out.w = (ORSF[(c >> 8)].apply(c));
//        out.w = (clampTo255[(c >> 8)].map(c));

    }

    public static final Vec4f WHITE = new Vec4f(255f, 255f, 255f);
    public static final Vec4f BLUE = new Vec4f(0f, 0f, 255f);
    public static final Vec4f GREEN = new Vec4f(0f, 255f, 0f);
    public static final Vec4f RED = new Vec4f(255f, 0f, 0f);


}
