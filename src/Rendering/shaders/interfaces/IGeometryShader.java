package Rendering.shaders.interfaces;

import Rendering.Materials.Material;
import Rendering.renderUtil.VertexOut;

public interface IGeometryShader {
    int getVerticesPerPrimitive();
    VertexOut[] geom(VertexOut[] vertexOut, Material material);
}
