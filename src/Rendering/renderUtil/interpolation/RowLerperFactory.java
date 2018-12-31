package Rendering.renderUtil.interpolation;

import Rendering.Materials.Material;

public class RowLerperFactory extends BaseLerperFactory {

    private final GouruadLerper gL = new GouruadLerper();
    private final PhongLerper pL = new PhongLerper();
    private final FlatLerper fL = new FlatLerper();

    public void setLerper(Material material, Interpolants interpolants, Interpolants l1, Interpolants l2,
                                  float factor) {

        switch (material.getShader().getShaderType()) {
            case GOURUAD:
                interpolants.setLerper(gouruadLerper(material, l1, l2, factor));
                break;
            case FLAT:
                interpolants.setLerper(flatLerper(material, l1, l2, factor));
                break;
            case PHONG:
                interpolants.setLerper(phongLerper(material, l1, l2, factor));
                break;
        }
    }


    private GouruadLerper gouruadLerper(Material material, Interpolants l1, Interpolants l2, float factor) {

        gL.p_proj_step.z = calcFloatStep(factor, l1.p_proj.z, l2.p_proj.z);
        calcVec3Step(gL.sColorStep, factor, l1.surfaceColor, l2.surfaceColor);
        gL.invWStep = calcFloatStep(factor, l1.invW, l2.invW);

        if (material.hasTexture()) {
            calcVec2Step(gL.texCoordStep, factor, l1.texCoord, l2.texCoord);
        }

        if (material.isSpecular()) {
            if (material.hasSpecularMap())
                calcVec2Step(gL.specCoordStep, factor, l1.specCoord, l2.specCoord);
            gL.specStep = calcFloatStep(factor, l1.specularity, l2.specularity);
        }

        return gL;
    }

    private PhongLerper phongLerper(Material material, Interpolants l1, Interpolants l2, float factor) {

        //if no normal map
        calcVec3Step(pL.p_proj_step, factor, l1.p_proj, l2.p_proj);
        calcVec3Step(pL.n_ws_step, factor, l1.n_ws, l2.n_ws);
        calcVec3Step(pL.p_ws_step, factor, l1.p_ws, l2.p_ws);
        pL.invWStep = calcFloatStep(factor, l1.invW, l2.invW);

        if (material.hasTexture()) {
            calcVec2Step(pL.texCoordStep, factor, l1.texCoord, l2.texCoord);
        }

        if (material.isSpecular()) {
            if (material.hasSpecularMap())
                calcVec2Step(pL.specCoordStep, factor, l1.specCoord, l2.specCoord);
            pL.specStep = calcFloatStep(factor, l1.specularity, l2.specularity);

        }
        return pL;
    }

    private FlatLerper flatLerper(Material material, Interpolants l1, Interpolants l2, float factor) {
        fL.p_proj_step.z = calcFloatStep(factor, l1.p_proj.z, l2.p_proj.z);
        fL.invWStep = calcFloatStep(factor, l1.invW, l2.invW);

        if (material.hasTexture()) {
            calcVec2Step(fL.texCoordStep, factor, l1.texCoord, l2.texCoord);
        }
        if (material.hasSpecularMap())
            calcVec2Step(fL.specCoordStep, factor, l1.specCoord, l2.specCoord);

        return fL;
    }

}

