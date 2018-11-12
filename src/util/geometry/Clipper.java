package util.geometry;

import components.Camera;
import core.Window;

import java.util.ArrayList;
import java.util.List;

public class Clipper {

    private static List<Vector2D> insideTex = new ArrayList<>();
    private static List<Vector2D> outsideTex = new ArrayList<>();

    private static List<Vector3D> insidePoints = new ArrayList<>();
    private static List<Vector3D> outsidePoints = new ArrayList<>();

    private static List<Triangle> triangles = new ArrayList<>();

    private static List<Triangle> toRemove = new ArrayList<>();
    private static List<Triangle> toAdd = new ArrayList<>();

    public static List<Triangle> clipTriangle(List<Plane> planes, Triangle t) {
        triangles.clear();
        clear();
        triangles.add(t);

        for(Plane plane: planes){
            if(!checkEachTriangle(plane)){
                return triangles;
            }
            clear();
        }
        /*//near plane
        Vector3D nPlane = new Vector3D(0f, 0f, 1f);
        Vector3D pPlane = new Vector3D(0f, 0f, fNear);
        if(!checkEachTriangle(nPlane, pPlane)){
            return triangles;
        }

        clear();
        //left plane
        nPlane = new Vector3D(1f, 0f, 0f);
        pPlane = new Vector3D(0f, 0f, 0f);
        if(!checkEachTriangle(nPlane, pPlane)){
            return triangles;
        }

        clear();
        //right plane
        nPlane = new Vector3D(-1f, 0f, 0f);
        pPlane = new Vector3D(Window.defaultWidth-1.5f, 0f, 0f);
        if(!checkEachTriangle(nPlane, pPlane)){
            return triangles;
        }

        clear();
        //bottom plane
        nPlane = new Vector3D(0f, 1f, 0f);
        pPlane = new Vector3D(0f, 0f, 0f);
        if(!checkEachTriangle(nPlane, pPlane)){
            return triangles;
        }

        clear();
        //top plane
        nPlane = new Vector3D(0f, -1f, 0f);
        pPlane = new Vector3D(0f, Window.defaultHeight-1.5f, 0f);
        if(!checkEachTriangle(nPlane, pPlane)){
            return triangles;
        }*/

        return triangles;
    }

    private static boolean checkEachTriangle(Plane plane){
        int i = 0;
        for(Triangle triangle: triangles){
            linesToClip(plane, triangle);
            if (!clippedTriangle(plane, triangle)){
                updateAfterClip();
                return false;
            }
            i++;
        }
        updateAfterClip();
        return true;
    }

    private static void updateAfterClip(){
        triangles.removeAll(toRemove);
        triangles.addAll(toAdd);
        toAdd.clear();
    }

    private static void clear(){
        insideTex.clear();
        outsidePoints.clear();
        outsideTex.clear();
        insidePoints.clear();
        toRemove.clear();
    }

    private static void linesToClip(Plane plane, Triangle t) {
        float d;
        for (int i = 0; i < 3; i++) {
            d = plane.distFromPlane(t.vectors[i]);
            if (d < 0) {
                outsidePoints.add(t.vectors[i]);
                outsideTex.add(t.textures[i]);
            } else {
                insidePoints.add(t.vectors[i]);
                insideTex.add(t.textures[i]);
            }
        }
    }

    private static boolean clippedTriangle(Plane plane, Triangle t) {
        if (outsidePoints.isEmpty()) {
            return true;
        }
        toRemove.add(t);
        if (outsidePoints.size() == 3) {
            return false;
        }
        Vector3D[] vectors = new Vector3D[3];

        Vector2D[] textures = new Vector2D[3];

        Triangle newT2;

        float scaler = calculateScaler(plane, insidePoints.get(0), outsidePoints.get(0));
        vectors[0] = intersectPoint(scaler, insidePoints.get(0), outsidePoints.get(0));
        textures[0] = intersectPoint(scaler, insideTex.get(0), outsideTex.get(0));

        if (outsidePoints.size() == 1) {//create two new triangles
            scaler = calculateScaler(plane, insidePoints.get(1), outsidePoints.get(0));
            vectors[1] = intersectPoint(scaler, insidePoints.get(1), outsidePoints.get(0));
            textures[1] = intersectPoint(scaler, insideTex.get(1), outsideTex.get(0));

            textures[2] = insideTex.get(1);
            vectors[2] = insidePoints.get(1);

            newT2 = new Triangle(insidePoints.get(0), insidePoints.get(1), vectors[0],
                    insideTex.get(0), insideTex.get(1), textures[0], t);
            newT2 =Pipeline.orderCW(newT2);

            toAdd.add(newT2);

        } else {//one new
            scaler = calculateScaler(plane, insidePoints.get(0), outsidePoints.get(1));
            vectors[1] = intersectPoint(scaler, insidePoints.get(0), outsidePoints.get(1));
            textures[1] = intersectPoint(scaler, insideTex.get(0), outsideTex.get(1));

            textures[2] = outsideTex.get(0);
            vectors[2] = insidePoints.get(0);
        }

        Triangle newT = new Triangle(vectors,textures, t);
        newT = Pipeline.orderCW(newT);
        toAdd.add(newT);
        return true;
    }

    private static float calculateScaler(Plane plane, Vector3D from, Vector3D to) {
        float planeD = -plane.normal.dotProduct(plane.pointOnPlane);
        float fDiff = from.dotProduct(plane.normal);
        float tDiff = to.dotProduct(plane.normal);
        return -((-planeD - fDiff) / (tDiff - fDiff));
    }

    private static Vector3D intersectPoint(float scaler, Vector3D from, Vector3D to) {

        Vector3D fromTo = from.minus(to);
        Vector3D fromToIntersect = fromTo.scale(scaler);
        return from.plus(fromToIntersect);
    }

    private static Vector2D intersectPoint(float scaler, Vector2D from, Vector2D to) {
        Vector2D fromTo = from.minus(to);
        Vector2D fromToIntersect = fromTo.scale(scaler);
        return from.plus(fromToIntersect);
    }
}
