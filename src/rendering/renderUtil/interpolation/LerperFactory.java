package rendering.renderUtil.interpolation;

import rendering.materials.Material;
import rendering.renderUtil.VertexOut;
import rendering.renderUtil.interpolation.flat.FlatLerper_E;
import rendering.renderUtil.interpolation.gouruad.GouruadLerper_E;
import rendering.renderUtil.interpolation.phong.PhongLerper_E;


public class LerperFactory extends BaseLerperFactory {

    public static void gouruadLerper(Material material, GouruadLerper_E gL, VertexOut v1, VertexOut v2,
                                     float factor) {
        gL.x = calcFloatStep(factor, v1.p_proj.x, v2.p_proj.x);

        gL.z = calcFloatStep(factor, v1.p_proj.z, v2.p_proj.z);

        gL.color_a = calcFloatStep(factor, v1.surfaceColor.w, v2.surfaceColor.w);
        gL.color_r = calcFloatStep(factor, v1.surfaceColor.x, v2.surfaceColor.x);
        gL.color_g = calcFloatStep(factor, v1.surfaceColor.y, v2.surfaceColor.y);
        gL.color_b = calcFloatStep(factor, v1.surfaceColor.z, v2.surfaceColor.z);

        gL.invW = calcFloatStep(factor, v1.invW, v2.invW);

        if (material.hasTexture()) {
            gL.tex_u = calcFloatStep(factor, v1.texCoord.x, v2.texCoord.x);
            gL.tex_v = calcFloatStep(factor, v1.texCoord.y, v2.texCoord.y);
        }

        if (material.isSpecular()) {
            if (material.hasSpecularMap()) {
                gL.spec_u = calcFloatStep(factor, v1.specCoord.x, v2.specCoord.x);
                gL.spec_v = calcFloatStep(factor, v1.specCoord.y, v2.specCoord.y);
            }
            gL.specularity = calcFloatStep(factor, v1.spec, v2.spec);
        }
    }

    public static void phongLerper(Material material, PhongLerper_E pL, VertexOut v1, VertexOut v2,
                                   float factor) {
        pL.n_ws_x = calcFloatStep(factor, v1.n_ws.x, v2.n_ws.x);
        pL.n_ws_y = calcFloatStep(factor, v1.n_ws.y, v2.n_ws.y);
        pL.n_ws_z = calcFloatStep(factor, v1.n_ws.z, v2.n_ws.z);

        pL.p_ws_x = calcFloatStep(factor, v1.p_ws.x, v2.p_ws.x);
        pL.p_ws_y = calcFloatStep(factor, v1.p_ws.y, v2.p_ws.y);
        pL.p_ws_z = calcFloatStep(factor, v1.p_ws.z, v2.p_ws.z);


        pL.x = calcFloatStep(factor, v1.p_proj.x, v2.p_proj.x);
        pL.z = calcFloatStep(factor, v1.p_proj.z, v2.p_proj.z);
        pL.invW = calcFloatStep(factor, v1.invW, v2.invW);

        if (material.hasTexture()) {
            pL.tex_u = calcFloatStep(factor, v1.texCoord.x, v2.texCoord.x);
            pL.tex_v = calcFloatStep(factor, v1.texCoord.y, v2.texCoord.y);
        }

        if (material.isSpecular() && material.hasSpecularMap()) {
            pL.spec_u = calcFloatStep(factor, v1.specCoord.x, v2.specCoord.x);
            pL.spec_v = calcFloatStep(factor, v1.specCoord.y, v2.specCoord.y);
        }

    }

    public static void flatLerper(Material material, FlatLerper_E fL, VertexOut v1, VertexOut v2,
                                  float factor) {
        fL.x = calcFloatStep(factor, v1.p_proj.x, v2.p_proj.x);
        fL.z = calcFloatStep(factor, v1.p_proj.z, v2.p_proj.z);
        fL.invW = calcFloatStep(factor, v1.invW, v2.invW);

        if (material.hasTexture()) {
            fL.tex_u = calcFloatStep(factor, v1.texCoord.x, v2.texCoord.x);
            fL.tex_v = calcFloatStep(factor, v1.texCoord.y, v2.texCoord.y);
        }

        if (material.isSpecular() && material.hasSpecularMap()) {
            fL.spec_u = calcFloatStep(factor, v1.specCoord.x, v2.specCoord.x);
            fL.spec_v = calcFloatStep(factor, v1.specCoord.y, v2.specCoord.y);
        }
    }

}