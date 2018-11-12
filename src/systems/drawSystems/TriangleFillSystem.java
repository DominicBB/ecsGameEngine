package systems.drawSystems;

import java.util.ArrayList;
import java.util.List;

import util.Bitmap;
import util.RenderContext;

import util.geometry.Triangle;
import util.geometry.Vector3D;

class TriangleFillSystem extends Filler {


    public static void fillTriangle(Triangle t, RenderContext renderContext, Bitmap texture) {
        Filler.renderContext = renderContext;
        List<Edge> edges = new ArrayList<>(3);
        edges.add(Draw.yIncreasingEdge(t.vectors[0], t.vectors[1], t.textures[0], t.textures[1]));
        edges.add(Draw.yIncreasingEdge(t.vectors[1], t.vectors[2], t.textures[1], t.textures[2]));
        edges.add(Draw.yIncreasingEdge(t.vectors[2], t.vectors[0], t.textures[2], t.textures[0]));

        edges = removeHorizontal(edges);

        if (edges.size() == 2) {
            scan(edges.get(0), edges.get(1), texture,t.getShade());
        } else {

            Edge e1 = edges.get(0);
            Edge e2 = edges.get(1);
            Edge e3 = edges.get(2);

            if (e1HasGreatestYChange(e1, e2, e3)) {
                // e1 has greatest y change
                scan(e1, e2, e3, texture,t.getShade());

            } else if (e2HasGreatestYChange(e2, e3)) {
                // e2 has greatest y change
                scan(e2, e3, e1, texture,t.getShade());

            } else {
                // e3 has greatest y change
                scan(e3, e1, e2, texture,t.getShade());
            }
        }

    }

    private static void scan(Edge leftEdge, Edge rightEdge, Bitmap t, Vector3D shade) {
        if (leftEdge.handiness == 0) {
            scanSegment(leftEdge, rightEdge, leftEdge.yStart, leftEdge.deltaYceil, t,shade);
        } else {
            scanSegment(rightEdge, leftEdge, rightEdge.yStart, rightEdge.deltaYceil, t,shade);
        }
    }

    private static void scan(Edge tallestEdge, Edge bottomEdge, Edge topEdge, Bitmap t, Vector3D shade) {
        //swap topEdge and bottomEdge if needed
        if (bottomEdge.v2.y > topEdge.v2.y) {
            Edge temp = bottomEdge;
            bottomEdge = topEdge;
            topEdge = temp;
        }

        if (tallestEdge.handiness == 0) {
            scanSegment(tallestEdge, bottomEdge, bottomEdge.yStart, bottomEdge.deltaYceil, t, shade);

            scanSegment(tallestEdge, topEdge, topEdge.yStart, topEdge.deltaYceil, t,shade);
        } else {
            scanSegment(bottomEdge, tallestEdge, bottomEdge.yStart, bottomEdge.deltaYceil, t,shade);

            scanSegment(topEdge, tallestEdge, topEdge.yStart, topEdge.deltaYceil, t,shade);
        }


    }


    private static void scanSegment(Edge left, Edge right, int y, float yChange, Bitmap t, Vector3D shade) {
        int i = 1;
        while (i <= yChange) {
            fillRow(left, right, y, t, shade);
            left.step();
            right.step();
            y++;
            i++;
        }
    }

    private static boolean e2HasGreatestYChange(Edge e2, Edge e3) {
        return (e2.deltaY > e3.deltaY);
    }

    private static boolean e1HasGreatestYChange(Edge e1, Edge e2, Edge e3) {
        return (e1.deltaY > e2.deltaY && e1.deltaY > e3.deltaY);
    }
}
