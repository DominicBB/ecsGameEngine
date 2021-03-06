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
    public void vertNonAlloc(Vertex vIn, Material material, VertexOut out) {
        out.p_proj.set((RenderState.mvp.multiply4x4(vIn.vec)));
        out.surfaceColor.set(Vector3D.newZeros());
        out.p_ws.set(RenderState.world.multiply4x4(vIn.vec));
        out.n_ws.set(RenderState.transform.getRotation().rotate(vIn.normal));
        out.invW = 1f / out.p_proj.w;

        if (material.hasTexture()) {
            out.texCoord.set(scaleToBitmap(vIn.texCoord, material.getTexture().texture));
            out.texCoord.scale(out.invW);
        }

        if (material.isDiffuse()) {

            Vector3D diffuse = diffuse(RenderState.lightingState.lightColor,
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
                specCoord.set(scaleToBitmap(specCoord, material.getSpecularMap()));
                specCoord.scale(invW);
            }
        }

        if (material.isAmbient()) {
            sColor.add(ambient(RenderState.lightingState.ambientColor, material.getAmbientFactor()));
        }

        return new VertexOut(p_proj, texCoord, specCoord, spec, sColor, n_ws, p_ws, invW);
    }


    private final Vector3D fragColor = Vector3D.newZeros();
    private final Vector3D fragTexColor = Vector3D.newZeros();

    @Override
    public final Vector3D frag(Interpolants lP, FloatBuffer zBuffer, Material material) {
        if (!ShaderUtil.zBufferTest(zBuffer, lP.p_proj.z, lP.xInt, lP.yInt))
            return null;

        float w = 1f / lP.invW;
        fragColor.set(lP.surfaceColor);

        if (material.hasSpecularMap()) {
            fragColor.add(calcSpecularAtFrag(lP.specCoord, lP.specularity, w, material));
        }

        if (material.hasTexture()) {
            perspectiveCorrectBitmapNonAlloc(lP.texCoord, material.getTexture().texture, w, fragTexColor);
            Vector3D.componentMulNonAlloc(fragColor, fragTexColor);
            return fragColor;
        }
        Vector3D.componentMulNonAlloc(fragColor, material.getColor());
        
       /* perspectiveCorrectBitmapNonAlloc(lP.texCoord, material.getTexture().texture, w, fragTexColor);
        Vector3D.componentMulNonAlloc(fragColor, fragTexColor);*/
        return fragColor;
    }


    private Vector3D sampleTexture(Vector2D texC, Material material) {
        return material.getTexture().texture.getPixel((int) texC.x,
                (int) texC.y);
    }


    @Override
    public ShaderType getShaderType() {
        return ShaderType.GOURUAD;
    }
}
