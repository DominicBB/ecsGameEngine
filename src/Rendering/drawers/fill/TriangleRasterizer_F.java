package Rendering.drawers.fill;

import Rendering.renderUtil.Edges.Edge;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.interpolation.LerperFactory;
import Rendering.renderUtil.interpolation.flat.FlatInterpolants;
import Rendering.renderUtil.interpolation.flat.FlatLerper_E;
import util.Mathf.Mathf3D.Vector3D;

public class TriangleRasterizer_F implements ITriRasterizer {
    private final Rasterizer_F rasterizer_f = new Rasterizer_F();
    private final FlatInterpolants
            gI1 = new FlatInterpolants(new FlatLerper_E()),
            gI2 = new FlatInterpolants(new FlatLerper_E()),
            gI3 = new FlatInterpolants(new FlatLerper_E());

    private final Vector3D fragColor, fragUtil;


    public TriangleRasterizer_F(Vector3D fragColor, Vector3D fragUtil) {
        this.fragColor = fragColor;
        this.fragUtil = fragUtil;
    }

    @Override
    public void fillTriangle(Edge tallest, Edge bottom, Edge top) {
        setTriData(tallest);

        gI1.reset(tallest.v1);
        gI2.reset(bottom.v1);
        gI3.reset(top.v1);

        LerperFactory.flatLerper(RenderState.material, gI1.flatLerper_e, tallest.v1, tallest.v2, 1f / tallest.dy);
        LerperFactory.flatLerper(RenderState.material, gI2.flatLerper_e, bottom.v1, bottom.v2, 1f / bottom.dy);
        LerperFactory.flatLerper(RenderState.material, gI3.flatLerper_e, top.v1, top.v2, 1f / top.dy);

        scan(gI1, gI2, gI3, tallest.isOnLeft, bottom.yStart, top.yStart, bottom.deltaYInt, top.deltaYInt);
    }

    @Override
    public void fillTriangle(Edge left, Edge right) {
        setTriData(left);

        gI1.reset(left.v1);
        gI2.reset(right.v1);
        float factor = 1f / left.dy;

        LerperFactory.flatLerper(RenderState.material, gI1.flatLerper_e, left.v1, left.v2, factor);
        LerperFactory.flatLerper(RenderState.material, gI2.flatLerper_e, right.v1, right.v2, factor);

        scan(gI1, gI2, left.isOnLeft, left.yStart, left.deltaYInt);
    }

    private void scan(FlatInterpolants left, FlatInterpolants right, boolean isOnLeft, int yStart, int dYInt) {
        if (isOnLeft)
            scanSegment_F(left, right, yStart, dYInt);
        else
            scanSegment_F(right, left, yStart, dYInt);

    }

    private void scan(FlatInterpolants tallest, FlatInterpolants bottom, FlatInterpolants top, boolean isOnLeft,
                      int bottomStarty, int topStarty, int bottomdy, int topdy) {
        if (isOnLeft) {
            scanSegment_F(tallest, bottom, bottomStarty, bottomdy);
            scanSegment_F(tallest, top, topStarty, topdy);
        } else {
            scanSegment_F(bottom, tallest, bottomStarty, bottomdy);
            scanSegment_F(top, tallest, topStarty, topdy);
        }

    }

    private void scanSegment_F(FlatInterpolants left, FlatInterpolants right, int y, int yChange) {
        int i = 1;
        while (i <= yChange) {
            rasterizer_f.rasterizeRow(left, right, y, fragColor, fragUtil);
            left.flatLerper_e.lerp(left);
            right.flatLerper_e.lerp(right);
            ++y;
            ++i;
        }
    }

    private void setTriData(Edge edge){
        rasterizer_f.surfaceColor = edge.v1.surfaceColor;
        rasterizer_f.spec = edge.v1.spec;
    }
}
