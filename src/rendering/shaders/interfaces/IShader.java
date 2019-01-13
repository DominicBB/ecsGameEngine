package rendering.shaders.interfaces;

import rendering.materials.Material;
import rendering.renderUtil.Vertex;
import rendering.renderUtil.VertexOut;
import rendering.shaders.ShaderType;

public interface IShader {
    ShaderType getShaderType();

    VertexOut vert(Vertex vertex, Material material);

    void vertNonAlloc(Vertex vertex, Material material, VertexOut out);
}
