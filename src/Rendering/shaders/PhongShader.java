package Rendering.shaders;

import Rendering.Materials.Material;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.shaders.interfaces.IShader;

import static Rendering.shaders.ShaderUtil.setVOut;
import static Rendering.shaders.ShaderUtil.transformVIn;

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

    /*public static boolean fragNonAlloc(PhongInterpolants pI, Material material, Vec4fi outColor, Vec4fi util, int y) {
        if (!ShaderUtil.zBufferTest(RenderState.zBuffer, pI.z, pI.xInt, y)) {
            return false;
        }

        int w = Rasterfi.un_inverse(pI.invW);
        if (material.hasTexture())
            sample_persp_NonAlloc(pI.tex_u, pI.tex_v, material.getTexture().texture, w, outColor);
        else
            outColor.set_unsafe(material.getColorfi());

        calculateLighting(pI, material, util, outColor);
        return true;
    }

    private static void calculateLighting(PhongInterpolants pI, Material material, Vec4fi util, Vec4fi surfaceColor) {

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

    private static void addDiffuse(PhongInterpolants pI, Material material, Vec4fi util, Vec4fi outColor) {
        util.set(pI.n_ws_x, pI.n_ws_y, pI.n_ws_z, 1);
        Rasterfi.multiply(outColor, (diffuse(util, material)));
    }

    private static void calcSpecular(PhongInterpolants pI, Material material, Vec4fi util) {
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
    }*/


    @Override
    public ShaderType getShaderType() {
        return ShaderType.PHONG;
    }
}
