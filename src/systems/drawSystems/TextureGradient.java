package systems.drawSystems;

import util.geometry.Vector2D;

public class TextureGradient {
    public float u;
    public final float deltaU;

    public float v;
    public final float deltaV;



    public TextureGradient(Vector2D t1, Vector2D t2){
        u = t1.x;
        v = t1.y;

        deltaU = t2.x - u;
        deltaV = t2.y - v;


    }
}
