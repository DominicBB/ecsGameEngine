package Rendering.drawers.fill;

import Rendering.renderUtil.Edges.Edge;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.interpolation.LerperFactory;
import Rendering.renderUtil.interpolation.gouruad.GouruadInterpolants;
import Rendering.renderUtil.interpolation.gouruad.GouruadLerper_E;
import util.Mathf.Mathf3D.Vec4fi;

public class TriangleRasterizer_G implements ITriRasterizer {
    private final Rasterizer_G rasterizer_G = new Rasterizer_G();
    private final GouruadInterpolants
            gI1 = new GouruadInterpolants(new GouruadLerper_E()),
            gI2 = new GouruadInterpolants(new GouruadLerper_E()),
            gI3 = new GouruadInterpolants(new GouruadLerper_E());

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

        LerperFactory.gouruadLerper(RenderState.material, gI1.gouruadLerper_E, tallest.v1, tallest.v2, Rasterfi.inverse(tallest.dy));
        LerperFactory.gouruadLerper(RenderState.material, gI2.gouruadLerper_E, bottom.v1, bottom.v2, Rasterfi.inverse(bottom.dy));
        LerperFactory.gouruadLerper(RenderState.material, gI3.gouruadLerper_E, top.v1, top.v2, Rasterfi.inverse(top.dy));

        scan(gI1, gI2, gI3, tallest.isOnLeft, bottom.yStart, top.yStart, bottom.deltaYInt, top.deltaYInt);
    }

    @Override
    public void fillTriangle(Edge left, Edge right) {
        gI1.reset(left.v1);
        gI2.reset(right.v1);
        int factor = Rasterfi.inverse(left.dy);

        LerperFactory.gouruadLerper(RenderState.material, gI1.gouruadLerper_E, left.v1, left.v2, factor);
        LerperFactory.gouruadLerper(RenderState.material, gI2.gouruadLerper_E, right.v1, right.v2, factor);

        scan(gI1, gI2, left.isOnLeft, left.yStart, left.deltaYInt);
    }

    private void scan(GouruadInterpolants left, GouruadInterpolants right, boolean isOnLeft, int yStart, int dYInt) {
        if (isOnLeft)
            scanSegment_G(left, right, yStart, dYInt);
        else
            scanSegment_G(right, left, yStart, dYInt);

    }

    private void scan(GouruadInterpolants tallest, GouruadInterpolants bottom, GouruadInterpolants top, boolean isOnLeft,
                      int bottomStarty, int topStarty, int bottomdy, int topdy) {
        if (isOnLeft) {
            scanSegment_G(tallest, bottom, bottomStarty, bottomdy);
            scanSegment_G(tallest, top, topStarty, topdy);
        } else {
            scanSegment_G(bottom, tallest, bottomStarty, bottomdy);
            scanSegment_G(top, tallest, topStarty, topdy);
        }
    }

    private void scanSegment_G(GouruadInterpolants left, GouruadInterpolants right, int y, int yChange) {
        int i = 1;
        while (i <= yChange) {
            rasterizer_G.rasterizeRow(left, right, y, fragColor, fragUtil);
            left.gouruadLerper_E.lerp(left);
            right.gouruadLerper_E.lerp(right);
            ++y;
            ++i;
        }
    }
}