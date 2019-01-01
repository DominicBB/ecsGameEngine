package Rendering.renderUtil.threading.tasks;

import Rendering.renderUtil.Meshes.IndexedMesh;
import Rendering.renderUtil.threading.threadSaftey.RenderLockNode;
import Rendering.renderUtil.threading.threadSaftey.RenderLocks;

public class StealingBatchTriTask implements ITask {

    private final BatchTriangleTask[] batchTriangleTasks;
    private volatile int currentIndex;

    public StealingBatchTriTask(int numTasks) {
        batchTriangleTasks = new BatchTriangleTask[numTasks];
        initBatches();
    }

    private void initBatches() {
        for (int i = 0, len = batchTriangleTasks.length; i < len; i++) {
            batchTriangleTasks[i] = new BatchTriangleTask();
        }
    }

    public void setBatches(IndexedMesh indexedMesh) {
        int size = indexedMesh.triIndices.length;
        int step = size / batchTriangleTasks.length;
        int stepRem = step % 3;
        step -= stepRem;

        int start = 0;
        int end = step;

        for (int i = 0, len = batchTriangleTasks.length; i < len; i++) {
            if (i == len - 1) end = size;
            batchTriangleTasks[i].setBatch(indexedMesh.transformedVertices, indexedMesh.triIndices,
                    start, end, 3);
            start += step;
            end += step;
        }

        currentIndex = 0;
    }

    /*public void setBatches(IndexedMesh indexedMesh) {
        int skipAmt = 3 * batchTriangleTasks.length;
        int size = indexedMesh.triIndices.length;
        int start = 0;
        for (int i = 0, len = batchTriangleTasks.length; i < len; i++) {
            batchTriangleTasks[i].setBatch(indexedMesh.transformedVertices, indexedMesh.triIndices,
                    start, size, skipAmt);
            start += 3;
        }
        currentIndex = 0;
    }*/

    @Override
    public void doTask() {
        BatchTriangleTask task;
        RenderLockNode renderLockNode = RenderLocks.getRenderLockNode(Thread.currentThread().getId());
        while (currentIndex < batchTriangleTasks.length) {
            synchronized (batchTriangleTasks) {
                if (currentIndex >= batchTriangleTasks.length) return;
                task = batchTriangleTasks[currentIndex++];
            }
            task.setRenderer(renderLockNode.renderer);
            task.doTask();
        }
        renderLockNode.drawTODOs();
        renderLockNode.reset();
    }
}
