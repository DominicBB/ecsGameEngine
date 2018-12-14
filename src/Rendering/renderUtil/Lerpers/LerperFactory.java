package Rendering.renderUtil.Lerpers;

import Rendering.Materials.Material;
import Rendering.renderUtil.VertexOut;

public class LerperFactory extends BaseLerperFactory {
    public final int numPrimitveVerticies = 3;

    private int gouruadCount = 0;
    private final GouruadLerper[] gouruadLerpers = new GouruadLerper[numPrimitveVerticies];
    private int phongCount = 0;
    private final PhongLerper[] phongLerpers = new PhongLerper[numPrimitveVerticies];
    private int flatCount = 0;
    private final FlatLerper[] flatLerpers = new FlatLerper[numPrimitveVerticies];


    public LerperFactory() {
        initLerpers();
    }

    private void initLerpers() {
        for (int i = 0; i < numPrimitveVerticies; i++) {
            gouruadLerpers[i] = new GouruadLerper();
            phongLerpers[i] = new PhongLerper();
            flatLerpers[i] = new FlatLerper();
        }
    }


    public Interpolants setLerper(Material material, Interpolants interpolants, VertexOut v1, VertexOut v2,
                                  float diviser) {
        switch (material.getShader().getShaderType()) {
            case GOURUAD:
                interpolants.setLerper(gouruadLerper(material, v1, v2, diviser));
                break;
            case FLAT:
                interpolants.setLerper(flatLerper(material, v1, v2, diviser));
                break;
            case PHONG:
                interpolants.setLerper(phongLerper(material, v1, v2, diviser));
                break;
        }
        return interpolants;
    }

    private GouruadLerper gouruadLerper(Material material, VertexOut v1, VertexOut v2, float diviser) {
        GouruadLerper gL = gouruadLerpers[nextGouruadIndex()];
        gouruadCount++;

        calcVec3Step(gL.p_proj_step, diviser, v1.p_proj, v2.p_proj);
        calcVec3Step(gL.sColorStep, diviser, v1.surfaceColor, v2.surfaceColor);
        gL.invWStep = calcFloatStep(diviser, v1.invW, v2.invW);

        if (material.hasTexture()) {
            calcVec2Step(gL.texCoordStep, diviser, v1.texCoord, v2.texCoord);
        }


        if (material.isSpecular()) {
            if (material.hasSpecularMap())
                calcVec2Step(gL.specCoordStep, diviser, v1.specCoord, v2.specCoord);
            gL.specStep = calcFloatStep(diviser, v1.spec, v2.spec);
        }
        return gL;
    }

    private PhongLerper phongLerper(Material material, VertexOut v1, VertexOut v2, float diviser) {
        PhongLerper pL = phongLerpers[nextPhongIndex()];
        phongCount++;

        //TODO:if no normal map

        calcVec3Step(pL.p_proj_step, diviser, v1.p_proj, v2.p_proj);
        calcVec3Step(pL.n_ws_step, diviser, v1.n_ws, v2.n_ws);
        calcVec3Step(pL.p_ws_step, diviser, v1.p_ws, v2.p_ws);
        pL.invZStep = calcFloatStep(diviser, v1.invW, v2.invW);

        if (material.hasTexture()) {
            calcVec2Step(pL.texCoordStep, diviser, v1.texCoord, v2.texCoord);
        }

        if (material.isSpecular()) {
            if (material.hasSpecularMap())
                calcVec2Step(pL.specCoordStep, diviser, v1.specCoord, v2.specCoord);
            pL.specStep = calcFloatStep(diviser, v1.spec, v2.spec);
        }
        return pL;
    }

    private FlatLerper flatLerper(Material material, VertexOut v1, VertexOut v2, float diviser) {
        FlatLerper fL = flatLerpers[nextFlatIndex()];
        flatCount++;

        calcVec3Step(fL.p_proj_step, diviser, v1.p_proj, v2.p_proj);
        fL.invZStep = calcFloatStep(diviser, v1.invW, v2.invW);

        if (material.hasTexture()) {
            calcVec2Step(fL.texCoordStep, diviser, v1.texCoord, v2.texCoord);
        }

        if (material.isSpecular()) {
            if (material.hasSpecularMap())
                calcVec2Step(fL.specCoordStep, diviser, v1.specCoord, v2.specCoord);
            fL.specStep = calcFloatStep(diviser, v1.spec, v2.spec);

        }
        return fL;
    }

    private int nextGouruadIndex() {
        return (gouruadCount >= 3) ? (gouruadCount = 0) : gouruadCount;
    }

    private int nextPhongIndex() {
        return (phongCount >= 3) ? (phongCount = 0) : phongCount;
    }

    private int nextFlatIndex() {
        return (flatCount >= 3) ? (flatCount = 0) : flatCount;
    }

}
