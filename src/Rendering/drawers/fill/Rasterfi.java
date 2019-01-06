package Rendering.drawers.fill;

import util.Mathf.Mathf3D.Vec4fi;
import util.Mathf.Mathfi;

public class Rasterfi {
    public static final int D_SHIFT = 19;
    public static final int D_FACTOR = 1 << D_SHIFT;

    private static final int DEFAULT_L_SHIFT = D_SHIFT / 2;
    private static final int INVERSE_SHIFT = D_SHIFT - (30 - D_SHIFT);

    /**
     * @param f
     * @return
     */
    public static int floatToFixed(float f) {
        return (int) (f * D_FACTOR);
    }

    /**
     * @param fp
     * @return
     */
    public static float fixedToFloat(int fp) {
        return ((float) fp) / D_FACTOR;
    }

    public static int inverse(int fp) {
        return ((Mathfi.MAX_D_FACT) / (fp >> INVERSE_SHIFT));
    }

    public static int un_inverse(int fp) {
        return ((Mathfi.MAX_D_FACT) / (fp >> 1)) << 7;
    }

    public static int multiplyByInv(int fp_0, int inv) {
        return ((fp_0 >> 18) * (inv >> (1)));
    }

    public static int multiply_shiftAfter(int fp_0, int fp_1) {
        return (fp_0 * fp_1) >> D_SHIFT;
    }


    //TODO test this
    public static int multiply(int fp_0, int fp_1, int left_val_shift) {
        return ((fp_0 >> left_val_shift) * (fp_1 >> (D_SHIFT - left_val_shift)));
    }

    public static int multiply(int fp_0, int fp_1) {
        return multiply(fp_0, fp_1, DEFAULT_L_SHIFT);
    }


    public static void multiply(Vec4fi vfp0, Vec4fi vfp1, int left_val_shift) {
        vfp0.set(
                /*multiply(vfp0.x, vfp1.x, left_val_shift),
                multiply(vfp0.y, vfp1.y, left_val_shift),
                multiply(vfp0.z, vfp1.z, left_val_shift),
                multiply(vfp0.w, vfp1.w, left_val_shift)*/
                multiply_shiftAfter(vfp0.x, vfp1.x),
                multiply_shiftAfter(vfp0.y, vfp1.y),
                multiply_shiftAfter(vfp0.z, vfp1.z),
                multiply_shiftAfter(vfp0.w, vfp1.w));
    }


    public static int ceil(int fp) {
        return Mathfi.ceil(fp, D_SHIFT);
    }

    public static int ceil_destroy_format(int fp) {
        return Mathfi.ceil_destroy_format(fp, D_SHIFT);
    }

    public static int toInt(int fp) {
        return fp >> D_SHIFT;
    }

    public static int abs(int fp, int decimal_shift) {
        int signBit = fp >> (31 - decimal_shift);
        return (fp + (signBit)) ^ (signBit);
    }
}
