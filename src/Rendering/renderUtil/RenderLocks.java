package Rendering.renderUtil;

import Rendering.renderUtil.Edges.Edge;
import util.Mathf.Mathf2D.Bounds2D.AABoundingRect;
import util.Mathf.Mathf2D.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class RenderLocks {
    public static volatile AABoundingRect thread1BR = new AABoundingRect(Vector2D.newZeros(), Vector2D.newZeros());
    public static volatile AABoundingRect thread2BR = new AABoundingRect(Vector2D.newZeros(), Vector2D.newZeros());

    public static List<Edge> thread1TODO = new ArrayList<>();
    public static List<Edge> thread1TODODoubleEdge = new ArrayList<>();


    public static List<Edge> thread2TODO = new ArrayList<>();
    public static List<Edge> thread2TODODoubleEdge = new ArrayList<>();

    private static volatile long thread1ID;
    private static volatile long thread2ID;

    public static void regesterThread(Thread thread) {
        if (thread1ID == 0L)
            thread1ID = thread.getId();
        else if (thread2ID == 0L)
            thread2ID = thread.getId();
    }

    public static void setAABR(float maxY, float minY, float maxX, float minX) {
        AABoundingRect aabr = (thread1ID == Thread.currentThread().getId()) ? thread1BR : thread2BR;
        aabr.set(maxY, minY, maxX, minX);
    }

    public static boolean BRintersect() {
        Vector2D max1 = thread1BR.getBottomRight();
        Vector2D min1 = thread1BR.getTopLeft();

        Vector2D max2 = thread2BR.getBottomRight();
        Vector2D min2 = thread2BR.getTopLeft();

        return ((min2.y < max1.y) && (min2.x < max1.x)
                && (max2.x > min1.x) && (max2.y > min1.y));
    }

    public static void addToTODO(Edge maxYChange, Edge bottom, Edge top) {
        List<Edge> threadTODO = (thread1ID == Thread.currentThread().getId()) ? thread1TODO : thread2TODO;
        threadTODO.add(maxYChange);
        threadTODO.add(bottom);
        threadTODO.add(top);
    }

    public static List<Edge> getTODO() {
        return (thread1ID == Thread.currentThread().getId()) ? thread1TODO : thread2TODO;
    }

    public static List<Edge> getTODODoubleEdge() {
        return (thread1ID == Thread.currentThread().getId()) ? thread1TODODoubleEdge : thread2TODODoubleEdge;
    }

    public static void reset(){
        thread1BR.set(0f,0f,0f,0f);
        thread2BR.set(0f,0f,0f,0f);

        thread1TODO.clear();
        thread1TODODoubleEdge.clear();
        thread2TODO.clear();
        thread2TODODoubleEdge.clear();
    }
}
