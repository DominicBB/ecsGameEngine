package systems.drawSystems;


import util.geometry.Polygon;
import util.geometry.Vector3D;

public abstract class Gradients {

   /* public Vector3D c;
    public final Vector3D cStep;

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

    public float z;
    public final float zStep;

    public int handiness;*/


    public Gradients(Edge tallestEdge, Edge bottomEdge, Edge topEdge, Polygon p) {

    }
}


 /*float invdX = 1.0f /
                (((topLine.x - tallestLine.x) *
                        (bottomLine.y - tallestLine.y) -
                        ((bottomLine.x - tallestLine.x) *
                                topLine.y - tallestLine.y)));
        float invdY = -invdX;

        Vector3D dC = (((colors[1].minus(colors[2])).scale(
                (bottomLine.y - tallestLine.y)).minus(
                ((colors[0].minus(colors[2])).scale(
                        topLine.y - tallestLine.y)))));

        colorXStep = dC.scale(invdX);
        colorYStep = dC.scale(invdY);*/
