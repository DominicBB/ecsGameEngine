package Rendering.renderUtil.interpolation;

import Rendering.drawers.fill.Rasterfi;
import util.Mathf.Mathf2D.Vec2f;
import util.Mathf.Mathf3D.Vec4f;

public abstract class BaseLerperFactory {
    protected static final int colorStep_shift = 12;

    protected static void calcVec2Step(Vec2f out, float factor, Vec2f vIn1, Vec2f vIn2) {
        out.set(
                (vIn2.x - vIn1.x) * factor,
                (vIn2.y - vIn1.y) * factor
        );
    }

    //TODO
    static int calcStep(int factor, int f1, int f2) {
        return Rasterfi.multiply(f2 - f1, factor);
    }

    static int calcStep_x(int factor, int f1, int f2) {
        return (((f2 - f1) >> colorStep_shift) * factor) >> 7;
    }

    static int calcStep(int factor, int f1, int f2, int left_val_shift) {
        return Rasterfi.multiply(f2 - f1, factor, left_val_shift);
    }

    static int calcStep_shiftAfter(int factor, int f1, int f2) {
        return Rasterfi.multiply_shiftAfter(f2 - f1, factor);
    }

    static int calcStep_color(int factor, int f1, int f2) {
        return (((f2 - f1) >> colorStep_shift) * factor) >> 7;
    }

    static int calcStep_tex(int factor, int f1, int f2) {
        return (((f2 - f1) >> 4) * (factor >> 4)) >> 11;
    }


    protected static void calcVec3Step(Vec4f out, float factor, Vec4f vIn1, Vec4f vIn2) {
        out.set(
                (vIn2.x - vIn1.x) * factor,
                (vIn2.y - vIn1.y) * factor,
                (vIn2.z - vIn1.z) * factor,
                (vIn2.w - vIn1.w) * factor
        );
    }
}
