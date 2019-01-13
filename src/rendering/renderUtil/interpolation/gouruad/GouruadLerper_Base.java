package rendering.renderUtil.interpolation.gouruad;

public abstract class GouruadLerper_Base {
    public int z;// depth buffering
    public int invW;// perspective correctness
    public int specularity;

    public int color_r, color_g, color_b, color_a;
    public int tex_u, tex_v;
    public int spec_u, spec_v;

    protected static void lerp(GouruadInterpolants interpolants, GouruadLerper_Base base) {
        interpolants.z += base.z;

        interpolants.color_r += base.color_r;
        interpolants.color_g += base.color_g;
        interpolants.color_b += base.color_b;
//        interpolants.color_a += base.color_a;

        interpolants.tex_u += base.tex_u;
        interpolants.tex_v += base.tex_v;

        /*interpolants.spec_u +=base. spec_u;
        interpolants.spec_v += base.spec_v;*/

        interpolants.invW += base.invW;
        interpolants.specularity += base.specularity;
    }
}
