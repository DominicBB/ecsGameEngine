package rendering.drawers.fill;

import rendering.renderUtil.Edges.Edge;
import rendering.renderUtil.RenderState;
import rendering.renderUtil.interpolation.LerperFactory;
import rendering.renderUtil.interpolation.gouruad.GouruadInterpolants;
import rendering.renderUtil.interpolation.gouruad.GouruadLerper_E;
import util.Mathf.Mathf3D.Vec4fi;

public class TriangleRasterizer_G implements ITriRasterizer {
    private final Rasterizer_G rasterizer_G = new Rasterizer_G();
    private final GouruadInterpolants
            gI1 = new GouruadInterpolants(new GouruadLerper_E()),
            gI2 = new GouruadInterpolants(new GouruadLerper_E()),
            gI3 = new GouruadInterpolants(new GouruadLerper_E());
//    private int l_x, l_z, l_invW, l_specularity, l_color_r, l_color_g, l_color_b, l_color_a, l_tex_u, l_tex_v, l_spec_u, l_spec_v;
//    private int r_x, r_z, r_invW, r_specularity, r_color_r, r_color_g, r_color_b, r_color_a, r_tex_u, r_tex_v, r_spec_u, r_spec_v;
    private final Vec4fi fragColor, fragUtil;

    public TriangleRasterizer_G(Vec4fi fragColor, Vec4fi fragUtil) {
        this.fragColor = fragColor;
        this.fragUtil = fragUtil;
    }

    @Override
    public void fillTriangle(Edge tallest, Edge bottom, Edge top) {
        gI1.reset(tallest.v1);
        gI2.reset(bottom.v1);
        gI3.reset(top.v1);

        LerperFactory.gouruadLerper(RenderState.material, gI1.gouruadLerper_E, tallest.v1, tallest.v2, Rasterfi.inverse_pos(tallest.dy));
        LerperFactory.gouruadLerper(RenderState.material, gI2.gouruadLerper_E, bottom.v1, bottom.v2, Rasterfi.inverse_pos(bottom.dy));
        LerperFactory.gouruadLerper(RenderState.material, gI3.gouruadLerper_E, top.v1, top.v2, Rasterfi.inverse_pos(top.dy));

        scan(gI1, gI2, gI3, tallest.isOnLeft, bottom.yStart, top.yStart, bottom.deltaYInt, top.deltaYInt);
    }

    @Override
    public void fillTriangle(Edge left, Edge right) {
        gI1.reset(left.v1);
        gI2.reset(right.v1);
        int factor = Rasterfi.inverse_pos(left.dy);

        LerperFactory.gouruadLerper(RenderState.material, gI1.gouruadLerper_E, left.v1, left.v2, factor);
        LerperFactory.gouruadLerper(RenderState.material, gI2.gouruadLerper_E, right.v1, right.v2, factor);

        scan(gI1, gI2, left.isOnLeft, left.yStart, left.deltaYInt);
    }

    private void scan(GouruadInterpolants left, GouruadInterpolants right, boolean isOnLeft, int yStart, int dYInt) {
        if (isOnLeft) {
//            setLeft(left.gouruadLerper_E);
//            setRight(right.gouruadLerper_E);
            scanSegment_G(left, right, yStart, dYInt);
            return;
        }

//        setLeft(right.gouruadLerper_E);
//        setRight(left.gouruadLerper_E);
        scanSegment_G(right, left, yStart, dYInt);


    }

    private void scan(GouruadInterpolants tallest, GouruadInterpolants bottom, GouruadInterpolants top, boolean isOnLeft,
                      int bottomStarty, int topStarty, int bottomdy, int topdy) {
        if (isOnLeft) {
//            setLeft(tallest.gouruadLerper_E);//TODO move to scan
//            setRight(bottom.gouruadLerper_E);
            scanSegment_G(tallest, bottom, bottomStarty, bottomdy);
//            setRight(top.gouruadLerper_E);
            scanSegment_G(tallest, top, topStarty, topdy);
        } else {
//            setLeft(bottom.gouruadLerper_E);
//            setRight(tallest.gouruadLerper_E);
            scanSegment_G(bottom, tallest, bottomStarty, bottomdy);
//            setLeft(top.gouruadLerper_E);
            scanSegment_G(top, tallest, topStarty, topdy);
        }
    }

