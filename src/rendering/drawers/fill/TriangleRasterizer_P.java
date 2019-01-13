package rendering.drawers.fill;

import rendering.renderUtil.Edges.Edge;
import rendering.renderUtil.RenderState;
import rendering.renderUtil.interpolation.LerperFactory;
import rendering.renderUtil.interpolation.phong.PhongInterpolants;
import rendering.renderUtil.interpolation.phong.PhongLerper_E;
import util.Mathf.Mathf3D.Vec4fi;

public class TriangleRasterizer_P implements ITriRasterizer {
    private Rasterizer_P rasterizer_P = new Rasterizer_P();
    private final PhongInterpolants
            gI1 = new PhongInterpolants(new PhongLerper_E()),
            gI2 = new PhongInterpolants(new PhongLerper_E()),
            gI3 = new PhongInterpolants(new PhongLerper_E());

    private final Vec4fi fragColor, fragUtil;

    public TriangleRasterizer_P(Vec4fi fragColor, Vec4fi fragUtil) {
        this.fragColor = fragColor;
        this.fragUtil = fragUtil;
    }

    @Override
    public void fillTriangle(Edge tallest, Edge bottom, Edge top) {
        gI1.reset(tallest.v1);
        gI2.reset(bottom.v1);
        gI3.reset(top.v1);

        LerperFactory.phongLerper(RenderState.material, gI1.phongLerper_e, tallest.v1, tallest.v2, Rasterfi.inverse(tallest.dy));
        LerperFactory.phongLerper(RenderState.material, gI2.phongLerper_e, bottom.v1, bottom.v2, Rasterfi.inverse(bottom.dy));
        LerperFactory.phongLerper(RenderState.material, gI3.phongLerper_e, top.v1, top.v2, Rasterfi.inverse(top.dy));

        scan(gI1, gI2, gI3, tallest.isOnLeft, bottom.yStart, top.yStart, bottom.deltaYInt, top.deltaYInt);
    }

    @Override
    public void fillTriangle(Edge left, Edge right) {
        gI1.reset(left.v1);
        gI2.reset(right.v1);
        int factor = Rasterfi.inverse(left.dy);

        LerperFactory.phongLerper(RenderState.material, gI1.phongLerper_e, left.v1, left.v2, factor);
        LerperFactory.phongLerper(RenderState.material, gI2.phongLerper_e, right.v1, right.v2, factor);

        scan(gI1, gI2, left.isOnLeft, left.yStart, left.deltaYInt);
    }

    private void scan(PhongInterpolants left, PhongInterpolants right, boolean isOnLeft, int yStart, int dYInt) {
        if (isOnLeft)
            scanSegment_P(left, right, yStart, dYInt);
        else
            scanSegment_P(right, left, yStart, dYInt);

    }

    private void scan(PhongInterpolants tallest, PhongInterpolants bottom, PhongInterpolants top, boolean isOnLeft,
                      int bottomStarty, int topStarty, int bottomdy, int topdy) {
        if (isOnLeft) {
            scanSegment_P(tallest, bottom, bottomStarty, bottomdy);
            scanSegment_P(tallest, top, topStarty, topdy);
        } else {
            scanSegment_P(bottom, tallest, bottomStarty, bottomdy);
            scanSegment_P(top, tallest, topStarty, topdy);
        }

    }

    private void scanSegment_P(PhongInterpolants left, PhongInterpolants right, int y, int yChange) {
        int i = 1;
        while (i <= yChange) {
            rasterizer_P.rasterizeRow(left, right, y, fragColor, fragUtil);
            left.phongLerper_e.lerp(left);
            right.phongLerper_e.lerp(right);
            ++y;
            ++i;
        }
    }
}
