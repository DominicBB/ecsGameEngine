package rendering.tileRaster;

import rendering.renderUtil.VertexOut;
import util.FloatWrapper;
import util.mathf.Mathf2D.Vec2f;
import util.mathf.Mathf3D.Vec4f;

public class FillTriangle {
    private final FloatWrapper
            min_x = new FloatWrapper(0f),
            max_x = new FloatWrapper(0f),
            min_y = new FloatWrapper(0f),
            max_y = new FloatWrapper(0f);

    public void fillTriangle(VertexOut v0, VertexOut v1, VertexOut v2) {
        calcMinMax(min_y, max_y, v0.p_proj.y, v1.p_proj.y, v2.p_proj.y);
        calcMinMax(min_x, max_x, v0.p_proj.x, v1.p_proj.x, v2.p_proj.x);

        //TODO: winding order
        float
                inner_01 = v0.p_proj.y - v1.p_proj.y, outer_01 = v1.p_proj.x - v0.p_proj.x,
                inner_12 = v1.p_proj.y - v2.p_proj.y, outer_12 = v2.p_proj.x - v1.p_proj.x,
                inner_20 = v2.p_proj.y - v0.p_proj.y, outer_20 = v0.p_proj.x - v2.p_proj.x;


        float
                bias0 = fillRuleBias(v1.p_proj.y, v2.p_proj.y),
                bias1 = fillRuleBias(v2.p_proj.y, v0.p_proj.y),
                bias2 = fillRuleBias(v0.p_proj.y, v1.p_proj.y);

        //TODO: check winding order
        Vec2f minPoint = new Vec2f(min_x.value, min_y.value);
        float
                bary0_row = orient2d(v0.p_proj, v1.p_proj, minPoint) + bias0,
                bary1_row = orient2d(v1.p_proj, v2.p_proj, minPoint) + bias1,
                bary2_row = orient2d(v2.p_proj, v0.p_proj, minPoint) + bias2;
        float bary0, bary1, bary2;


        //TODO: fill convention
        for (int y = (int) min_y.value, yMax = (int) max_y.value; y <= yMax; y++) {

            bary0 = bary0_row;
            bary1 = bary1_row;
            bary2 = bary2_row;

            for (int x = (int) min_x.value, xMax = (int) max_x.value; x < xMax; x++) {

                if (isPixelInsideTriangle(bary0, bary1, bary2)) {
                    //renderPixel
                }

                bary0 += inner_01;
                bary1 += inner_12;
                bary2 += inner_20;
            }

            bary0_row += outer_01;
            bary1_row += outer_12;
            bary2_row += outer_20;
        }

    }

    //might be too slow
    private boolean isPixelInsideTriangle(float bary0, float bary1, float bary2) {
        return (Float.floatToRawIntBits(bary0) | Float.floatToRawIntBits(bary1) | Float.floatToRawIntBits(bary2)) >= 0;
    }

    private float orient2d(Vec4f v1, Vec4f v2, Vec2f p) {
        return (v2.x - v1.x) * (p.y - v1.y) - (v2.y - v1.y) * (p.x - v1.x);
    }

    private void calcMinMax(FloatWrapper min_out, FloatWrapper max_out, float max, float mid, float min) {
        float temp;
        if (max < mid) {
            temp = mid;
            max = mid;
            mid = temp;
        }

        if (mid < min) {
            temp = mid;
            mid = min;
            min = temp;
        }

        if (max < mid) {
            max = mid;
        }

        min_out.value = min;
        max_out.value = max;
    }


    private float fillRuleBias(float v1_y, float v2_y) {
        return ((v2_y - v1_y) > -0f) ? 0f : -1f;
    }


}
