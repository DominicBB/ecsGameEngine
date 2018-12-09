package Rendering.shaders;

import Rendering.Materials.Material;
import Rendering.renderUtil.Lerpers.LerpValues;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.shaders.interfaces.IShader;
import util.FloatBuffer;
import util.Mathf.Mathf3D.Vector3D;

import static Rendering.shaders.ShaderUtil.calculateSpecular;
import static Rendering.shaders.ShaderUtil.diffuse;
import static Rendering.shaders.ShaderUtil.ambient;

public class PhongShader implements IShader {
    @Override
    public final VertexOut vert(Vertex vIn, Material material) {

        //transform vertex into mvp
        Vector3D pos_proj = (RenderState.objmvp.multiply4x4(vIn.vec));
        return new VertexOut(pos_proj, vIn.texCoord, vIn.specCoord, 1f, Vector3D.newOnes(), vIn.normal, vIn.vec);
    }

    @Override
    public final Vector3D frag(LerpValues lerpValues, FloatBuffer zBuffer, Material material) {

        int x = lerpValues.getxInt();
        if (!ShaderUtil.zBufferTest(zBuffer, lerpValues.getPos_proj().w, x, lerpValues.getyInt())) {
            return null;
        }

        Vector3D surfaceColor = calculateLighting(lerpValues, material, x);

        Vector3D color;
        if (material.hasTexture()) {

            color = surfaceColor.
                    componentMul(material.getTexture().texture.getPixelColor(
                            (int) lerpValues.getTexCoord().x,
                            (int) lerpValues.getTexCoord().y));

        } else {
            color = material.getColor().componentMul(surfaceColor);
        }
        return color;
    }

    private Vector3D calculateLighting(LerpValues lerpValues, Material material, int x) {

        if (material.isDiffuse()) {
            lerpValues.getSurfaceColor().add(calcDiffuse(lerpValues, material, x));
        }

        if (material.isSpecular()) {
            lerpValues.getSurfaceColor().add(calcSpecular(lerpValues, material));
        }

        if (material.isAmbient()) {
            lerpValues.getSurfaceColor().add(ambient(RenderState.lightingState.ambientColor,
                    material.getAmbientFactor()));
        }
        return lerpValues.getSurfaceColor();
    }

    private Vector3D calcDiffuse(LerpValues lerpValues, Material material, int x) {
        Vector3D n = lerpValues.getN_ws();
        if (material.hasNormalMap()) {
            n = material.getNormalMap().getPixelColor(x, lerpValues.getyInt());
        }

        Vector3D diffuse = diffuse(RenderState.lightingState.lightColor,
                RenderState.lightingState.lightDir,
                n,
                RenderState.lightingState.attenuation,
                material.getDiffuseFactor());

        return diffuse;
    }

    private Vector3D calcSpecular(LerpValues lerpValues, Material material) {
        //Vector3D p_ws = renderer.screenSpaceToWorldSpace(lerpValues.getPos_proj());

        float spec = calculateSpecular(lerpValues.getN_ws(), lerpValues.getP_ws(), material);
        Vector3D specColor;

        if (material.hasSpecularMap()) {

            specColor = material.getSpecularMap().getPixelColor(
                    (int) lerpValues.getSpecCoord().x,
                    (int) lerpValues.getSpecCoord().y);

        } else {
            specColor = material.getDefualtSpecularColor();
        }

        specColor.scale(spec);
        return specColor;
    }

    private static final ShaderType SHADER_TYPE = ShaderType.PHONG;

    @Override
    public ShaderType getShaderType() {
        return SHADER_TYPE;
    }
}
