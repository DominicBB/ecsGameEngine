package Rendering.renderUtil.interpolation;

import Rendering.Materials.Material;
import Rendering.renderUtil.VOutfi;
import Rendering.renderUtil.interpolation.flat.FlatLerper_E;
import Rendering.renderUtil.interpolation.gouruad.GouruadLerper_E;
import Rendering.renderUtil.interpolation.phong.PhongLerper_E;


public class LerperFactory extends BaseLerperFactory {

    public static void gouruadLerper(Material material, GouruadLerper_E gL, VOutfi v1, VOutfi v2,
                                     int factor) {
        gL.x = calcStep(factor, v1.p_proj.x, v2.p_proj.x);

        gL.z = calcStep_shiftAfter(factor, v1.p_proj.z, v2.p_proj.z);

        gL.color_a = calcStep(factor, v1.surfaceColor.w, v2.surfaceColor.w, colorStep_shift);
        gL.color_r = calcStep(factor, v1.surfaceColor.x, v2.surfaceColor.x, colorStep_shift);
        gL.color_g = calcStep(factor, v1.surfaceColor.y, v2.surfaceColor.y, colorStep_shift);
        gL.color_b = calcStep(factor, v1.surfaceColor.z, v2.surfaceColor.z, colorStep_shift);

        gL.invW = calcStep_shiftAfter(factor, v1.invW, v2.invW);

        if (material.hasTexture()) {
            gL.tex_u = calcStep(factor, v1.texCoord.x, v2.texCoord.x);
            gL.tex_v = calcStep(factor, v1.texCoord.y, v2.texCoord.y);
        }

        if (material.isSpecular()) {
            if (material.hasSpecularMap()) {
                gL.spec_u = calcStep(factor, v1.specCoord.x, v2.specCoord.x);
                gL.spec_v = calcStep(factor, v1.specCoord.y, v2.specCoord.y);
            }
            gL.specularity = calcStep(factor, v1.spec, v2.spec);
        }
    }

    public static void phongLerper(Material material, PhongLerper_E pL, VOutfi v1, VOutfi v2,
                                   int factor) {
        pL.n_ws_x = calcStep(factor, v1.n_ws.x, v2.n_ws.x);
        pL.n_ws_y = calcStep(factor, v1.n_ws.y, v2.n_ws.y);
        pL.n_ws_z = calcStep(factor, v1.n_ws.z, v2.n_ws.z);

        pL.p_ws_x = calcStep(factor, v1.p_ws.x, v2.p_ws.x);
        pL.p_ws_y = calcStep(factor, v1.p_ws.y, v2.p_ws.y);
        pL.p_ws_z = calcStep(factor, v1.p_ws.z, v2.p_ws.z);


        pL.x = calcStep(factor, v1.p_proj.x, v2.p_proj.x);
        pL.z = calcStep_shiftAfter(factor, v1.p_proj.z, v2.p_proj.z);
        pL.invW = calcStep_shiftAfter(factor, v1.invW, v2.invW);

        if (material.hasTexture()) {
            pL.tex_u = calcStep(factor, v1.texCoord.x, v2.texCoord.x);
            pL.tex_v = calcStep(factor, v1.texCoord.y, v2.texCoord.y);
        }

        if (material.isSpecular() && material.hasSpecularMap()) {
            pL.spec_u = calcStep(factor, v1.specCoord.x, v2.specCoord.x);
            pL.spec_v = calcStep(factor, v1.specCoord.y, v2.specCoord.y);
        }

    }

    public static void flatLerper(Material material, FlatLerper_E fL, VOutfi v1, VOutfi v2,
                                  int factor) {
        fL.x = calcStep(factor, v1.p_proj.x, v2.p_proj.x);
        fL.z = calcStep_shiftAfter(factor, v1.p_proj.z, v2.p_proj.z);
        fL.invW = calcStep_shiftAfter(factor, v1.invW, v2.invW);
        if (fL.invW < 0) {
            fL.invW = fL.invW;
        }
        if (material.hasTexture()) {
            fL.tex_u = calcStep(factor, v1.texCoord.x, v2.texCoord.x);
            fL.tex_v = calcStep(factor, v1.texCoord.y, v2.texCoord.y);
        }

        if (material.isSpecular() && material.hasSpecularMap()) {
            fL.spec_u = calcStep(factor, v1.specCoord.x, v2.specCoord.x);
            fL.spec_v = calcStep(factor, v1.specCoord.y, v2.specCoord.y);
        }
    }

}
