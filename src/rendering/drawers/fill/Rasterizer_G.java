package rendering.drawers.fill;

import rendering.renderUtil.RenderState;
import rendering.renderUtil.interpolation.gouruad.GouruadInterpolants;
import rendering.renderers.Renderer;
import rendering.shaders.GouraudShader;
import util.Mathf.Mathf3D.Vec4fi;

import static rendering.renderUtil.interpolation.BaseLerperFactory.*;

class Rasterizer_G {
    //        final GouruadLerper_R gouruadLerper_r = new GouruadLerper_R();
    private int ro_z, ro_invW, ro_specularity, ro_color_r, ro_color_g, ro_color_b, ro_color_a, ro_tex_u, ro_tex_v, ro_spec_u, ro_spec_v;

    private final GouruadInterpolants ROW_I_INTERPOLANTS = new GouruadInterpolants(null);

    final void rasterizeRow(GouruadInterpolants left, GouruadInterpolants right, int y, Vec4fi fColor, Vec4fi util) {
        int from = Rasterfi.ceil_destroy_format_pos(left.x);
        int to = Rasterfi.ceil_destroy_format_pos(right.x);
        setRowInterpolants(left, from);
        if (to - from == 0) {
            fragShade(y, fColor, util);
            return;
        }

        int invdX = Rasterfi.inverse_pos(right.x - left.x);
//        RowLerperFactory.gouruadLerper(gouruadLerper_r, RenderState.material, left, right, invdX);
        set_ro(left, right, invdX);

        for (; ROW_I_INTERPOLANTS.xInt < to; ROW_I_INTERPOLANTS.xInt++) {
            fragShade(y, fColor, util);
//            gouruadLerper_r.lerp(ROW_I_INTERPOLANTS);
            lerpRow();
        }
    }

    private void fragShade(int y, Vec4fi fColor, Vec4fi util) {
        if (GouraudShader.fragNonAlloc(ROW_I_INTERPOLANTS, fColor, util, y))
            Renderer.onFragShaded(ROW_I_INTERPOLANTS.xInt, y, fColor, RenderState.material);
    }

    private void setRowInterpolants(GouruadInterpolants gI, int xInt) {
        ROW_I_INTERPOLANTS.reset(xInt, gI.x, gI.z, gI.invW, gI.specularity, gI.color_r, gI.color_g, gI.color_b, gI.color_a,
                gI.tex_u, gI.tex_v, gI.spec_u, gI.spec_v);
    }

    private void set_ro(GouruadInterpolants gI, GouruadInterpolants gI2, int factor) {
        ro_z = calcStep_shiftAfter(factor, gI.z, gI2.z);

//        ro_color_a = calcStep_color(factor, gI.color_a, gI2.color_a);
        ro_color_r = calcStep_color(factor, gI.color_r, gI2.color_r);
        ro_color_g = calcStep_color(factor, gI.color_g, gI2.color_g);
        ro_color_b = calcStep_color(factor, gI.color_b, gI2.color_b);

        ro_invW = calcStep_shiftAfter(factor, gI.invW, gI2.invW);

        if (RenderState.material.hasTexture()) {
            ro_tex_u = calcStep_tex(factor, gI.tex_u, gI2.tex_u);
            ro_tex_v = calcStep_tex(factor, gI.tex_v, gI2.tex_v);
        }

        if (RenderState.material.isSpecular()) {
            if (RenderState.material.hasSpecularMap()) {
                ro_spec_u = calcStep(factor, gI.spec_u, gI2.spec_u);
                ro_spec_v = calcStep(factor, gI.spec_v, gI2.spec_v);
            }
            ro_specularity = calcStep_tex(factor, gI.specularity, gI2.specularity);
        }
    }

    private void lerpRow() {
        ROW_I_INTERPOLANTS.z += ro_z;
        ROW_I_INTERPOLANTS.invW += ro_invW;

//        ROW_I_INTERPOLANTS.color_a += ro_color_a;
        ROW_I_INTERPOLANTS.color_b += ro_color_b;
        ROW_I_INTERPOLANTS.color_g += ro_color_g;
        ROW_I_INTERPOLANTS.color_r += ro_color_r;


        ROW_I_INTERPOLANTS.tex_u += ro_tex_u;
        ROW_I_INTERPOLANTS.tex_v += ro_tex_v;
        ROW_I_INTERPOLANTS.spec_u += ro_spec_u;
        ROW_I_INTERPOLANTS.spec_v += ro_spec_v;
        ROW_I_INTERPOLANTS.specularity += ro_specularity;
    }

    //----------TEST----------------------
    /*final void rasterizeRow(int y, int to, Vec4fi fColor, Vec4fi util) {
        for (; ROW_I_INTERPOLANTS.xInt < to; ROW_I_INTERPOLANTS.xInt++) {
            fragShade(y, fColor, util);
            gouruadLerper_r.lerp(ROW_I_INTERPOLANTS);
        }
    }*/
}
