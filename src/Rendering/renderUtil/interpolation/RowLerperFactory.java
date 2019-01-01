package Rendering.renderUtil.interpolation;

import Rendering.Materials.Material;
import Rendering.renderUtil.interpolation.flat.FlatInterpolants;
import Rendering.renderUtil.interpolation.flat.FlatLerper_R;
import Rendering.renderUtil.interpolation.gouruad.GouruadInterpolants;
import Rendering.renderUtil.interpolation.gouruad.GouruadLerper_R;
import Rendering.renderUtil.interpolation.phong.PhongInterpolants;
import Rendering.renderUtil.interpolation.phong.PhongLerper_R;

public class RowLerperFactory extends BaseLerperFactory {

    public static void gouruadLerper(GouruadLerper_R gL, Material material, GouruadInterpolants l1, GouruadInterpolants l2,
                              float factor) {

        gL.z = calcFloatStep(factor, l1.z, l2.z);

        gL.color_a = calcFloatStep(factor, l1.color_a, l2.color_a);
        gL.color_r = calcFloatStep(factor, l1.color_r, l2.color_r);
        gL.color_g = calcFloatStep(factor, l1.color_g, l2.color_g);
        gL.color_b = calcFloatStep(factor, l1.color_b, l2.color_b);

        gL.invW = calcFloatStep(factor, l1.invW, l2.invW);

        if (material.hasTexture()) {
            gL.tex_u = calcFloatStep(factor, l1.tex_u, l2.tex_u);
            gL.tex_v = calcFloatStep(factor, l1.tex_v, l2.tex_v);
        }

        if (material.isSpecular()) {
            if (material.hasSpecularMap()) {
                gL.spec_u = calcFloatStep(factor, l1.spec_u, l2.spec_u);
                gL.spec_v = calcFloatStep(factor, l1.spec_v, l2.spec_v);
            }
            gL.specularity = calcFloatStep(factor, l1.specularity, l2.specularity);
        }

    }

    public static void phongLerper(PhongLerper_R pL, Material material, PhongInterpolants l1, PhongInterpolants l2, float factor) {

        //if no normal map
        pL.n_ws_x = calcFloatStep(factor, l1.n_ws_x, l2.n_ws_x);
        pL.n_ws_y = calcFloatStep(factor, l1.n_ws_y, l2.n_ws_y);
        pL.n_ws_z = calcFloatStep(factor, l1.n_ws_z, l2.n_ws_z);

        pL.p_ws_x = calcFloatStep(factor, l1.p_ws_x, l2.p_ws_x);
        pL.p_ws_y = calcFloatStep(factor, l1.p_ws_y, l2.p_ws_y);
        pL.p_ws_z = calcFloatStep(factor, l1.p_ws_z, l2.p_ws_z);

        pL.z = calcFloatStep(factor, l1.z, l2.z);
        pL.invW = calcFloatStep(factor, l1.invW, l2.invW);

        if (material.hasTexture()) {
            pL.tex_u = calcFloatStep(factor, l1.tex_u, l2.tex_u);
            pL.tex_v = calcFloatStep(factor, l1.tex_v, l2.tex_v);
        }

        if (material.isSpecular() && material.hasSpecularMap()) {
            pL.spec_u = calcFloatStep(factor, l1.spec_u, l2.spec_u);
            pL.spec_v = calcFloatStep(factor, l1.spec_v, l2.spec_v);

        }
    }

    public static void flatLerper(FlatLerper_R fL, Material material, FlatInterpolants l1, FlatInterpolants l2, float factor) {
        fL.z = calcFloatStep(factor, l1.z, l2.z);
        fL.invW = calcFloatStep(factor, l1.invW, l2.invW);

        if (material.hasTexture()) {
            fL.tex_u = calcFloatStep(factor, l1.tex_u, l2.tex_u);
            fL.tex_v = calcFloatStep(factor, l1.tex_v, l2.tex_v);
        }

        if (material.isSpecular() && material.hasSpecularMap()) {
            fL.spec_u = calcFloatStep(factor, l1.spec_u, l2.spec_u);
            fL.spec_v = calcFloatStep(factor, l1.spec_v, l2.spec_v);
        }
    }

}



