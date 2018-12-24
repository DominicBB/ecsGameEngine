package Rendering.shaders.interfaces;

import Rendering.Materials.Material;
import Rendering.renderUtil.interpolation.Interpolants;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.shaders.ShaderType;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public interface IShader {
    ShaderType getShaderType();

    VertexOut vert(Vertex vertex, Material material);

    void vertNonAlloc(Vertex vertex, Material material, VertexOut out);


    Vector3D frag(Interpolants vertex, Material material);

    boolean fragNonAlloc(Interpolants vertex, Material material, Vector3D outColor, Vector3D util);

}
