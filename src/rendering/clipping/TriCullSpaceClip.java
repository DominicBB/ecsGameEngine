package rendering.clipping;

import rendering.renderUtil.VertexOut;

import java.util.ArrayList;
import java.util.List;

public class TriCullSpaceClip {

    private final List<VertexOut> tempL = new ArrayList<>();
    private final List<VertexOut> outsidePoints = new ArrayList<>();

    public void clipNonAlloc(List<VertexOut> clippedVs, VertexOut v0, VertexOut v1, VertexOut v2) {
        clip(clippedVs, v0, v1, v2);
    }

    public List<VertexOut> clip(VertexOut v0, VertexOut v1, VertexOut v2) {
        List<VertexOut> vertexOuts = new ArrayList<>();
        clip(vertexOuts, v0, v1, v2);
        return vertexOuts;
    }

    private void clip(List<VertexOut> clippedVs, VertexOut v0, VertexOut v1, VertexOut v2) {
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

        clipVerticesAgainstPlanes(2, clippedVs);//z plane
        if (clippedVs.isEmpty())
            return;

        clipVerticesAgainstPlanes(0, clippedVs);//x plane
        if (clippedVs.isEmpty())
            return;
        clipVerticesAgainstPlanes(1, clippedVs);//y plane


    }

    private void clipVerticesAgainstPlanes(int planeComponent, List<VertexOut> clippedVs) {
        clipAgainstPlane(planeComponent, 1f, clippedVs, tempL);
        if (tempL.isEmpty())
            return;
        clipAgainstPlane(planeComponent, -1f, tempL, clippedVs);
    }

    /**
     * @param planeComponent, a number representing xyz with 012 respectively
     * @param planeF, 1 or -1 to represent either of the parallel planes
     * @param in, all vertices that need to be clipped, in clockwise order
     * @param out, all the clipped vertices, in clockwise order
     */
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

        //clip final line of triangle
        VertexOut c = in.get(0);
        float currComponent = c.p_proj.getComponentValue(planeComponent) * planeF;
        clipVertex(previous, prevInside, c, prevComponent, currComponent, out);

        in.clear();
    }

    /**
     * Clip a single line of a primitive
     * @param prevComponent, a number representing xyz with 012 respectively
     * @param currComponent, a number representing xyz with 012 respectively
     */
    private static boolean clipVertex(VertexOut previous, boolean prevInside, VertexOut current,
                                      float prevComponent, float currComponent,
                                      List<VertexOut> res) {

        boolean isVertexInside = isVertInside(currComponent, current.p_proj.w);

        if (isVertexInside ^ prevInside)
            res.add(calculateClippedVertex(previous, current, prevComponent, currComponent));

        if (isVertexInside)
            res.add(current);

        return isVertexInside;
    }

    private static VertexOut calculateClippedVertex(VertexOut previous, VertexOut current, float pComponent,
                                                    float cComponent) {

        float diff = previous.p_proj.w - pComponent;
        float lerpAmt = diff / (diff - (current.p_proj.w - cComponent));
        return previous.lerp(current, lerpAmt);
    }

    private boolean allInside(VertexOut v0, VertexOut v1, VertexOut v2) {
        return allInside(v0) & allInside(v1) & allInside(v2);
    }

    private boolean allInside(VertexOut v) {
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

    private void clear() {
        tempL.clear();
        outsidePoints.clear();
    }

    private static boolean isVertInside(float component, float w) {
        return component <= w;
    }
}