    private void scanSegment_G(GouruadInterpolants left, GouruadInterpolants right, int y, int yChange) {
//        setLeft(left.gouruadLerper_E);
//        setRight(right.gouruadLerper_E);
        int i = 1;
        while (i <= yChange) {
            rasterizer_G.rasterizeRow(left, right, y, fragColor, fragUtil);
            left.gouruadLerper_E.lerp(left);
            right.gouruadLerper_E.lerp(right);
//            lerpLeft(left);
//            lerpRight(right);
            ++y;
            ++i;
        }
    }

   /* private void lerpLeft(GouruadInterpolants interpolants) {
        interpolants.x += l_x;
        interpolants.z += l_z;
        interpolants.invW += l_invW;
        interpolants.color_a += l_color_a;
        interpolants.color_b += l_color_b;
        interpolants.color_g += l_color_g;
        interpolants.color_r += l_color_r;
        interpolants.tex_u += l_tex_u;
        interpolants.tex_v += l_tex_v;
        *//*interpolants.spec_u += l_spec_u;
        interpolants.spec_v += l_spec_v;*//*
        interpolants.specularity += l_specularity;
    }

    private void lerpRight(GouruadInterpolants interpolants) {
        interpolants.x += r_x;
        interpolants.z += r_z;
        interpolants.invW += r_invW;
        interpolants.color_a += r_color_a;
        interpolants.color_b += r_color_b;
        interpolants.color_g += r_color_g;
        interpolants.color_r += r_color_r;
        interpolants.tex_u += r_tex_u;
        interpolants.tex_v += r_tex_v;
       *//* interpolants.spec_u += r_spec_u;
        interpolants.spec_v += r_spec_v;*//*
        interpolants.specularity += r_specularity;
    }

    private void setLeft(GouruadLerper_E lerper) {
        l_z = lerper.z;
        l_x = lerper.x;
        l_invW = lerper.invW;
        l_color_a = lerper.color_a;
        l_color_b = lerper.color_b;
        l_color_g = lerper.color_g;
        l_color_r = lerper.color_r;
        l_tex_u = lerper.tex_u;
        l_tex_v = lerper.tex_v;
       *//* l_spec_u = lerper.spec_u;
        l_spec_v = lerper.spec_v;*//*
        l_specularity = lerper.specularity;
    }

    private void setRight(GouruadLerper_E lerper) {
        r_z = lerper.z;
        r_x = lerper.x;
        r_invW = lerper.invW;
        r_color_a = lerper.color_a;
        r_color_b = lerper.color_b;
        r_color_g = lerper.color_g;
        r_color_r = lerper.color_r;
        r_tex_u = lerper.tex_u;
        r_tex_v = lerper.tex_v;
       *//* r_spec_u = lerper.spec_u;
        r_spec_v = lerper.spec_v;*//*
        r_specularity = lerper.specularity;
    }*/

    //------------------TEST----------------------
    /*private void scanSegment_G(GouruadInterpolants left, GouruadInterpolants right, int y, int yChange) {
        int i = 1;
        int from, to, invdX;
        while (i <= yChange) {
            from = Rasterfi.ceil_destroy_format_pos(left.x);
            to = Rasterfi.ceil_destroy_format_pos(right.x);
            rasterizer_G.setRowInterpolants(left, from);
            if (to - from == 0) {
                rasterizer_G.fragShade(y, fragColor, fragUtil);
            } else {
                invdX = Rasterfi.inverse(right.x - left.x);
                RowLerperFactory.gouruadLerper(rasterizer_G.gouruadLerper_r, RenderState.material, left, right, invdX);
                rasterizer_G.rasterizeRow(y, to, fragColor, fragUtil);
            }
            left.gouruadLerper_E.lerp(left);
            right.gouruadLerper_E.lerp(right);
            ++y;
            ++i;
        }
    }*/
}
