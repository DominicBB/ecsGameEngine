package Rendering.renderUtil.Lerpers;

import Rendering.Materials.Material;

public class RowLerperFactory extends BaseLerperFactory {

    private final GouruadLerper gL = new GouruadLerper();
    private final PhongLerper pL = new PhongLerper();
    private final FlatLerper fL = new FlatLerper();

    public Interpolants setLerper(Material material, Interpolants interpolants, Interpolants l1, Interpolants l2,
                                  float diviser) {


        calculateLerperValues(material, interpolants, l1, l2, diviser);

        return interpolants;
    }

    private void calculateLerperValues(Material material, Interpolants interpolants, Interpolants l1, Interpolants l2,
                                       float diviser) {
        switch (material.getShader().getShaderType()) {
            case GOURUAD:
                interpolants.setLerper(gouruadLerper(material, l1, l2, diviser));
                break;
            case FLAT:
                interpolants.setLerper(flatLerper(material, l1, l2, diviser));
                break;
            case PHONG:
                interpolants.setLerper(phongLerper(material, l1, l2, diviser));
                break;
        }
    }

    private GouruadLerper gouruadLerper(Material material, Interpolants l1, Interpolants l2, float diviser) {

       // calcVec3Step(gL.p_proj_step, diviser, l1.getP_proj(), l2.getP_proj());
        calcVec3Step(gL.sColorStep, diviser, l1.getSurfaceColor(), l2.getSurfaceColor());
        gL.invWStep = calcFloatStep(diviser, l1.getInvW(), l2.getInvW());

        if (material.hasTexture()) {
            calcVec2Step(gL.texCoordStep, diviser, l1.getTexCoord(), l2.getTexCoord());
        }

        if (material.isSpecular()) {
            if (material.hasSpecularMap())
                calcVec2Step(gL.specCoordStep, diviser, l1.getSpecCoord(), l2.getSpecCoord());
            gL.specStep = calcFloatStep(diviser, l1.getSpecularity(), l2.getSpecularity());
        }

        return gL;
    }

    private PhongLerper phongLerper(Material material, Interpolants l1, Interpolants l2, float diviser) {

        //if no normal map
        calcVec3Step(pL.p_proj_step, diviser, l1.getP_proj(), l2.getP_proj());
        calcVec3Step(pL.n_ws_step, diviser, l1.getN_ws(), l2.getN_ws());
        calcVec3Step(pL.p_ws_step, diviser, l1.getP_ws(), l2.getP_ws());
        pL.invZStep = calcFloatStep(diviser, l1.getInvW(), l2.getInvW());

        if (material.hasTexture()) {
            calcVec2Step(pL.texCoordStep, diviser, l1.getTexCoord(), l2.getTexCoord());
        }

        if (material.isSpecular()) {
            if (material.hasSpecularMap())
                calcVec2Step(pL.specCoordStep, diviser, l1.getSpecCoord(), l2.getSpecCoord());
            pL.specStep = calcFloatStep(diviser, l1.getSpecularity(), l2.getSpecularity());

        }
        return pL;
    }

    private FlatLerper flatLerper(Material material, Interpolants l1, Interpolants l2, float diviser) {
        calcVec3Step(fL.p_proj_step, diviser, l1.getP_proj(), l2.getP_proj());
        fL.invZStep = calcFloatStep(diviser, l1.getInvW(), l2.getInvW());

        if (material.hasTexture()) {
            calcVec2Step(fL.texCoordStep, diviser, l1.getTexCoord(), l2.getTexCoord());
        }

        if (material.isSpecular()) {
            if (material.hasSpecularMap())
                calcVec2Step(fL.specCoordStep, diviser, l1.getSpecCoord(), l2.getSpecCoord());
            fL.specStep = calcFloatStep(diviser, l1.getSpecularity(), l2.getSpecularity());

        }
        return fL;
    }

}

