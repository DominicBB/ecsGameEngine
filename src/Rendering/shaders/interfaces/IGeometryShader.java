package Rendering.shaders.interfaces;

import Rendering.Materials.Material;
import Rendering.renderUtil.VertexOut;

public interface IGeometryShader {
    int getVerticesPerPrimitive();

    VertexOut[] geom(VertexOut v1, VertexOut v2, VertexOut v3, Material material, VertexOut[] vertexOut);
}
