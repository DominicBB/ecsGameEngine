package util.Mathf;

public class Mathfi {
    private static final int CONST_D_SHIFT = 30;
    private static final int CONST_D_FACT = 1 << 30;
    public static final FixedPoint reallyCloseToOne = new FixedPoint(floatToFixed(0.99999f, CONST_D_FACT), CONST_D_SHIFT);
    public static final FixedPoint HALF = new FixedPoint(floatToFixed(0.5f, CONST_D_FACT), CONST_D_SHIFT);

    public static final int MAX_D_FACT = 1 << 30;


    /**
     * @param f
     * @param decimal_factor = 1 << decimal_shift
     * @return
     */
    public static int floatToFixed(float f, int decimal_factor) {
        return (int) (f * decimal_factor);
    }

    /**
     * @param fp
     * @param decimal_factor = 1 << decimal_shift
     * @return
     */
    public static float fixedToFloat(int fp, int decimal_factor) {
        return ((float) fp) / decimal_factor;
    }

    public static int divide(int numerator, int denominator, int decimal_shift) {
        return (numerator << decimal_shift) / denominator;
    }

    /**
     * Converts numerator and denominator casted to long before division
     *
     * @param numerator
     * @param denominator
     * @param decimal_shift
     * @return
     */
    public static int divide_safe(int numerator, int denominator, int decimal_shift) {
        return (int) (((long) numerator << decimal_shift) / (long) denominator);
    }

    /**
     * Multiply two, same changeFormat, fixed point numbers. returning the result in that changeFormat
     *
     * @param fp_0
     * @param fp_1
     * @param decimal_shift
     * @return
     */
    public static int multiply(int fp_0, int fp_1, int left_val_shift, int decimal_shift) {
        return (fp_0 >> left_val_shift) * (fp_1 >> (decimal_shift - left_val_shift));
    }

    /**
     * Multiply two, same changeFormat, fixed point numbers. returning the result in that changeFormat
     * Converts fp_0 and fp_1 to long before multiplication
     *
     * @param fp_0
     * @param fp_1
     * @param decimal_shift
     * @return
     */
    public static int multiply_safe(int fp_0, int fp_1, int decimal_shift) {
        return (int) (((long) fp_0 * (long) fp_1) >> decimal_shift);
    }

    /**
     * Multiply two, same changeFormat, fixed point numbers. returning the result in that changeFormat
     * this uses rounding for greater precision but is slower
     *
     * @param fp_0
     * @param fp_1
     * @param decimal_shift
     * @return
     */
    public static int multiply_round(int fp_0, int fp_1, int decimal_shift) {
        int i = fp_0 * fp_1;
        i = i + ((i & 1 << (decimal_shift - 1)) << 1);
        return i >> decimal_shift;
    }

    public static int round(int fp, int decimal_shift) {
        return Mathfi.truncate(fp + (1 << (decimal_shift - 1)), decimal_shift);
    }

    public static int ceil(int fp, int decimal_shift) {
        fp += (reallyCloseToOne.value >> (CONST_D_SHIFT - decimal_shift));
        return truncate(fp, decimal_shift);
    }

    public static int ceil_destroy_format(int fp, int decimal_shift) {
        return (fp + (reallyCloseToOne.value >> (CONST_D_SHIFT - decimal_shift))) >> decimal_shift;
    }

    public static int truncate(int fp, int decimal_shift) {
        return (fp >> decimal_shift) << decimal_shift;
    }

    public static int truncateTo_0DShift(int fp, int decimal_shift) {
        return (fp >> decimal_shift);
    }

    public static int changeFormat(int fp, int decimal_shift, int decimal_shift_result) {
        int dif = decimal_shift_result - decimal_shift;
        return (dif >= 0) ? fp << dif : fp >> -dif;
    }

    public static int upFormat(int fp, int decimal_shift, int decimal_shift_result) {
        int dif = decimal_shift_result - decimal_shift;
        return fp << dif;
    }

    public static int downFormat(int fp, int decimal_shift, int decimal_shift_result) {
        int dif = decimal_shift - decimal_shift_result;
        return fp >> dif;
    }

    public static int abs(int fp, int decimal_shift) {
        int signBit = fp >> (31 - decimal_shift);
        return (fp + (signBit)) ^ (signBit);
    }
}
