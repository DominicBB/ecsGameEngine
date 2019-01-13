package rendering.renderUtil.interpolation;

import rendering.Materials.Material;
import rendering.renderUtil.interpolation.flat.FlatInterpolants;
import rendering.renderUtil.interpolation.flat.FlatLerper_R;
import rendering.renderUtil.interpolation.gouruad.GouruadInterpolants;
import rendering.renderUtil.interpolation.gouruad.GouruadLerper_R;
import rendering.renderUtil.interpolation.phong.PhongInterpolants;
import rendering.renderUtil.interpolation.phong.PhongLerper_R;

public class RowLerperFactory extends BaseLerperFactory {


    public static void gouruadLerper(GouruadLerper_R gL, Material material, GouruadInterpolants gI, GouruadInterpolants gI2,
                                     int factor) {
        gL.z = calcStep_shiftAfter(factor, gI.z, gI2.z);

        gL.color_a = calcStep_color(factor, gI.color_a, gI2.color_a);
        gL.color_r = calcStep_color(factor, gI.color_r, gI2.color_r);
        gL.color_g = calcStep_color(factor, gI.color_g, gI2.color_g);
        gL.color_b = calcStep_color(factor, gI.color_b, gI2.color_b);

        gL.invW = calcStep_shiftAfter(factor, gI.invW, gI2.invW);

        if (material.hasTexture()) {
            gL.tex_u = calcStep_tex(factor, gI.tex_u, gI2.tex_u);
            gL.tex_v = calcStep_tex(factor, gI.tex_v, gI2.tex_v);
        }

        if (material.isSpecular()) {
            if (material.hasSpecularMap()) {
                gL.spec_u = calcStep(factor, gI.spec_u, gI2.spec_u);
                gL.spec_v = calcStep(factor, gI.spec_v, gI2.spec_v);
            }
            gL.specularity = calcStep_tex(factor, gI.specularity, gI2.specularity);
        }
    }

    public static void phongLerper(PhongLerper_R pL, Material material, PhongInterpolants pI, PhongInterpolants pI2,
                                   int factor) {
        pL.n_ws_x = calcStep(factor, pI.n_ws_x, pI2.n_ws_x);
        pL.n_ws_y = calcStep(factor, pI.n_ws_y, pI2.n_ws_y);
        pL.n_ws_z = calcStep(factor, pI.n_ws_z, pI2.n_ws_z);

        pL.p_ws_x = calcStep(factor, pI.p_ws_x, pI2.p_ws_x);
        pL.p_ws_y = calcStep(factor, pI.p_ws_y, pI2.p_ws_y);
        pL.p_ws_z = calcStep(factor, pI.p_ws_z, pI2.p_ws_z);

        pL.z = calcStep_shiftAfter(factor, pI.z, pI2.z);
        pL.invW = calcStep_shiftAfter(factor, pI.invW, pI2.invW);

        if (material.hasTexture()) {
            pL.tex_u = calcStep(factor, pI.tex_u, pI2.tex_u);
            pL.tex_v = calcStep(factor, pI.tex_v, pI2.tex_v);
        }

        if (material.isSpecular() && material.hasSpecularMap()) {
            pL.spec_u = calcStep(factor, pI.spec_u, pI2.spec_u);
            pL.spec_v = calcStep(factor, pI.spec_v, pI2.spec_v);
        }
    }

    public static void flatLerper(FlatLerper_R fL, Material material, FlatInterpolants fI, FlatInterpolants fI2,
                                  int factor) {
        fL.z = calcStep_shiftAfter(factor, fI.z, fI2.z);
        fL.invW = calcStep_shiftAfter(factor, fI.invW, fI2.invW);

        if (material.hasTexture()) {
            fL.tex_u = calcStep(factor, fI.tex_u, fI2.tex_u);
            fL.tex_v = calcStep(factor, fI.tex_v, fI2.tex_v);
        }

        if (material.isSpecular() && material.hasSpecularMap()) {
            fL.spec_u = calcStep(factor, fI.spec_u, fI2.spec_u);
            fL.spec_v = calcStep(factor, fI.spec_v, fI2.spec_v);
        }
    }

}



