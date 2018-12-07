package Rendering.shaders.interfaces;

import Rendering.Materials.Material;
import Rendering.renderUtil.Lerpers.LerpValues;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import Rendering.shaders.ShaderType;
import util.FloatBuffer;
import util.Mathf.Mathf3D.Vector3D;

public interface IShader {
    ShaderType getShaderType();

    VertexOut vert(Vertex vertex, Material material);

    Vector3D frag(LerpValues vertex, FloatBuffer zBuffer, Material material);

}
