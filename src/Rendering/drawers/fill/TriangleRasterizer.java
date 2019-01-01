package Rendering.drawers.fill;

import Rendering.renderUtil.Edges.Edge;
import Rendering.renderUtil.Edges.EdgeFactory;
import Rendering.renderUtil.VertexOut;
import Rendering.renderUtil.threading.threadSaftey.RenderLocks;
import util.FloatWrapper;
import util.Mathf.Mathf;
import util.Mathf.Mathf2D.Bounds2D.AABoundingRect;
import util.Mathf.Mathf3D.Triangle;
import util.Mathf.Mathf3D.Vector3D;

import java.util.List;


public class TriangleRasterizer {
    private final EdgeFactory edgeFactory = new EdgeFactory();
    private final Edge e1 = Edge.newEmpty(), e2 = Edge.newEmpty(), e3 = Edge.newEmpty();
    private final VertexOut[] edgeVertices = new VertexOut[6];
    private final Rasterizer rasterizer = new Rasterizer();
    private final FloatWrapper xMax = new FloatWrapper(0f), xMin = new FloatWrapper(0f);

    private List<Edge> edgesTODO;
    private List<Edge> edgesTODODouble;

    private AABoundingRect boundingRect;

    public void fillTriangle(VertexOut v1, VertexOut v2, VertexOut v3) {
        setUpEdges(v1, v2, v3);
    }

    public void scan(Edge leftEdge, Edge rightEdge) {
        if (leftEdge.isOnLeft)
            scanSegment(leftEdge, rightEdge, leftEdge.yStart, leftEdge.deltaYInt);
        else
            scanSegment(rightEdge, leftEdge, rightEdge.yStart, rightEdge.deltaYInt);

    }

    public void scan(Edge tallestEdge, Edge bottomEdge, Edge topEdge) {
        if (tallestEdge.isOnLeft) {
            scanSegment(tallestEdge, bottomEdge, bottomEdge.yStart, bottomEdge.deltaYInt);
            scanSegment(tallestEdge, topEdge, topEdge.yStart, topEdge.deltaYInt);
        } else {
            scanSegment(bottomEdge, tallestEdge, bottomEdge.yStart, bottomEdge.deltaYInt);
            scanSegment(topEdge, tallestEdge, topEdge.yStart, topEdge.deltaYInt);
        }

    }

    private void scanSegment(Edge left, Edge right, int y, int yChange) {
        int i = 1;
        //if ShaderA
        //LERPFACTORYSHIT()
        while (i <= yChange) {
            rasterizer.rasterizeRow(left, right, y);
            left.interpolants.lerp();
            right.interpolants.lerp();
            ++y;
            ++i;
        }
    }

    private void setUpEdges(VertexOut maxY, VertexOut midY, VertexOut minY) {
        int eIndex = 0;
        VertexOut temp;
        if (maxY.p_proj.y < midY.p_proj.y) {
            temp = maxY;
            maxY = midY;
            midY = temp;
        }

        if (midY.p_proj.y < minY.p_proj.y) {
            temp = midY;
            midY = minY;
            minY = temp;
        }

        if (maxY.p_proj.y < midY.p_proj.y) {
            temp = maxY;
            maxY = midY;
            midY = temp;
        }

        float ceil1 = (float) Mathf.fastCeil(maxY.p_proj.y);
        float ceil2 = (float) Mathf.fastCeil(midY.p_proj.y);
        float ceil3 = (float) Mathf.fastCeil(minY.p_proj.y);
        float dy1 = ceil1 - ceil3;
        float dy2 = ceil2 - ceil3;
        float dy3 = ceil1 - ceil2;

        int horizontalLineCount = 0;
        if (dy1 == 0.0f)
            ++horizontalLineCount;
        else {
            edgeVertices[eIndex++] = minY;
            edgeVertices[eIndex++] = maxY;
        }
        if (dy2 == 0.0f)
            ++horizontalLineCount;
        else {
            edgeVertices[eIndex++] = minY;
            edgeVertices[eIndex++] = midY;
        }
        if (dy3 == 0.0f)
            ++horizontalLineCount;
        else {
            edgeVertices[eIndex++] = midY;
            edgeVertices[eIndex] = maxY;
        }


        if (horizontalLineCount >= 2) {
            boundingRect.set(0f, 0f, 0, 0f);
            return;
        }

        calcMinMaxX(xMin, xMax, maxY.p_proj, midY.p_proj, minY.p_proj);

        if (horizontalLineCount == 1) {
            setUpForOneHorizontal();
            return;
        }
        setUpForNoHorizontal(maxY, midY, minY, dy1, dy2, dy3);
    }

