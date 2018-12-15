package Rendering.drawers.fill;

import java.util.ArrayList;
import java.util.List;

import Rendering.Materials.Material;
import Rendering.renderUtil.Edges.Edge;
import Rendering.renderUtil.Edges.EdgeFactory;
import Rendering.renderUtil.Renderer;

import Rendering.renderUtil.VertexOut;

public class TriangleRasterizer extends Rasterizer {
    private static EdgeFactory edgeFactory = new EdgeFactory();
    private final static Edge e1 = Edge.newEmpty(), e2 = Edge.newEmpty(), e3 = Edge.newEmpty();
    private static List<Edge> edges = new ArrayList<>(3);


    public static void fillTriangle(VertexOut v1, VertexOut v2, VertexOut v3, Renderer renderer, Material material) {
        edges.clear();

        constructEdges(v1, v2, v3, material);
        addEdges();

        int size = edges.size();
        if (size < 2)
            return;

        if (size == 2) {
            scan(edges.get(0), edges.get(1), material, renderer);
            return;
        }

        if (e1HasGreatestYChange(e1, e2, e3)) {
            // e1 has greatest y change
            scan(e1, e2, e3, material, renderer);
            return;
        }
        if (e2HasGreatestYChange(e2, e3)) {
            // e2 has greatest y change
            scan(e2, e3, e1, material, renderer);
            return;
        }
        // e3 has greatest y change
        scan(e3, e1, e2, material, renderer);
    }

    private static void constructEdges(VertexOut v1, VertexOut v2, VertexOut v3, Material material) {
        edgeFactory.reuseEdge(e1, v1, v2, material);
        edgeFactory.reuseEdge(e2, v2, v3, material);
        edgeFactory.reuseEdge(e3, v3, v1, material);
    }

    private static void addEdges() {
        if (e1.deltaYInt != 0) {
            edges.add(e1);
        }
        if (e2.deltaYInt != 0) {
            edges.add(e2);
        }
        if (e3.deltaYInt != 0) {
            edges.add(e3);
        }
    }

    private static void scan(Edge leftEdge, Edge rightEdge, Material material, Renderer renderer) {

        switch (leftEdge.handiness) {
            case 0:
                scanSegment(leftEdge, rightEdge, leftEdge.yStart, leftEdge.deltaYInt, material, renderer);
                return;
            default:
                scanSegment(rightEdge, leftEdge, rightEdge.yStart, rightEdge.deltaYInt, material, renderer);
        }
    }

    private static void scan(Edge tallestEdge, Edge bottomEdge, Edge topEdge, Material material, Renderer renderer) {
        //swap topEdge and bottomEdge if needed
        if (bottomEdge.yEnd > topEdge.yEnd) {
            Edge temp = bottomEdge;
            bottomEdge = topEdge;
            topEdge = temp;
        }

        switch (tallestEdge.handiness) {
            case 0:
                scanSegment(tallestEdge, bottomEdge, bottomEdge.yStart, bottomEdge.deltaYInt, material, renderer);
                scanSegment(tallestEdge, topEdge, topEdge.yStart, topEdge.deltaYInt, material, renderer);
                return;
            default:
                scanSegment(bottomEdge, tallestEdge, bottomEdge.yStart, bottomEdge.deltaYInt, material, renderer);
                scanSegment(topEdge, tallestEdge, topEdge.yStart, topEdge.deltaYInt, material, renderer);
        }
    }


    private static void scanSegment(Edge left, Edge right, int y, int yChange, Material material, Renderer renderer) {
        int i = 1;

        while (i <= yChange) {
            rasterizeRow(left, right, y, material, renderer);
            left.interpolants.lerp();
            right.interpolants.lerp();
            ++y;
            ++i;
        }

       /* do {
            rasterizeRow(left, right, y, material, renderer);
            left.interpolants.lerp();
            right.interpolants.lerp();
            y++;
            i++;
        } while (i <= yChange);*/
    }

    private static boolean e2HasGreatestYChange(Edge e2, Edge e3) {
        return (e2.deltaY > e3.deltaY);
    }

    private static boolean e1HasGreatestYChange(Edge e1, Edge e2, Edge e3) {
        return (e1.deltaY > e2.deltaY && e1.deltaY > e3.deltaY);
    }
}
