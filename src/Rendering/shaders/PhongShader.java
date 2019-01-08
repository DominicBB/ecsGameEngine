package Rendering.shaders;

import Rendering.Materials.Material;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.renderUtil.interpolation.phong.PhongInterpolants;
import Rendering.shaders.interfaces.IShader;
import util.Mathf.Mathf3D.Vec4f;

import static Rendering.shaders.ShaderUtil.*;

public class PhongShader implements IShader {
    PhongShader() {
    }

    @Override
    public final VertexOut vert(Vertex vIn, Material material) {
        return transformVIn(vIn, material);
    }

    @Override
    public void vertNonAlloc(Vertex vIn, Material material, VertexOut out) {
        setVOut(vIn, material, out);
    }

    public static boolean fragNonAlloc(PhongInterpolants pI, Material material, Vec4f outColor, Vec4f util, int y) {
        if (!ShaderUtil.zBufferTest(RenderState.zBuffer, pI.z, pI.xInt, y)) {
            return false;
        }

        float w = 1f / pI.invW;
        if (material.hasTexture())
            sample_persp_NonAlloc(pI.tex_u, pI.tex_v, material.getTexture().texture, w, outColor);
        else
            outColor.set(material.getColor());

        calculateLighting(pI, material, util, outColor);
        return true;
    }

    private static void calculateLighting(PhongInterpolants pI, Material material, Vec4f util, Vec4f surfaceColor) {

        if (material.isDiffuse()) {
            addDiffuse(pI, material, util, surfaceColor);
        }

        if (material.isSpecular()) {
            calcSpecular(pI, material, util);
            surfaceColor.add(surfaceColor.componentMul(util));
        }

        if (material.isAmbient()) {
            surfaceColor.add(surfaceColor.componentMul(ambient(RenderState.lightingState.ambientColor,
                    material.getAmbientFactor())));
        }
    }

    private static void addDiffuse(PhongInterpolants pI, Material material, Vec4f util, Vec4f outColor) {
        util.set(pI.n_ws_x, pI.n_ws_y, pI.n_ws_z, 1f);
        Vec4f.componentMulNonAlloc(outColor, (diffuse(util, material)));
    }

    private static void calcSpecular(PhongInterpolants pI, Material material, Vec4f util) {
        util.set(pI.p_ws_x, pI.p_ws_y, pI.p_ws_z, 1f);
        Vec4f viewDir;
        (viewDir = RenderState.camera.transform.getPosition().minus(util)).normalise();
        util.set(pI.n_ws_x, pI.n_ws_y, pI.n_ws_z, 1f);
        float spec = specular(util, RenderState.lightingState.lightDir, viewDir,
                material.getSpecularFactor(), material.getSpecularPower(), RenderState.lightingState.attenuation);

        if (material.hasSpecularMap()) {
            util.set(sample_persp(pI.spec_u, pI.spec_v, material.getSpecularMap(), 0f));

        } else {
            util.set(material.getDefualtSpecularColor());
        }

        util.scale(spec);
    }


    @Override
    public ShaderType getShaderType() {
        return ShaderType.PHONG;
    }
}
