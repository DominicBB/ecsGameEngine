package Rendering.shaders;

import Rendering.Materials.Material;
import Rendering.renderUtil.Lerpers.Interpolants;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.shaders.interfaces.IShader;
import util.FloatBuffer;
import util.Mathf.Mathf3D.Vector3D;

import static Rendering.shaders.ShaderUtil.*;

public class PhongShader implements IShader {
    @Override
    public final VertexOut vert(Vertex vIn, Material material) {

        //transform vertex into mvp
        Vector3D p_proj = (RenderState.mvp.multiply4x4(vIn.vec));
        return new VertexOut(p_proj,
                vIn.texCoord, vIn.specCoord,
                1f,
                Vector3D.newZeros(),
                RenderState.transform.getRotation().rotate(vIn.normal),
                RenderState.world.multiply4x4(vIn.vec),
                1f / p_proj.w);
    }

    @Override
    public final Vector3D frag(Interpolants interpolants, FloatBuffer zBuffer, Material material) {

        int x = interpolants.getxInt();
        if (!ShaderUtil.zBufferTest(zBuffer, interpolants.getP_proj().z, x, interpolants.getyInt())) {
            return null;
        }

        Vector3D surfaceColor = calculateLighting(interpolants, material, x);

        Vector3D color;
        if (material.hasTexture()) {

            color = surfaceColor.
                    componentMul(material.getTexture().texture.getPixelColor(
                            (int) interpolants.getTexCoord().x,
                            (int) interpolants.getTexCoord().y));

        } else {
            color = material.getColor().componentMul(surfaceColor);
        }
        return color;
    }

    private Vector3D calculateLighting(Interpolants interpolants, Material material, int x) {

        if (material.isDiffuse()) {
            interpolants.getSurfaceColor().add(calcDiffuse(interpolants, material, x));
        }

        if (material.isSpecular()) {
            interpolants.getSurfaceColor().add(calcSpecular(interpolants, material));
        }

        if (material.isAmbient()) {
            interpolants.getSurfaceColor().add(ambient(RenderState.lightingState.ambientColor,
                    material.getAmbientFactor()));
        }
        return interpolants.getSurfaceColor();
    }

    private Vector3D calcDiffuse(Interpolants interpolants, Material material, int x) {
        Vector3D n = interpolants.getN_ws();
        if (material.hasNormalMap()) {
            n = material.getNormalMap().getPixelColor(x, interpolants.getyInt());
        }

        Vector3D diffuse = diffuse(RenderState.lightingState.lightColor,
                RenderState.lightingState.lightDir,
                n,
                RenderState.lightingState.attenuation,
                material.getDiffuseFactor());

        return diffuse;
    }

    private Vector3D calcSpecular(Interpolants interpolants, Material material) {
        //Vector3D p_ws = renderer.screenSpaceToWorldSpace(interpolants.getP_proj());

        float spec = calculateSpecular(interpolants.getN_ws(), interpolants.getP_ws(), material);
        Vector3D specColor;

        if (material.hasSpecularMap()) {
            specColor = perspectiveCorrectBitmap(interpolants.getSpecCoord(),material.getSpecularMap(), 0f);

        } else {
            specColor = material.getDefualtSpecularColor();
        }

        specColor.scale(spec);
        return specColor;
    }


    @Override
    public ShaderType getShaderType() {
        return ShaderType.PHONG;
    }
}
