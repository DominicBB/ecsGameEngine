package Rendering.renderUtil.Gradients;

import Rendering.renderUtil.VertexOut;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

public class GradientFactory {

    private static final Gradients gradients = new Gradients();

    public static Gradients calcGradients(VertexOut minYVert, VertexOut midYVert, VertexOut maxYVert) {
        /*gradients.minYVert = minYVert;
        gradients.midYVert = midYVert;
        gradients.maxYVert = maxYVert;*/

        float invdX = 1.0f /
                (((midYVert.p_proj.x - maxYVert.p_proj.x) *
                        (minYVert.p_proj.y - maxYVert.p_proj.y)) -
                        ((minYVert.p_proj.x - maxYVert.p_proj.x) *
                                (midYVert.p_proj.y - maxYVert.p_proj.y)));

        float invdY = -invdX;

return gradients;
    }

    /*private static void calcVec2Step(Vector2D[] values, float invdY, float invdX, Vector2D out) {
        out.x = (((values[1].x - values[2].x) *
                (gradients.minYVert.p_proj.y - gradients.maxYVert.p_proj.y)) -
                ((values[0].x - values[2].x) *
                        (gradients.midYVert.p_proj.y - gradients.maxYVert.p_proj.y))) * invdX;

        out.y = (((values[1].y - values[2].y) *
                (gradients.minYVert.p_proj.x - gradients.maxYVert.p_proj.x)) -
                ((values[0].y - values[2].y) *
                        (gradients.midYVert.p_proj.x - gradients.maxYVert.p_proj.x))) * invdY;

    }*/

}
