package Rendering.shaders;

import Rendering.Materials.Material;
import Rendering.renderUtil.Lerpers.LerpValues;
import Rendering.renderUtil.RenderContext;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.shaders.interfaces.IShader;
import core.Window;
import util.Mathf.Mathf3D.Vector3D;

import static Rendering.shaders.ShaderUtil.calculateSpecular;
import static Rendering.shaders.ShaderUtil.diffuse;
import static Rendering.shaders.ShaderUtil.ambient;

public class StandardShaderPhong implements IShader {
    @Override
    public final VertexOut vert(Vertex vIn, RenderContext renderContext, Material material) {

        //transform vertex into MVP
        Vector3D pos_proj = (renderContext.MVP.multiply4x4(vIn.vec));
        return new VertexOut(pos_proj, vIn.texCoord, vIn.specCoord, 1f, Vector3D.newOnes(), vIn.normal, vIn.vec);
    }

    @Override
    public final Vector3D frag(LerpValues lerpValues, RenderContext renderContext, Material material) {

        int x = (int) lerpValues.getPos_proj().x;
        if (!ShaderUtil.zBufferTest(renderContext.getzBuffer(), lerpValues.getPos_proj().w,
                x, lerpValues.getyInt(), Window.defaultWidth)) {
            return null;
        }

        Vector3D surfaceColor = calculateLighting(lerpValues, renderContext, material, x);

        Vector3D color;
        if (material.hasTexture()) {

            color = surfaceColor.
                    componentMul(material.getTexture().texture.getPixel(
                            (int) lerpValues.getTexCoord().x,
                            (int) lerpValues.getTexCoord().y));

        } else {
            color = material.getColor().componentMul(surfaceColor);
        }
        return color;
    }

    private Vector3D calculateLighting(LerpValues lerpValues, RenderContext renderContext, Material material, int x) {

        if (material.isDiffuse()) {
            lerpValues.getSurfaceColor().add(calcDiffuse(lerpValues, renderContext, material, x));
        }

        if (material.isSpecular()) {
            lerpValues.getSurfaceColor().add(calcSpecular(lerpValues, renderContext, material));
        }

        if (material.isAmbient()) {
            lerpValues.getSurfaceColor().add(ambient(renderContext.getLightingState().ambientColor,
                    material.getAmbientFactor()));
        }
        return lerpValues.getSurfaceColor();
    }

    private Vector3D calcDiffuse(LerpValues lerpValues, RenderContext renderContext, Material material, int x) {
        Vector3D n = lerpValues.getN_ws();
        if (material.hasNormalMap()) {
            n = material.getNormalMap().getPixel(x, lerpValues.getyInt());
        }

        Vector3D diffuse = diffuse(renderContext.getLightingState().lightColor,
                renderContext.getLightingState().lightDir,
                n,
                renderContext.getLightingState().attenuation,
                material.getDiffuseFactor());

        return diffuse;
    }

    private Vector3D calcSpecular(LerpValues lerpValues, RenderContext renderContext, Material material) {
        //Vector3D p_ws = renderContext.screenSpaceToWorldSpace(lerpValues.getPos_proj());

        float spec = calculateSpecular(lerpValues.getN_ws(), lerpValues.getP_ws(), renderContext, material);
        Vector3D specColor;

        if (material.hasSpecularMap()) {

            specColor = material.getSpecularMap().getPixel(
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
