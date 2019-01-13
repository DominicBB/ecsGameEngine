package rendering.shaders;


import rendering.materials.Material;
import rendering.renderUtil.RenderState;
import rendering.renderUtil.Vertex;
import rendering.renderUtil.VertexOut;
import rendering.renderUtil.interpolation.flat.FlatInterpolants;
import rendering.shaders.interfaces.IGeometryShader;
import rendering.shaders.interfaces.IShader;
import util.mathf.Mathf3D.Triangle;
import util.mathf.Mathf3D.Vec4f;

import static rendering.shaders.ShaderUtil.*;

public class FlatShader implements IShader, IGeometryShader {
    private final int verticiesPerPrimitive = 3;

    FlatShader() {
    }

    @Override
    public final VertexOut vert(Vertex vertex, Material material) {
        return transformVIn(vertex, material);
    }

    @Override
    public void vertNonAlloc(Vertex vIn, Material material, VertexOut out) {
        setVOut(vIn, material, out);
    }

    @Override
    public final VertexOut[] geom(VertexOut v1, VertexOut v2, VertexOut v3, Material material, VertexOut[] vertices) {
        Vec4f averageNorm = (v1.n_ws.sqrMagnitude() == 0f) ? Triangle.normal(v1, v2, v3) : averageNormal(v1, v2, v3);

        Vec4f surfaceColor = Vec4f.newZeros();
        float spec = calculateBasicLighting(averageNorm, v1.p_ws, material, surfaceColor);

        //TODO: do not need to set for all 3
        if (material.hasSpecularMap()) {
            vertices[0].spec = spec;
            vertices[1].spec = spec;
            vertices[2].spec = spec;

            vertices[0].specCoord.set(v1.specCoord);
            vertices[1].specCoord.set(v2.specCoord);
            vertices[2].specCoord.set(v3.specCoord);
        }

        if (material.hasTexture()) {
            vertices[0].texCoord.set(v1.texCoord);
            vertices[1].texCoord.set(v2.texCoord);
            vertices[2].texCoord.set(v3.texCoord);
        }

        vertices[0].surfaceColor.set(surfaceColor);
        vertices[1].surfaceColor.set(surfaceColor);
        vertices[2].surfaceColor.set(surfaceColor);

        vertices[0].p_proj.set(v1.p_proj);
        vertices[1].p_proj.set(v2.p_proj);
        vertices[2].p_proj.set(v3.p_proj);

        vertices[0].invW = v1.invW;
        vertices[1].invW = v2.invW;
        vertices[2].invW = v3.invW;




        /*for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new VertexOut(vertices[i].p_proj,
                    Vec2f.newCopy(vertices[i].texCoord),
                    *//*Vec2f.newCopy(vertices[i].specCoord)*//*vertices[i].specCoord,
                    spec,
                    surfaceColor,
                    Vec4f.newCopy(vertices[i].n_ws),
                    Vec4f.newCopy(vertices[i].p_ws),
                    vertices[i].invW);
        }*/

        return vertices;
    }


    public static boolean fragNonAlloc(FlatInterpolants fI, Vec4f surfaceColor, float spec,
                                       Material material, Vec4f outColor, Vec4f util, int y) {
        if (!ShaderUtil.zBufferTest(RenderState.zBuffer, fI.z, fI.xInt, y))
            return false;

        float w = 1f / fI.invW;
        outColor.set(surfaceColor);
        if (material.hasSpecularMap()) {
            sample_persp_NonAlloc(fI.spec_u, fI.spec_v, material.getSpecularMap(), w, util);
            outColor.add(util.mul(spec));
        }
        if (material.hasTexture()) {
            sample_persp_NonAlloc(fI.tex_u, fI.tex_v, material.getTexture().texture, w, util);
            Vec4f.componentMulNonAlloc(outColor, util);
        } else {
            Vec4f.componentMulNonAlloc(outColor, material.getColor());
        }
        return true;
    }

    @Override
    public final int getVerticesPerPrimitive() {
        return verticiesPerPrimitive;
    }


    private float calculateBasicLighting(Vec4f n, Vec4f p, Material material, Vec4f sColor) {
        if (material.isDiffuse()) {
            sColor.add(diffuse(n, material));
        }
        float spec = 1f;
        if (material.isSpecular()) {
            spec = calcSpecularity(n, p, material, sColor);
        }

        if (material.isAmbient()) {
            sColor.add(ambient(RenderState.lightingState.ambientColor, material.getAmbientFactor()));
        }
        return spec;
    }

    private float calcSpecularity(Vec4f n, Vec4f p,
                                  Material material, Vec4f sColor) {
        float specularity = calculateSpecular(n, p, material);
        if (!material.hasSpecularMap()) {
            sColor.add(material.getDefualtSpecularColor().mul(specularity));
        }
        return specularity;
    }

    private Vec4f averageNormal(VertexOut v1, VertexOut v2, VertexOut v3) {
        Vec4f avg = Vec4f.newCopy(v1.n_ws);
        avg.add(v2.n_ws);
        avg.add(v3.n_ws);
        avg.mutDivide(3);

        return avg;
    }

    @Override
    public ShaderType getShaderType() {
        return ShaderType.FLAT;
    }
}
