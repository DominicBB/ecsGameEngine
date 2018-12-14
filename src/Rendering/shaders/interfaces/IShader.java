package Rendering.shaders.interfaces;

import Rendering.Materials.Material;
import Rendering.renderUtil.Lerpers.Interpolants;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.shaders.ShaderType;
import util.FloatBuffer;
import util.Mathf.Mathf3D.Vector3D;

public interface IShader {
    ShaderType getShaderType();

    VertexOut vert(Vertex vertex, Material material);

    void vertNonAlloc(Vertex vertex, Material material, VertexOut out);


    Vector3D frag(Interpolants vertex, FloatBuffer zBuffer, Material material);

}
