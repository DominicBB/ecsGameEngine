package Rendering.shaders.interfaces;

import Rendering.Materials.Material;
import Rendering.renderUtil.RenderContext;
import Rendering.renderUtil.VertexOut;

public interface IGeometryShader {
    int getVerticesPerPrimitive();
    VertexOut[] geom(VertexOut[] vertexOut, RenderContext renderContext, Material material);
}
