package systems.drawSystems;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import util.RenderContext;
import util.geometry.Line3D;
import util.geometry.Quad;
import util.geometry.Triangle;

class QuadFiller extends Filler {

    private static int yMax;
    private static int yMin;
    private static int xMax;
    private static int xMin;

    public static void fillQuad(Quad q, RenderContext renderContext) {
        //this should not get reached. just in case
        convertToTriangles(q, renderContext);
//        if (true) {
//
//        } else {

			/*List<Line3D> lines = new ArrayList<>();
			lines.add(Draw.yIncreasingLine3D(q.vectors[0], q.vectors[1]));
			lines.add(Draw.yIncreasingLine3D(q.vectors[1], q.vectors[2]));
			lines.add(Draw.yIncreasingLine3D(q.vectors[2], q.vectors[3]));
			lines.add(Draw.yIncreasingLine3D(q.vectors[3], q.vectors[0]));

			lines = removeHorizontal(lines);

			if (lines.size() == 2) {

			} else if (lines.size() == 3) {

			} else {

			}

			Line3D l1 = new Line3D(q.vectors[0], q.vectors[1]);
			Line3D l2 = new Line3D(q.vectors[1], q.vectors[2]);
			Line3D l3 = new Line3D(q.vectors[2], q.vectors[3]);
			Line3D l4 = new Line3D(q.vectors[3], q.vectors[0]);

			yMax = (int) Math.max(Math.max(l4.v1.y, l4.v2.y), Math.max(Math.max(l3.v1.y, l3.v2.y),
					Math.max(Math.max(l1.v1.y, l1.v2.y), Math.max(l2.v1.y, l2.v2.y))));

			yMin = (int) Math.min(Math.min(l4.v1.y, l4.v2.y), Math.min(Math.min(l3.v1.y, l3.v2.y),
					Math.min(Math.min(l1.v1.y, l1.v2.y), Math.min(l2.v1.y, l2.v2.y))));

			xMax = (int) Math.max(Math.max(l4.v1.x, l4.v2.x), Math.max(Math.max(l3.v1.x, l3.v2.x),
					Math.max(Math.max(l1.v1.x, l1.v2.x), Math.max(l2.v1.x, l2.v2.x))));

			xMin = (int) Math.max(Math.max(l4.v1.x, l4.v2.x), Math.min(Math.min(l3.v1.x, l3.v2.x),
					Math.min(Math.min(l1.v1.x, l1.v2.x), Math.min(l2.v1.x, l2.v2.x))));
					*/
//        }
    }

    private static void convertToTriangles(Quad q, RenderContext renderContext) {
        Triangle[] triangles = q.convertToTriangles();

        Draw.fillPolygon(triangles[0], renderContext);
        Draw.fillPolygon(triangles[0], renderContext);

    }

    // private static void scan(Line3D line1, Line3D line2, Triangle t) {
    // float segmentDy = Math.max(line1.deltaY, line2.deltaY);
    //
    // // line1
    // float dx = line1.deltaX / segmentDy;
    // float dy = line1.deltaY / segmentDy;
    // float dz = (line1.v2.z - line1.v1.z) / segmentDy;
    //
    // float x1 = line1.v1.x;
    // float y = line1.v1.y;
    // float z1 = line1.v1.z;
    //
    // // line2
    // float dx2 = line2.deltaX / segmentDy;
    // float dz2 = (line2.v2.z - line2.v1.z) / segmentDy;
    //
    // float x2 = line2.v1.x;
    // float z2 = line2.v1.z;
    //
    // scanSegment(x1, dx, z1, dz, y, dy, x2, dx2, z2, dz2, segmentDy, t);
    // }
}
