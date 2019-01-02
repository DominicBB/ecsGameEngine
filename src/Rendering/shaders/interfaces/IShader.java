package Rendering.shaders.interfaces;

import Rendering.Materials.Material;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.shaders.ShaderType;

public interface IShader {
    ShaderType getShaderType();

    VertexOut vert(Vertex vertex, Material material);

    void vertNonAlloc(Vertex vertex, Material material, VertexOut out);
}
