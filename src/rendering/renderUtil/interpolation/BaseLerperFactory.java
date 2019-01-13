package rendering.renderUtil.interpolation;

import rendering.drawers.fill.Rasterfi;

public abstract class BaseLerperFactory {
    //TODO decide on factor D_SHIFT.
    public static int calcStep(int factor, int f1, int f2) {
        return Rasterfi.multiply(f2 - f1, factor);
    }

    public static int calcStep_x(int factor, int f1, int f2) {
        return (((f2 - f1)) * factor) >> Rasterfi.D_SHIFT_INV;
    }

    public static int calcStep(int factor, int f1, int f2, int left_val_shift) {
        return Rasterfi.multiply(f2 - f1, factor, left_val_shift);
    }

    public static int calcStep_shiftAfter(int factor, int f1, int f2) {
        return Rasterfi.multiply_shiftAfter(f2 - f1, factor);
    }

    public static int calcStep_color(int factor, int f1, int f2) {
        return (((f2 - f1)) * factor) >> Rasterfi.D_SHIFT_INV; // 15 as factor is shift 15
    }

    public static int calcStep_tex(int factor, int f1, int f2) {
        return (((f2 - f1)) * (factor)) >> Rasterfi.D_SHIFT_INV;
    }
}
