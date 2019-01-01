package Rendering.shaders;

import Rendering.Materials.Material;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.renderUtil.interpolation.IInterpolants;
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
    public final Vector3D frag(IInterpolants IInterpolants, Material material) {

        if (!ShaderUtil.zBufferTest(RenderState.zBuffer, IInterpolants.p_proj.z, IInterpolants.xInt, IInterpolants.yInt)) {
            return null;
        }

        float w = 1f / IInterpolants.invW;

        Vector3D surfaceColor = calculateLighting(IInterpolants, material, IInterpolants.xInt);

        Vector3D color;
        if (material.hasTexture()) {
            color = surfaceColor.
                    componentMul(material.getTexture().texture.getPixel(
                            (int) IInterpolants.texCoord.x,
                            (int) IInterpolants.texCoord.y));

        } else {
            color = material.getColor().componentMul(surfaceColor);
        }
        return color;
    }

    @Override
    public boolean fragNonAlloc(IInterpolants vertex, Material material, Vector3D outColor, Vector3D util) {
        return false;
    }

    private Vector3D calculateLighting(IInterpolants IInterpolants, Material material, int x) {

        if (material.isDiffuse()) {
            IInterpolants.surfaceColor.add(calcDiffuse(IInterpolants, material, x));
        }

        if (material.isSpecular()) {
            IInterpolants.surfaceColor.add(calcSpecular(IInterpolants, material));
        }

        if (material.isAmbient()) {
            IInterpolants.surfaceColor.add(ambient(RenderState.lightingState.ambientColor,
                    material.getAmbientFactor()));
        }
        return IInterpolants.surfaceColor;
    }

    private Vector3D calcDiffuse(IInterpolants IInterpolants, Material material, int x) {
        Vector3D n = IInterpolants.n_ws;
        if (material.hasNormalMap()) {
            n = material.getNormalMap().getPixel(x, IInterpolants.yInt);
        }
        return diffuse(n, material);
    }

    private Vector3D calcSpecular(IInterpolants IInterpolants, Material material) {
        //Vector3D p_ws = renderer.screenSpaceToWorldSpace(IInterpolants.getP_proj());

        float spec = calculateSpecular(IInterpolants.n_ws, IInterpolants.p_ws, material);
        Vector3D specColor;

        if (material.hasSpecularMap()) {
            specColor = sample_persp(IInterpolants.specCoord, material.getSpecularMap(), 0f);

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
