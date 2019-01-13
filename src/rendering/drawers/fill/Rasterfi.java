package rendering.drawers.fill;

import util.Mathf.Mathf3D.Vec4fi;
import util.Mathf.Mathfi;

public class Rasterfi {
    public static final int D_SHIFT_INV = 15;
    public static final int D_SHIFT_COLOR = 10;
    public static final int D_SHIFT_POS = 10;
    public static final int D_SHIFT_COORD = 10;

    public static final int D_FACTOR_INV = 1 << D_SHIFT_INV;
    public static final int D_FACTOR_COLOR = 1 << D_SHIFT_COLOR;
    public static final int D_FACTOR_POS = 1 << D_SHIFT_POS;
    public static final int D_FACTOR_COORD = 1 << D_SHIFT_COORD;

    private static final int DEFAULT_L_SHIFT = D_SHIFT_INV / 2;
    private static final int INVERSE_SHIFT_MUL = D_SHIFT_INV - (30 - D_SHIFT_INV);//0
    private static final int reallyCloseToOne = Mathfi.reallyCloseToOne.value >> (Mathfi.reallyCloseToOne.D_SHIFT - D_SHIFT_INV);
    private static final float INV_D_FACT = 1f / ((float) D_FACTOR_INV);

    /**
     * @param f
     * @return
     */
    public static int floatToFixed(float f) {
        return (int) (f * D_FACTOR_INV);
    }

    public static int floatToFixed_color(float f) {
        return (int) (f * D_FACTOR_COLOR);
    }

    public static int floatToFixed_pos(float f) {
        return (int) (f * D_FACTOR_POS);
    }

    /**
     * @param fp
     * @return
     */
    public static float fixedToFloat(int fp) {
        return ((float) fp) * INV_D_FACT;
    }

    public static int inverse(int fp) {
        return ((Mathfi.MAX_D_FACT) / (fp)) /*<< INVERSE_SHIFT_MUL*/;
    }

    /**
     * @param fp in d_shift 10
     * @return in d_shift 19
     */
    public static int inverse_pos(int fp) {
        return ((Mathfi.MAX_D_FACT) / (fp)) >> 5;
    }

    /**
     * @param fp in d_shift 15
     * @return in d_shift 10
     */
    public static int un_inverse(int fp) {
        return ((Mathfi.MAX_D_FACT) / (fp)) >> 5 /*<< INVERSE_SHIFT_MUL*/;
    }

    public static int multiplyByInv(int fp_0, int inv) {
        return ((fp_0 >> 18) * (inv >> (1)));
    }

    public static int multiply_shiftAfter(int fp_0, int fp_1) {
        return (fp_0 * fp_1) >> D_SHIFT_INV;
    }

    public static int multiply(int fp_0, int fp_1, int left_val_shift) {
        return ((fp_0 >> left_val_shift) * (fp_1 >> (D_SHIFT_INV - left_val_shift)));
    }

    public static int multiply(int fp_0, int fp_1) {
        return multiply(fp_0, fp_1, DEFAULT_L_SHIFT);
    }

    public static int multiply_onlySR(int fp_0, int fp_1) {
        return (fp_0 * (fp_1 >> D_SHIFT_INV));
    }

    public static int multiply_noshift(int fp_0, int fp_1) {
        return fp_0 * fp_1;
    }

    /**
     * @param coord in d_shift = D_FACTOR_COORD
     * @param z     in d_shift = 15
     * @return
     */
    public static int multiply_sample_coord(int coord, int z) {
        return ((coord) * (z));
    }


    public static void multiply_noshift(Vec4fi vfp0, Vec4fi vfp1) {
        vfp0.set(
                multiply_noshift(vfp0.x, vfp1.x),
                multiply_noshift(vfp0.y, vfp1.y),
                multiply_noshift(vfp0.z, vfp1.z),
                /*multiply_noshift(vfp0.w, vfp1.w)*/vfp0.w);
    }


    public static int ceil_pos(int fp) {
        return Mathfi.ceil(fp, D_SHIFT_POS);
    }

    public static int ceil_destroy_format_pos(int fp) {
        return (fp + reallyCloseToOne) >> D_SHIFT_POS;
    }

    public static int toInt(int fp, int d_shift) {
        return fp >> d_shift;
    }

    public static int toInt_inv(int fp) {
        return fp >> D_SHIFT_INV;
    }

    public static int toInt_color(int fp) {
        return fp >> D_SHIFT_COLOR;
    }

    public static int toInt_pos(int fp) {
        return fp >> D_SHIFT_POS;
    }

    public static int abs(int fp, int decimal_shift) {
        int signBit = fp >> (31 - decimal_shift);
        return (fp + signBit) ^ signBit;
    }
}
