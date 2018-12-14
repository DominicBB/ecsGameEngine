package Rendering.shaders;


import Rendering.Materials.Material;
import Rendering.renderUtil.Lerpers.Interpolants;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.shaders.interfaces.IGeometryShader;
import Rendering.shaders.interfaces.IShader;
import util.FloatBuffer;
import util.Mathf.Mathf3D.Triangle;
import util.Mathf.Mathf3D.Vector3D;

import static Rendering.shaders.ShaderUtil.*;

public class FlatShader implements IShader, IGeometryShader {
    private final int verticiesPerPrimitive = 3;

    @Override
    public final VertexOut vert(Vertex vertex, Material material) {
        Vector3D p_proj = RenderState.mvp.multiply4x4(vertex.vec);
        return new VertexOut(
                p_proj,
                vertex.texCoord,
                vertex.specCoord,
                1f,
                Vector3D.newZeros(),
                vertex.normal,
                RenderState.world.multiply4x4(vertex.vec),
                1f/p_proj.w
        );
    }

    @Override
    public final VertexOut[] geom(VertexOut[] vertices, Material material) {
        Vector3D averageNorm = (vertices[0].n_ws.sqrMagnitude() == 0f) ? Triangle.normal(vertices) : averageNormal(vertices);

        Vector3D surfaceColor = vertices[0].surfaceColor;

        float spec = calculateBasicLighting(averageNorm, vertices[0].p_ws, material, surfaceColor);

        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new VertexOut(vertices[i].p_proj,
                    vertices[i].texCoord,
                    vertices[i].specCoord,
                    spec,
                    surfaceColor,
                    vertices[i].n_ws,
                    vertices[i].p_ws,
                    vertices[i].invW);
        }

        return vertices;
    }

    @Override
    public final Vector3D frag(Interpolants lP, FloatBuffer zBuffer, Material material) {
        float z = 1f/lP.getInvW();
        if (!ShaderUtil.zBufferTest(zBuffer, z, lP.getxInt(), lP.getyInt()))
            return null;


        if (material.isSpecular()) {
            lP.getSurfaceColor().add(calcSpecularAtFrag(lP.getSpecCoord(), lP.getSpecularity(),z, material));
        }

        Vector3D color;
        if (material.hasTexture()) {
            color = perspectiveCorrectBitmap(lP.getTexCoord(),material.getTexture().texture,z);
        } else {
            color = lP.getSurfaceColor().componentMul(material.getColor());
        }

        return color;
    }

    @Override
    public final int getVerticesPerPrimitive() {
        return verticiesPerPrimitive;
    }


    private float calculateBasicLighting(Vector3D n, Vector3D p, Material material, Vector3D sColor) {
        if (material.isDiffuse()) {
            sColor.add(calcDiffuse(n, material));
        }
        float specularity = 1f;
        if (material.isSpecular()) {
            specularity = calcSpecularity(n, p, material, sColor);
        }

        if (material.isAmbient()) {
            sColor.add(ambient(RenderState.lightingState.ambientColor, material.getAmbientFactor()));
        }

        return specularity;
    }

    private Vector3D calcDiffuse(Vector3D n, Material material) {
        return diffuse(RenderState.lightingState.lightColor,
                RenderState.lightingState.lightDir,
                n,
                RenderState.lightingState.attenuation,
                material.getDiffuseFactor());
    }

    private float calcSpecularity(Vector3D n, Vector3D p,
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

    @Override
    public ShaderType getShaderType() {
        return ShaderType.FLAT;
    }
}
