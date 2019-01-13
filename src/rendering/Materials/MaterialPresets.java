package rendering.Materials;

import rendering.renderUtil.Colorf;
import rendering.shaders.Shaders;
import util.Mathf.Mathf3D.Vec4f;

public final class MaterialPresets {
    public static final Material MATERIAL_1 = new Material("mat1", Shaders.GOURAUD_SHADER,
            null, null, null, null,
            1f, 1f, 1f, 100f, Vec4f.newOnes().mul(2f), Colorf.RED,
            true, true, true);

    public static final Material MATERIAL_2 = new Material("mat2", Shaders.FLAT_SHADER,
            null, null, null, null,
            1f, 1f, 1f, 100f, Vec4f.newOnes().mul(2f), Colorf.RED,
            true, true, true);

    public static final Material MATERIAL_3 = new Material("mat3", Shaders.GOURAUD_SHADER,
            null, null, null, null,
            1f, 1f, 1f, 100f, Vec4f.newOnes().mul(2f), Colorf.RED,
            true, true, true);
}
