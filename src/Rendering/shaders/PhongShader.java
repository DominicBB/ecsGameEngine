package Rendering.shaders;

import Rendering.Materials.Material;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.renderUtil.interpolation.Interpolants;
import Rendering.shaders.interfaces.IShader;
import util.Mathf.Mathf3D.Vector3D;

import static Rendering.shaders.ShaderUtil.*;

public class PhongShader implements IShader {
     PhongShader(){}
    @Override
    public final VertexOut vert(Vertex vIn, Material material) {
        return transformVIn(vIn, material);
    }

    @Override
    public void vertNonAlloc(Vertex vIn, Material material, VertexOut out) {
        setVOut(vIn, material, out);
    }

    @Override
    public final Vector3D frag(Interpolants interpolants, Material material) {

        if (!ShaderUtil.zBufferTest(RenderState.zBuffer, interpolants.p_proj.z, interpolants.xInt, interpolants.yInt)) {
            return null;
        }

        float w = 1f / interpolants.invW;

        Vector3D surfaceColor = calculateLighting(interpolants, material, interpolants.xInt);

        Vector3D color;
        if (material.hasTexture()) {
            color = surfaceColor.
                    componentMul(material.getTexture().texture.getPixel(
                            (int) interpolants.texCoord.x,
                            (int) interpolants.texCoord.y));

        } else {
            color = material.getColor().componentMul(surfaceColor);
        }
        return color;
    }

    @Override
    public boolean fragNonAlloc(Interpolants vertex, Material material, Vector3D outColor, Vector3D util) {
        return false;
    }

    private Vector3D calculateLighting(Interpolants interpolants, Material material, int x) {

        if (material.isDiffuse()) {
            interpolants.surfaceColor.add(calcDiffuse(interpolants, material, x));
        }

        if (material.isSpecular()) {
            interpolants.surfaceColor.add(calcSpecular(interpolants, material));
        }

        if (material.isAmbient()) {
            interpolants.surfaceColor.add(ambient(RenderState.lightingState.ambientColor,
                    material.getAmbientFactor()));
        }
        return interpolants.surfaceColor;
    }

    private Vector3D calcDiffuse(Interpolants interpolants, Material material, int x) {
        Vector3D n = interpolants.n_ws;
        if (material.hasNormalMap()) {
            n = material.getNormalMap().getPixel(x, interpolants.yInt);
        }
        return diffuse(n, material);
    }

    private Vector3D calcSpecular(Interpolants interpolants, Material material) {
        //Vector3D p_ws = renderer.screenSpaceToWorldSpace(interpolants.getP_proj());

        float spec = calculateSpecular(interpolants.n_ws, interpolants.p_ws, material);
        Vector3D specColor;

        if (material.hasSpecularMap()) {
            specColor = sample_persp(interpolants.specCoord, material.getSpecularMap(), 0f);

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
