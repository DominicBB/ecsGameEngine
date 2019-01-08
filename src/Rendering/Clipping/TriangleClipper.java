package Rendering.Clipping;

import Rendering.renderUtil.VertexOut;
import util.Mathf.Mathf2D.Vec2f;
import util.Mathf.Mathf3D.Plane;
import util.Mathf.Mathf3D.Triangle;
import util.Mathf.Mathf3D.Vec4f;

import java.util.ArrayList;
import java.util.List;

public class TriangleClipper {

    private static List<VertexOut> insidePoints = new ArrayList<>();
    private static List<VertexOut> outsidePoints = new ArrayList<>();

    private static List<VertexOut> toRemove = new ArrayList<>();
    private static List<VertexOut> toAdd = new ArrayList<>();

    public static List<VertexOut> clipTriangle(List<Plane> clippingPlanes, VertexOut v1, VertexOut v2, VertexOut v3) {
        List<VertexOut> results = new ArrayList<>();
        results.clear();
        clear();
        results.add(v1);
        results.add(v2);
        results.add(v3);

        boolean allOutside;
        for (Plane plane : clippingPlanes) {
            allOutside = !checkEachTriangle(plane, results);
            updateAfterClip(results);
            if (allOutside) {
                /*if (results.size() > 1) {
                    results.remove(results.size() - 1);
                }*/
                results.clear();
                return results;
            }
        }

        return results;
    }

    private static boolean checkEachTriangle(Plane plane, List<VertexOut> vertices) {
        int triangleCompletlyOutsideCount = 0;
        final int end = vertices.size() - 1;
        for (int i = 0; i < end; i += 3) {
            linesToClip(plane, vertices.get(i));
            linesToClip(plane, vertices.get(i + 1));
            linesToClip(plane, vertices.get(i + 2));

            if (!clippedTriangle(plane, vertices.get(i), vertices.get(i + 1), vertices.get(i + 2))) {
                triangleCompletlyOutsideCount++;
            }
            clear();
        }
        return triangleCompletlyOutsideCount == 0;
    }

    private static void updateAfterClip(List<VertexOut> vertices) {
        vertices.removeAll(toRemove);
        vertices.addAll(toAdd);
        toAdd.clear();
        toRemove.clear();
    }

    private static void clear() {
        outsidePoints.clear();
        insidePoints.clear();
    }

    private static void linesToClip(Plane plane, VertexOut v1) {

        float d = plane.distFromPlane(v1.p_proj);
        if (d < 0) {
            outsidePoints.add(v1);
            //outsideTex.add(v1);
        } else {
            insidePoints.add(v1);
            //insideTex.add(v1);
        }

    }

    private static boolean clippedTriangle(Plane plane, VertexOut v1, VertexOut v2, VertexOut v3) {
        //no clipping needed if everything is inside
        if (outsidePoints.isEmpty()) {
            return true;
        }
        toRemove.add(v1);
        toRemove.add(v2);
        toRemove.add(v3);
        //whole triangle is outside
        if (outsidePoints.size() == 3) {
            return false;
        }

        VertexOut[] clippedTriVertices = new VertexOut[3];

        float scaler = calculateScaler(plane, insidePoints.get(0).p_proj, outsidePoints.get(0).p_proj);
        clippedTriVertices[0] = intersectPoint(scaler, insidePoints.get(0), outsidePoints.get(0));

        if (outsidePoints.size() == 1) {//create two new triangles
            VertexOut[] vertices2 = new VertexOut[3];
            vertices2[0] = insidePoints.get(0);
            vertices2[1] = insidePoints.get(1);
            vertices2[2] = clippedTriVertices[0];
            vertices2 = Triangle.orderCW(vertices2);
            addVertices(vertices2);

            scaler = calculateScaler(plane, insidePoints.get(1).p_proj, outsidePoints.get(0).p_proj);

            clippedTriVertices[1] = intersectPoint(scaler, insidePoints.get(1), outsidePoints.get(0));
            clippedTriVertices[2] = insidePoints.get(1);

        } else {//one new triangle
            scaler = calculateScaler(plane, insidePoints.get(0).p_proj, outsidePoints.get(1).p_proj);

            clippedTriVertices[1] = intersectPoint(scaler, insidePoints.get(0), outsidePoints.get(1));
            clippedTriVertices[2] = insidePoints.get(0);
        }

        clippedTriVertices = Triangle.orderCW(clippedTriVertices);
        addVertices(clippedTriVertices);
        return true;
    }

    private static void addVertices(VertexOut[] vertices) {
        for (int i = 0; i < vertices.length; i++) {
            toAdd.add(vertices[i]);
        }
    }

    private static float calculateScaler(Plane plane, Vec4f from, Vec4f to) {
        float planeD = -plane.normal.dotProduct(plane.pointOnPlane);
        float fDiff = from.dotProduct(plane.normal);
        float tDiff = to.dotProduct(plane.normal);
        return -((-planeD - fDiff) / (tDiff - fDiff));
    }

    private static VertexOut intersectPoint(float scaler, VertexOut inside, VertexOut outside) {

        //TODO: optimise with regards to material
        Vec4f newP_proj = intersectPoint(scaler, inside.p_proj, outside.p_proj);
        Vec2f newTexCoord = intersectPoint(scaler, inside.texCoord, outside.texCoord);
        Vec2f newSpecCoord = inside.specCoord;/* intersectPoint(scaler, inside.specCoord, outside.specCoord);*/
        float spec = 1f;/* intersectPoint(scaler, inside.spec, outside.spec);*/
        Vec4f sColor = intersectPoint(scaler, inside.surfaceColor, outside.surfaceColor);
        /*Vec4f newP_ws = intersectPoint(scaler, inside.p_ws, outside.p_ws);
        Vec4f newN_ws = intersectPoint(scaler, inside.n_ws, outside.n_ws);*/
        float invZ = intersectPoint(scaler, inside.invW, outside.invW);


        return new VertexOut(newP_proj, newTexCoord, newSpecCoord, spec, sColor, null, null, invZ);
    }

    private static Vec4f intersectPoint(float scaler, Vec4f inside, Vec4f outside) {
        Vec4f dif = inside.minus(outside);
        dif.scale(scaler);
        return inside.plus(dif);
    }

    private static Vec2f intersectPoint(float scaler, Vec2f inside, Vec2f outside) {
        Vec2f dif = inside.minus(outside);
        dif.scale(scaler);
        return inside.plus(dif);
    }

    private static float intersectPoint(float scaler, float inside, float outside) {
        float dif = inside - outside;
        return inside + (dif * scaler);
    }
}
