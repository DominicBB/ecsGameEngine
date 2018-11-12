package systems.drawSystems;

import util.geometry.Vector2D;
import util.geometry.Vector3D;


public class Edge {
    public Gradients gradients;

//    public Vector3D c;
//    public final Vector3D cStep;

    public final Vector3D v1;
    public final Vector3D v2;

    public float x;
    public final float xStep;

    public final int yStart;
    public final int yEnd;

    public final float deltaX;
    public final float deltaY;
    public final float deltaYceil;
    public final int deltaYInt;

//    public float z;
//    public final float zStep;

    public float w;
    public final float wStep;

    public float u;
    public final float uStep;

    public float v;
    public final float vStep;

    public int handiness;


    public Edge(Vector3D v1, Vector3D v2, Vector2D t1, Vector2D t2, int handiness) {
        this.v1 = v1;
        this.v2 = v2;
        this.handiness = handiness;

        float ceily1 = (float) Math.ceil(v1.y);
        float ceily2 = (float) Math.ceil(v2.y);

        yStart = (int) ceily1;
        yEnd = (int) ceily2;

        //difference
        deltaX = v2.x - v1.x;
        deltaY = v2.y - v1.y;
        deltaYInt = (int) v2.y - (int) v1.y;
        deltaYceil = ceily2 - ceily1;

        //steps
        xStep = deltaX / deltaYceil;
//        zStep = (v2.z - v1.z) / deltaYceil;
//        cStep = (c2.minus(c1)).divide(deltaYceil);
        wStep = (v2.w - v1.w) / deltaYceil;
        uStep = (t2.x - t1.x) / deltaYceil;
        vStep = (t2.y - t1.y) / deltaYceil;

        //the truncated amount y
        float yStartErr = yStart - v1.y;

        //start values
        x = v1.x; /*+ yStartErr * xStep;*/
//        z = v1.z;
//        c = c1;
        w = v1.w;
        u = t1.x;
        v = t1.y;


        //the truncated amount x
        float xStartError = x - v1.x;

    }


    public void setGradients(Gradients gradients) {
        this.gradients = gradients;
    }

    public void step() {
        x += xStep;
        w += wStep;
        u += uStep;
        v += vStep;
//        z += zStep;
//        c = c.plus(cStep);

    }
}
