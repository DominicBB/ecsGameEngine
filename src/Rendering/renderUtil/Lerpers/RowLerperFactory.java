package Rendering.renderUtil.Lerpers;

import Rendering.Materials.Material;
import Rendering.renderUtil.Bitmap;
import util.Mathf.Mathf2D.Vector2D;

public class RowLerperFactory extends BaseLerperFactory {

    private final GouruadLerper gL = new GouruadLerper();
    private final PhongLerper pL = new PhongLerper();
    private final FlatLerper fL = new FlatLerper();


    public RowLerperFactory() {

    }


    public LerpValues createLerpValues(Material material, LerpValues lerpValues, LerpValues l1, LerpValues l2,
                                       float diviser) {
        lerpValues.reset();
        switch (material.shaderType) {
            case GOURUAD:
                lerpValues.setLerper(gouruadLerper(material, l1, l2, diviser));
            case FLAT:
                lerpValues.setLerper(phongLerper(material, l1, l2, diviser));
            case PHONG:
                lerpValues.setLerper(flatLerper(material, l1, l2, diviser));
        }
        lerpValues.setyInt(l1.getyInt());
        lerpValues.setxInt(l1.getxInt());
        return lerpValues;
    }

    private GouruadLerper gouruadLerper(Material material, LerpValues l1, LerpValues l2, float diviser) {

        calcVec3Step(gL.p_proj_step, diviser, l1.getPos_proj(), l2.getPos_proj());
        calcVec3Step(gL.sColorStep, diviser, l1.getSurfaceColor(), l2.getSurfaceColor());
        if (material.hasTexture() && !material.isSpecular()) {
            calcVec2StepFromBitmap(gL.texCoordStep, diviser, l1.getTexCoord(), l2.getTexCoord(), material.getTexture().texture);
            return gL;
        }

        if (!material.hasTexture() && !material.isSpecular()) {
            return gL;
        }

        if (material.isSpecular() && material.hasTexture()) {
            calcVec2StepFromBitmap(gL.texCoordStep, diviser, l1.getTexCoord(), l2.getTexCoord(), material.getTexture().texture);
            calcVec2StepFromBitmap(gL.specCoordStep, diviser, l1.getSpecCoord(), l2.getSpecCoord(), material.getSpecularMap());
            gL.specStep = calcFloatStep(diviser, l1.getSpecularity(), l2.getSpecularity());
            return gL;
        }
        return gL;
    }

    private PhongLerper phongLerper(Material material, LerpValues l1, LerpValues l2, float diviser) {

        //if no normal map
        calcVec3Step(pL.p_proj_step, diviser, l1.getPos_proj(), l2.getPos_proj());
        calcVec3Step(pL.n_ws_step, diviser, l1.getN_ws(), l2.getN_ws());
        calcVec3Step(pL.p_ws_step, diviser, l1.getP_ws(), l2.getP_ws());

        if (material.hasTexture() && !material.isSpecular()) {
            calcVec2StepFromBitmap(pL.texCoordStep, diviser, l1.getTexCoord(), l2.getTexCoord(), material.getTexture().texture);
            return pL;
        }

        if (!material.hasTexture() && !material.isSpecular()) {
            return pL;
        }

        if (material.isSpecular() && material.hasTexture()) {
            calcVec2StepFromBitmap(pL.texCoordStep, diviser, l1.getTexCoord(), l2.getTexCoord(), material.getTexture().texture);
            calcVec2StepFromBitmap(pL.specCoordStep, diviser, l1.getSpecCoord(), l2.getSpecCoord(), material.getSpecularMap());
            pL.specStep = calcFloatStep(diviser, l1.getSpecularity(), l2.getSpecularity());
            return pL;
        }
        return pL;
    }

    private FlatLerper flatLerper(Material material, LerpValues l1, LerpValues l2, float diviser) {
        calcVec3Step(fL.p_proj_step, diviser, l1.getPos_proj(), l2.getPos_proj());
        if (material.hasTexture() && !material.isSpecular()) {
            calcVec2StepFromBitmap(fL.texCoordStep, diviser, l1.getTexCoord(), l2.getTexCoord(), material.getTexture().texture);
            return fL;
        }

        if (!material.hasTexture() && !material.isSpecular()) {
            return fL;
        }

        if (material.isSpecular() && material.hasTexture()) {
            calcVec2StepFromBitmap(fL.texCoordStep, diviser, l1.getTexCoord(), l2.getTexCoord(), material.getTexture().texture);
            calcVec2StepFromBitmap(fL.specCoordStep, diviser, l1.getSpecCoord(), l2.getSpecCoord(), material.getSpecularMap());
            return fL;
        }
        return fL;
    }

    private void calcVec2StepFromBitmap(Vector2D out, float diviser, Vector2D vIn1, Vector2D vIn2,
                                        Bitmap bitmap) {
        calcVec2Step(out, diviser, vIn1, vIn2);
        out.x *= (bitmap.getWidth() - 1);
        out.y *= (bitmap.getWidth() - 1);
    }

}

