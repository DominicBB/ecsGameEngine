package Rendering.renderUtil.threading.threadSaftey;

import Rendering.renderUtil.Edges.Edge;
import util.Mathf.Mathf2D.Bounds2D.AABoundingRect;
import util.Mathf.Mathf2D.Vector2D;

import java.util.*;

public class RenderLocksMulti {
    public static Map<Long, RenderLockNode> lockNodeMap = new HashMap<>();
    private static List<RenderLockNode> values = new ArrayList<>();
    public static volatile int numThreads;


    public static void regesterThread(Thread thread) {
        RenderLockNode renderLockNode = new RenderLockNode(thread.getId());
        lockNodeMap.put(thread.getId(), renderLockNode);
        values.add(renderLockNode);
        ++numThreads;
    }

    public static void setAABR(float maxY, float minY, float maxX, float minX) {
        AABoundingRect aabr = getRenderLockNode(Thread.currentThread().getId()).BR;
        aabr.set(maxY, minY, maxX, minX);
    }

    public static boolean BRintersect() {
        AABoundingRect aabr = getRenderLockNode(Thread.currentThread().getId()).BR;
        AABoundingRect aabr2;

        Vector2D max1 = aabr.getBottomRight();
        Vector2D min1 = aabr.getTopLeft();
        Vector2D max2, min2;

        for (int i = 0, len = values.size(); i < len; i++) {
            aabr2 = values.get(i).BR;
            max2 = aabr2.getBottomRight();
            min2 = aabr2.getTopLeft();

            if (brIntersects(min1, max1, min2, max2))
                return true;
        }
        return false;
    }

    public static void addToTODO(Edge maxYChange, Edge bottom, Edge top) {
        List<Edge> threadTODO = getRenderLockNode(Thread.currentThread().getId()).TODO;
        threadTODO.add(maxYChange);
        threadTODO.add(bottom);
        threadTODO.add(top);
    }

    public static List<Edge> getTODO() {
        return getRenderLockNode(Thread.currentThread().getId()).TODO;
    }

    public static List<Edge> getTODODoubleEdge() {
        return getRenderLockNode(Thread.currentThread().getId()).TODODoubleEdge;
    }

    public static RenderLockNode getRenderLockNode(long threadID) {
        return lockNodeMap.get(threadID);
    }

    private static boolean brIntersects(Vector2D min1, Vector2D max1, Vector2D min2, Vector2D max2) {
        return (min2.y < max1.y) && (min2.x < max1.x)
                && (max2.x > min1.x) && (max2.y > min1.y);
    }

    public static void reset() {
        for (int i = 0, len = values.size(); i < len; i++) {
            values.get(i).reset();
        }
    }
}
