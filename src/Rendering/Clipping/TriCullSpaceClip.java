package Rendering.Clipping;

import Rendering.renderUtil.VertexOut;

import java.util.ArrayList;
import java.util.List;

public class TriCullSpaceClip {

    private static final List<VertexOut> tempL = new ArrayList<>();
    private static final List<VertexOut> outsidePoints = new ArrayList<>();

    public static void clipNonAlloc(List<VertexOut> clippedVs, VertexOut v0, VertexOut v1, VertexOut v2) {
        clip(clippedVs, v0, v1, v2);
    }

    public static List<VertexOut> clip(VertexOut v0, VertexOut v1, VertexOut v2) {
        List<VertexOut> vertexOuts = new ArrayList<>();
        clip(vertexOuts, v0, v1, v2);
        return vertexOuts;
    }

    private static void clip(List<VertexOut> clippedVs, VertexOut v0, VertexOut v1, VertexOut v2) {
        clippedVs.clear();
        clear();
        boolean allInside = allInside(v0, v1, v2);
        if (outsidePoints.size() == 3)
            return;


        clippedVs.add(v0);
        clippedVs.add(v1);
        clippedVs.add(v2);
        if (allInside)
            return;

        clipVerticesAgainstPlanes(0, clippedVs);//x
        if (clippedVs.isEmpty())
            return;
        clipVerticesAgainstPlanes(1, clippedVs);//y
        if (clippedVs.isEmpty())
            return;
        clipVerticesAgainstPlanes(2, clippedVs);//z



    }

    private static void clipVerticesAgainstPlanes(int planeComponent, List<VertexOut> clippedVs) {
        clipAgainstPlane(planeComponent, 1f, clippedVs, tempL);
        if (tempL.isEmpty())
            return;
        clipAgainstPlane(planeComponent, -1f, tempL, clippedVs);
    }

    private static void clipAgainstPlane(int planeComponent, float planeF, List<VertexOut> in, List<VertexOut> out) {
        VertexOut previous = in.get(0);
        float prevComponent = previous.p_proj.getComponentValue(planeComponent) * planeF;
        boolean prevInside = isVertInside(prevComponent, previous.p_proj.w);

        for (int i = 1; i < in.size(); i++) {
            VertexOut c = in.get(i);
            float currComponent = c.p_proj.getComponentValue(planeComponent) * planeF;
            prevInside = clipVertex(previous, prevInside, c, prevComponent, currComponent, out);
            previous = c;
            prevComponent = currComponent;
        }

        //clip initial
        VertexOut c = in.get(0);
        float currComponent = c.p_proj.getComponentValue(planeComponent) * planeF;
        clipVertex(previous, prevInside, c, prevComponent, currComponent, out);

        in.clear();
    }

    private static boolean clipVertex(VertexOut previous, boolean prevInside, VertexOut c,
                                      float prevComponent, float currComponent,
                                      List<VertexOut> res) {

        boolean isVertexInside = isVertInside(currComponent, c.p_proj.w);

        if (isVertexInside ^ prevInside)
            res.add(calculateClippedVertex(previous, c, prevComponent, currComponent));

        if (isVertexInside)
            res.add(c);

        return isVertexInside;
    }

    private static VertexOut calculateClippedVertex(VertexOut previous, VertexOut current, float pComponent,
                                                    float cComponent) {

        float diff = previous.p_proj.w - pComponent;
        float lerpAmt = diff / (diff - (current.p_proj.w - cComponent));
        return previous.lerp(current, lerpAmt);
    }

    private static boolean allInside(VertexOut v0, VertexOut v1, VertexOut v2) {
        return allInside(v0) & allInside(v1) & allInside(v2);
    }

    private static boolean allInside(VertexOut v) {
        float absW = Math.abs(v.p_proj.w);
        boolean allInside = Math.abs(v.p_proj.x) <= absW &&
                Math.abs(v.p_proj.y) <= absW &&
                Math.abs(v.p_proj.z) <= absW;

        if (!allInside) {
            outsidePoints.add(v);
            return false;
        }

        return true;
    }

    private static void clear() {
        tempL.clear();
        outsidePoints.clear();
    }

    private static boolean isVertInside(float component, float w) {
        return component <= w;
    }
}
