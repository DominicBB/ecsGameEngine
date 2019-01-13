package rendering.shaders.interfaces;

import rendering.materials.Material;
import rendering.renderUtil.VertexOut;

public interface IGeometryShader {
    int getVerticesPerPrimitive();

    VertexOut[] geom(VertexOut v1, VertexOut v2, VertexOut v3, Material material, VertexOut[] vertexOut);
}
