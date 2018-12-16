package Rendering.Materials;

import Rendering.renderUtil.Colorf;
import Rendering.shaders.GouraudShader;
import util.Mathf.Mathf3D.Vector3D;

public final class MaterialPresets {
    public static Material material1 = new Material("mat1",new GouraudShader(),
            null,null,null,null,
            1f,1f,1f,100f, Vector3D.newOnes().mul(2f), Colorf.RED,
            true,true,true);
}
