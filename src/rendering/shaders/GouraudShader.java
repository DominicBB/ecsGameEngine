package rendering.shaders;

import rendering.materials.Material;
import rendering.renderUtil.RenderState;
import rendering.renderUtil.Vertex;
import rendering.renderUtil.VertexOut;
import rendering.renderUtil.interpolation.gouruad.GouruadInterpolants;
import rendering.shaders.interfaces.IShader;
import util.mathf.Mathf2D.Vec2f;
import util.mathf.Mathf3D.Vec4f;

import static rendering.shaders.ShaderUtil.*;

public class GouraudShader implements IShader {

    GouraudShader() {
    }

    @Override
    public void vertNonAlloc(Vertex vIn, Material material, VertexOut out) {
        out.p_proj.set((RenderState.mvp.multiply4x4(vIn.vec)));
        out.surfaceColor.set(Vec4f.newZeros());
        out.p_ws.set(RenderState.world.multiply4x4(vIn.vec));
        out.n_ws.set(RenderState.transform.getRotation().rotate(vIn.normal));
        out.invW = 1f / out.p_proj.w;

        if (material.hasTexture()) {
            out.texCoord.set(scaleToBitmap(vIn.texCoord, material.getTexture().texture));
            out.texCoord.scale(out.invW);
        }

        if (material.isDiffuse()) {
            Vec4f diffuse = diffuse(
                    RenderState.lightingState.lightColor,
                    RenderState.lightingState.lightDir,
                    out.n_ws,
                    RenderState.lightingState.attenuation,
                    material.getDiffuseFactor());

            out.surfaceColor.add(diffuse);
        }

        out.spec = 1f;
        if (material.isSpecular()) {
            out.spec = calculateSpecular(out.n_ws, out.p_ws, material);
            if (!material.hasSpecularMap()) {
                out.surfaceColor.add(material.getDefualtSpecularColor().mul(out.spec));
            } else {
                out.specCoord.set(scaleToBitmap(vIn.specCoord, material.getSpecularMap()));
                out.specCoord.scale(out.invW);
            }
        }

        if (material.isAmbient()) {
            out.surfaceColor.add(ambient(RenderState.lightingState.ambientColor, material.getAmbientFactor()));
        }
    }

    @Override
    public final VertexOut vert(Vertex vIn, Material material) {
        VertexOut vertexOut = new VertexOut(
                RenderState.mvp.multiply4x4(vIn.vec),
                Vec2f.newCopy(vIn.texCoord),
                Vec2f.newCopy(vIn.specCoord),
                0f,
                Vec4f.newZeros(),
                RenderState.transform.getRotation().rotate(vIn.normal),
                RenderState.world.multiply4x4(vIn.vec),
                0f
        );

        vertNonAlloc(vIn, material, vertexOut);
        return vertexOut;
    }

    public static boolean fragNonAlloc(GouruadInterpolants gI, Material material, Vec4f outColor, Vec4f util, int y) {
        if (!ShaderUtil.zBufferTest(RenderState.zBuffer, gI.z, gI.xInt, y))
            return false;

        float w = 1f / gI.invW;
        outColor.set(gI.color_r, gI.color_g, gI.color_b, gI.color_a);
        if (material.hasSpecularMap()) {
            outColor.add(calcSpecularAtFrag(gI.spec_u, gI.spec_v, gI.specularity, w, material));
        }

        if (material.hasTexture()) {
            sample_persp_NonAlloc(gI.tex_u, gI.tex_v, material.getTexture().texture, w, util);
            Vec4f.componentMulNonAlloc(outColor, util);
            return true;
        }
        Vec4f.componentMulNonAlloc(outColor, material.getColor());
        return true;
    }


    @Override
    public ShaderType getShaderType() {
        return ShaderType.GOURUAD;
    }
}