    private void setUpForNoHorizontal(VertexOut maxY, VertexOut midY, VertexOut minY, float dy1, float dy2, float dy3) {
        boolean isOnLeft = Triangle.z_crossProd(minY.p_proj, maxY.p_proj, midY.p_proj) < 0f;
        boundingRect.set(maxY.p_proj.y, minY.p_proj.y, xMax.value, xMin.value);

        if (RenderLocks.BRintersect()) {
            storeEdges(maxY, midY, minY, dy1, dy2, dy3, isOnLeft);
            return;
        }
        reuseEdges(maxY, midY, minY, dy1, dy2, dy3, isOnLeft);
        scan(e1, e2, e3);
    }

    private void setUpForOneHorizontal() {
        VertexOut v1 = edgeVertices[0], v2 = edgeVertices[1], v3 = edgeVertices[2], v4 = edgeVertices[3];

        float dy1 = v2.p_proj.y - v1.p_proj.y;
        float dy2 = v4.p_proj.y - v3.p_proj.y;
        float yMax, yMin;
        if (v2.p_proj.y > v4.p_proj.y) {
            yMax = v2.p_proj.y;
            yMin = v4.p_proj.y;
        } else {
            yMin = v2.p_proj.y;
            yMax = v4.p_proj.y;
        }

        boolean isOnLeft = v1.p_proj.x < v3.p_proj.x;
        isOnLeft |= v2.p_proj.x < v4.p_proj.x;

        boundingRect.set(yMax, yMin, xMax.value, xMin.value);

        if (RenderLocks.BRintersect()) {
            edgesTODODouble.add(edgeFactory.createEdge(v1, v2, dy1, isOnLeft));
            edgesTODODouble.add(edgeFactory.createEdge(v3, v4, dy2, !isOnLeft));
            return;
        }
        edgeFactory.reuseEdge(e1, v1, v2, dy1, isOnLeft);
        edgeFactory.reuseEdge(e2, v3, v4, dy2, !isOnLeft);
        scan(e1, e2);
    }

    private void calcMinMaxX(FloatWrapper minX, FloatWrapper maxX, Vector3D v1, Vector3D v2, Vector3D v3) {
        Vector3D max = v1, mid = v2, min = v3, temp;
        if (max.x < mid.x) {
            temp = v2;
            max = mid;
            mid = temp;
        }

        if (mid.y < min.y) {
            temp = mid;
            mid = min;
            min = temp;
        }

        if (max.y < mid.y) {
            max = mid;
        }

        minX.value = min.x;
        maxX.value = max.x - 1;
    }

    private void storeEdges(VertexOut maxY, VertexOut midY, VertexOut minY, float dy1, float dy2, float dy3,
                            boolean isOnLeft) {
        edgesTODO.add(edgeFactory.createEdge(minY, maxY, dy1, isOnLeft));
        edgesTODO.add(edgeFactory.createEdge(minY, midY, dy2, !isOnLeft));
        edgesTODO.add(edgeFactory.createEdge(midY, maxY, dy3, !isOnLeft));
    }

    private void reuseEdges(VertexOut maxY, VertexOut midY, VertexOut minY, float dy1, float dy2, float dy3,
                            boolean isOnLeft) {

        edgeFactory.reuseEdge(e1, minY, maxY, dy1, isOnLeft);
        edgeFactory.reuseEdge(e2, minY, midY, dy2, !isOnLeft);
        edgeFactory.reuseEdge(e3, midY, maxY, dy3, !isOnLeft);
    }

    public void setEdgesTODO(List<Edge> edgesTODO) {
        this.edgesTODO = edgesTODO;
    }

    public void setEdgesTODODouble(List<Edge> edgesTODODouble) {
        this.edgesTODODouble = edgesTODODouble;
    }

    public void setBoundingRect(AABoundingRect boundingRect) {
        this.boundingRect = boundingRect;
    }
}
