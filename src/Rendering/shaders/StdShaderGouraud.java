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

public class StdShaderGouraud implements IShader {

    @Override
    public final VertexOut vert(Vertex vIn, Material material) {

        //transform vertex into mvp
        Vector3D pos_proj = (RenderState.objmvp.multiply4x4(vIn.vec));
        Vector3D sColor = Vector3D.newZeros();

        if (material.isDiffuse()) {

            Vector3D diffuse = diffuse(RenderState.lightingState.lightColor,
                    RenderState.lightingState.lightDir,
                    vIn.normal,
                    RenderState.lightingState.attenuation,
                    material.getDiffuseFactor());

            sColor.add(diffuse);
        }

        float spec = 1f;
        if (material.isSpecular()) {
            spec = calculateSpecular(vIn.normal, vIn.vec, material);
            if (!material.hasSpecularMap()) {
                sColor.add(material.getDefualtSpecularColor().mul(spec));
            }
        }

        if (material.isAmbient()) {
            sColor.add(ambient(RenderState.lightingState.ambientColor, material.getAmbientFactor()));
        }

        return new VertexOut(pos_proj, vIn.texCoord, vIn.specCoord, spec, sColor, vIn.normal, vIn.vec);
    }

    @Override
    public final Vector3D frag(LerpValues lerpValues, FloatBuffer zBuffer, Material material) {

        if (!ShaderUtil.zBufferTest(zBuffer, lerpValues.getPos_proj().w, lerpValues.getxInt(), lerpValues.getyInt()))
            return null;

        if (material.isSpecular()) {
            lerpValues.getSurfaceColor().add(calcSpecular(lerpValues, material));
        }

        Vector3D color;
        if (material.hasTexture()) {
            color = calcTextureColor(lerpValues, material);
        } else {
            color = lerpValues.getSurfaceColor().componentMul(material.getColor());
        }

        return color;
    }

    private Vector3D calcSpecular(LerpValues lerpValues, Material material) {
        if (material.hasSpecularMap()) {

            Vector3D specColor = material.getSpecularMap().getPixelColor((int) lerpValues.getSpecCoord().x,
                    (int) lerpValues.getSpecCoord().y);

            return specColor.mul(lerpValues.getSpecularity());
        } else {
            return material.getDefualtSpecularColor().mul(lerpValues.getSpecularity());
        }
    }

    private Vector3D calcTextureColor(LerpValues lerpValues, Material material) {
        lerpValues.getSurfaceColor().
                componentMul(material.getTexture().texture.getPixelColor((int) lerpValues.getTexCoord().x,
                        (int) lerpValues.getTexCoord().y));
        return lerpValues.getSurfaceColor();
    }

    private static final ShaderType SHADER_TYPE = ShaderType.PHONG;

    @Override
    public ShaderType getShaderType() {
        return SHADER_TYPE;
    }
}
