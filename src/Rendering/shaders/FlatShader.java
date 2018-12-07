package Rendering.shaders;


import Rendering.Materials.Material;
import Rendering.renderUtil.Lerpers.LerpValues;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Renderer;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.shaders.interfaces.IGeometryShader;
import Rendering.shaders.interfaces.IShader;
import util.FloatBuffer;
import util.Mathf.Mathf3D.Vector3D;

import static Rendering.shaders.ShaderUtil.calculateSpecular;
import static Rendering.shaders.ShaderUtil.diffuse;
import static Rendering.shaders.ShaderUtil.ambient;

public class FlatShader implements IShader, IGeometryShader {
    private final int verticiesPerPrimitive = 3;

    @Override
    public final VertexOut vert(Vertex vertex, Material material) {
        Vector3D pos_proj = RenderState.objmvp.multiply4x4(vertex.vec);
        return new VertexOut(
                pos_proj,
                vertex.texCoord,
                vertex.specCoord,
                1f,
                Vector3D.newOnes(),
                vertex.normal,
                vertex.vec
        );
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

    @Override
    public final int getVerticesPerPrimitive() {
        return verticiesPerPrimitive;
    }

    @Override
    public final VertexOut[] geom(VertexOut[] vertices, Renderer renderer, Material material) {
        VertexOut[] vOut = new VertexOut[3];
        Vector3D averageNorm = averageNormal(vertices);
        Vector3D surfaceColor = vertices[0].surfaceColor;

        float spec = calculateBasicLighting(averageNorm, vertices[0].p_ws, renderer, material, surfaceColor);


        for (int i = 0; i < vOut.length; i++) {
            vOut[i] = new VertexOut(vertices[i].p_proj,
                    vertices[i].texCoord,
                    vertices[i].specCoord,
                    spec,
                    surfaceColor,
                    vertices[i].n_ws,
                    vertices[i].p_ws);
        }
        return vOut;
    }

    private float calculateBasicLighting(Vector3D n, Vector3D p, Renderer renderer, Material material, Vector3D sColor) {
        if (material.isDiffuse()) {
            sColor.add(calcDiffuse(n, renderer, material));
        }
        float specularity = 1f;
        if (material.isSpecular()) {
            specularity = calcSpecularity(n, p, renderer, material, sColor);
        }

        if (material.isAmbient()) {
            sColor.add(ambient(RenderState.lightingState.ambientColor, material.getAmbientFactor()));
        }

        return specularity;
    }

    private Vector3D calcDiffuse(Vector3D n, Renderer renderer, Material material) {
        return diffuse(RenderState.lightingState.lightColor,
                RenderState.lightingState.lightDir,
                n,
                RenderState.lightingState.attenuation,
                material.getDiffuseFactor());
    }

    private float calcSpecularity(Vector3D n, Vector3D p, Renderer renderer,
                                  Material material, Vector3D sColor) {

        float specularity = calculateSpecular(n, p, material);
        if (!material.hasSpecularMap()) {
            sColor.add(material.getDefualtSpecularColor().mul(specularity));
        }
        return specularity;
    }

    private Vector3D averageNormal(VertexOut[] vecs) {
        Vector3D sum = vecs[0].n_ws;
        for (int i = 1; i < vecs.length; i++) {
            sum.add(vecs[i].n_ws);
        }
        return sum.divide(vecs.length);
    }

    private static final ShaderType SHADER_TYPE = ShaderType.FLAT;
    @Override
    public ShaderType getShaderType() {
        return SHADER_TYPE;
    }
}
