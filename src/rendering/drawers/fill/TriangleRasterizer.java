package rendering.drawers.fill;

import rendering.renderUtil.edges.Edge;
import rendering.renderUtil.edges.EdgeFactory;
import rendering.renderUtil.VertexOut;
import rendering.renderUtil.threading.threadSaftey.RenderLocks;
import rendering.shaders.ShaderType;
import util.FloatWrapper;
import util.mathf.Mathf;
import util.mathf.Mathf2D.Bounds2D.AABoundingRect;
import util.mathf.Mathf3D.Triangle;
import util.mathf.Mathf3D.Vec4f;

import java.util.List;


public class TriangleRasterizer {
    private final Edge e1 = Edge.newEmpty(), e2 = Edge.newEmpty(), e3 = Edge.newEmpty();
    private final VertexOut[] edgeVertices = new VertexOut[6];
    private final FloatWrapper xMax = new FloatWrapper(0f), xMin = new FloatWrapper(0f);

    private final Vec4f fragColor = Vec4f.newZeros(), fragUtil = Vec4f.newZeros();
    private final TriangleRasterizer_G triangleRasterizer_g = new TriangleRasterizer_G(fragColor, fragUtil);
    private final TriangleRasterizer_F triangleRasterizer_f = new TriangleRasterizer_F(fragColor, fragUtil);
    private final TriangleRasterizer_P triangleRasterizer_p = new TriangleRasterizer_P(fragColor, fragUtil);

    private ShaderType shaderType;

    private List<Edge> edgesTODO;
    private List<Edge> edgesTODODouble;

    private AABoundingRect boundingRect;

    public void fillTriangle(VertexOut v1, VertexOut v2, VertexOut v3) {
        setUpEdges(v1, v2, v3);
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

        scanEdge(e1, e2, e3);
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
            edgesTODODouble.add(EdgeFactory.createEdge(v1, v2, dy1, isOnLeft));
            edgesTODODouble.add(EdgeFactory.createEdge(v3, v4, dy2, !isOnLeft));
            return;
        }
        EdgeFactory.reuseEdge(e1, v1, v2, dy1, isOnLeft);
        EdgeFactory.reuseEdge(e2, v3, v4, dy2, !isOnLeft);

        scanEdge(e1, e2);
    }

    private void calcMinMaxX(FloatWrapper minX, FloatWrapper maxX, Vec4f v1, Vec4f v2, Vec4f v3) {
        Vec4f max = v1, mid = v2, min = v3, temp;
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
        maxX.value = max.x;
    }

    private void storeEdges(VertexOut maxY, VertexOut midY, VertexOut minY, float dy1, float dy2, float dy3,
                            boolean isOnLeft) {
        edgesTODO.add(EdgeFactory.createEdge(minY, maxY, dy1, isOnLeft));
        edgesTODO.add(EdgeFactory.createEdge(minY, midY, dy2, !isOnLeft));
        edgesTODO.add(EdgeFactory.createEdge(midY, maxY, dy3, !isOnLeft));
    }

    private void reuseEdges(VertexOut maxY, VertexOut midY, VertexOut minY, float dy1, float dy2, float dy3,
                            boolean isOnLeft) {

        EdgeFactory.reuseEdge(e1, minY, maxY, dy1, isOnLeft);
        EdgeFactory.reuseEdge(e2, minY, midY, dy2, !isOnLeft);
        EdgeFactory.reuseEdge(e3, midY, maxY, dy3, !isOnLeft);
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

    public void setShaderType(ShaderType shaderType) {
        this.shaderType = shaderType;
    }

    public void scanEdge(Edge e1, Edge e2, Edge e3) {
        switch (shaderType) {
            case GOURUAD:
                triangleRasterizer_g.fillTriangle(e1, e2, e3);
                break;
            case FLAT:
                triangleRasterizer_f.fillTriangle(e1, e2, e3);
                break;
            case PHONG:
                triangleRasterizer_p.fillTriangle(e1, e2, e3);
                break;
        }
    }

    public void scanEdge(Edge e1, Edge e2) {
        switch (shaderType) {
            case GOURUAD:
                triangleRasterizer_g.fillTriangle(e1, e2);
                break;
            case FLAT:
                triangleRasterizer_f.fillTriangle(e1, e2);
                break;
            case PHONG:
                triangleRasterizer_p.fillTriangle(e1, e2);
                break;
        }
    }
}
