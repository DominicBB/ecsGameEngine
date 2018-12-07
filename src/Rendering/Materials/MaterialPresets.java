package Rendering.Materials;

import Rendering.renderUtil.Colorf;
import Rendering.shaders.StdShaderGouraud;
import util.Mathf.Mathf3D.Vector3D;

public final class MaterialPresets {
    public static Material material1 = new Material("mat1",new StdShaderGouraud(),
            null,null,null,null,
            1f,1f,1f,100f, Vector3D.newOnes(), Colorf.RED,
            true,true,true);
}
