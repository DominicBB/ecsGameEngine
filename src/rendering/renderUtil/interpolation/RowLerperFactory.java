package rendering.renderUtil.interpolation;

import rendering.materials.Material;
import rendering.renderUtil.interpolation.flat.FlatInterpolants;
import rendering.renderUtil.interpolation.flat.FlatLerper_R;
import rendering.renderUtil.interpolation.gouruad.GouruadInterpolants;
import rendering.renderUtil.interpolation.gouruad.GouruadLerper_R;
import rendering.renderUtil.interpolation.phong.PhongInterpolants;
import rendering.renderUtil.interpolation.phong.PhongLerper_R;

public class RowLerperFactory extends BaseLerperFactory {

    public static void gouruadLerper(GouruadLerper_R gL, Material material, GouruadInterpolants gI, GouruadInterpolants gI2,
                                     float factor) {
        gL.z = calcFloatStep(factor, gI.z, gI2.z);

        gL.color_a = calcFloatStep(factor, gI.color_a, gI2.color_a);
        gL.color_r = calcFloatStep(factor, gI.color_r, gI2.color_r);
        gL.color_g = calcFloatStep(factor, gI.color_g, gI2.color_g);
        gL.color_b = calcFloatStep(factor, gI.color_b, gI2.color_b);

        gL.invW = calcFloatStep(factor, gI.invW, gI2.invW);

        if (material.hasTexture()) {
            gL.tex_u = calcFloatStep(factor, gI.tex_u, gI2.tex_u);
            gL.tex_v = calcFloatStep(factor, gI.tex_v, gI2.tex_v);
        }

        if (material.isSpecular()) {
            if (material.hasSpecularMap()) {
                gL.spec_u = calcFloatStep(factor, gI.spec_u, gI2.spec_u);
                gL.spec_v = calcFloatStep(factor, gI.spec_v, gI2.spec_v);
            }
            gL.specularity = calcFloatStep(factor, gI.specularity, gI2.specularity);
        }
    }

    public static void phongLerper(PhongLerper_R pL, Material material, PhongInterpolants pI, PhongInterpolants pI2,
                                   float factor) {
        pL.n_ws_x = calcFloatStep(factor, pI.n_ws_x, pI2.n_ws_x);
        pL.n_ws_y = calcFloatStep(factor, pI.n_ws_y, pI2.n_ws_y);
        pL.n_ws_z = calcFloatStep(factor, pI.n_ws_z, pI2.n_ws_z);

        pL.p_ws_x = calcFloatStep(factor, pI.p_ws_x, pI2.p_ws_x);
        pL.p_ws_y = calcFloatStep(factor, pI.p_ws_y, pI2.p_ws_y);
        pL.p_ws_z = calcFloatStep(factor, pI.p_ws_z, pI2.p_ws_z);

        pL.z = calcFloatStep(factor, pI.z, pI2.z);
        pL.invW = calcFloatStep(factor, pI.invW, pI2.invW);

        if (material.hasTexture()) {
            pL.tex_u = calcFloatStep(factor, pI.tex_u, pI2.tex_u);
            pL.tex_v = calcFloatStep(factor, pI.tex_v, pI2.tex_v);
        }

        if (material.isSpecular() && material.hasSpecularMap()) {
            pL.spec_u = calcFloatStep(factor, pI.spec_u, pI2.spec_u);
            pL.spec_v = calcFloatStep(factor, pI.spec_v, pI2.spec_v);
        }
    }

    public static void flatLerper(FlatLerper_R fL, Material material, FlatInterpolants fI, FlatInterpolants fI2,
                                  float factor) {
        fL.z = calcFloatStep(factor, fI.z, fI2.z);
        fL.invW = calcFloatStep(factor, fI.invW, fI2.invW);

        if (material.hasTexture()) {
            fL.tex_u = calcFloatStep(factor, fI.tex_u, fI2.tex_u);
            fL.tex_v = calcFloatStep(factor, fI.tex_v, fI2.tex_v);
        }

        if (material.isSpecular() && material.hasSpecularMap()) {
            fL.spec_u = calcFloatStep(factor, fI.spec_u, fI2.spec_u);
            fL.spec_v = calcFloatStep(factor, fI.spec_v, fI2.spec_v);
        }
    }

}



