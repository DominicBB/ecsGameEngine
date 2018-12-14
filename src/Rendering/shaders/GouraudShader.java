package Rendering.shaders;

import Rendering.Materials.Material;
import Rendering.renderUtil.Lerpers.Interpolants;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.shaders.interfaces.IShader;
import util.FloatBuffer;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

import static Rendering.shaders.ShaderUtil.*;

public class GouraudShader implements IShader {

    @Override
    public final VertexOut vert(Vertex vIn, Material material) {

        Vector3D p_proj = (RenderState.mvp.multiply4x4(vIn.vec));
        Vector3D sColor = Vector3D.newZeros();
        Vector3D p_ws = RenderState.world.multiply4x4(vIn.vec);
        Vector3D n_ws = RenderState.transform.getRotation().rotate(vIn.normal);
        Vector2D texCoord = vIn.texCoord;
        Vector2D specCoord = vIn.specCoord;

        float invW = 1f / p_proj.w;
        if (material.hasTexture()) {
            texCoord = scaleToBitmap(texCoord, material.getTexture().texture);
            texCoord.scale(invW);
        }

        if (material.isDiffuse()) {

            Vector3D diffuse = diffuse(RenderState.lightingState.lightColor,
                    RenderState.lightingState.lightDir,
                    n_ws,
                    RenderState.lightingState.attenuation,
                    material.getDiffuseFactor());

            sColor.add(diffuse);
        }

        float spec = 1f;
        if (material.isSpecular()) {
            spec = calculateSpecular(n_ws, p_ws, material);
            if (!material.hasSpecularMap()) {
                sColor.add(material.getDefualtSpecularColor().mul(spec));
            } else {
                specCoord = specCoord.mul(invW);
//                scaleToBitmap(specCoord, material.getSpecularMap());
            }
        }

        if (material.isAmbient()) {
            sColor.add(ambient(RenderState.lightingState.ambientColor, material.getAmbientFactor()));
        }

        return new VertexOut(p_proj, texCoord, specCoord, spec, sColor, n_ws, p_ws, invW);
    }

    @Override
    public final Vector3D frag(Interpolants lP, FloatBuffer zBuffer, Material material) {
        float w = 1f / lP.getInvW();
        //should use z here
        if (!ShaderUtil.zBufferTest(zBuffer, w, lP.getxInt(), lP.getyInt()))
            return null;

        if (material.isSpecular()) {
            lP.getSurfaceColor().add(calcSpecularAtFrag(lP.getSpecCoord(), lP.getSpecularity(), w, material));
        }

        Vector3D color;
        if (material.hasTexture()) {
//            scaleToBitmap(texCoord, material.getTexture().texture);
            color = lP.getSurfaceColor().componentMul(perspectiveCorrectBitmap(lP.getTexCoord(), material.getTexture().texture, w));
//            Vector2D texC = lP.getTexCoord().mul(w);
//            scaleToBitmap(texC, material.getTexture().texture);
//            color = lP.getSurfaceColor().componentMul(sampleTexture(texC, material));
        } else {
            color = lP.getSurfaceColor().componentMul(material.getColor());
        }

        return color;
    }


    private Vector3D sampleTexture(Vector2D texC, Material material) {
        return material.getTexture().texture.getPixelColor((int) texC.x,
                (int) texC.y);
    }


    @Override
    public ShaderType getShaderType() {
        return ShaderType.GOURUAD;
    }
}
