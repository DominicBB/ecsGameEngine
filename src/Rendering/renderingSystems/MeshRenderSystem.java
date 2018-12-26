package Rendering.renderingSystems;

import Rendering.renderUtil.Meshes.IndexedMesh;
import Rendering.renderUtil.threading.tasks.BatchTriangleTask;
import Rendering.renderUtil.threading.threadManagers.MultiThreadManager;
import Rendering.renderUtil.threading.threadSaftey.RenderLocksMulti;
import components.RenderableMesh;
import components.TransformComponent;

public class MeshRenderSystem {
    private RenderSystem renderSystem;
    private final int numThreads;
    //    private SingleThreadManager singleThreadManager = new SingleThreadManager();
    private final MultiThreadManager multiThreadManager = new MultiThreadManager(2);

    private final BatchTriangleTask batchTriangleTask3 = new BatchTriangleTask();
    private final BatchTriangleTask batchTriangleTask2 = new BatchTriangleTask();
    private final BatchTriangleTask batchTriangleTask1 = new BatchTriangleTask();

    public MeshRenderSystem(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
//        singleThreadManager.giveTask(batchTriangleTask1);
//        RenderLocks.regesterThread(singleThreadManager.thread);
        numThreads = multiThreadManager.numThreads + 1;
        multiThreadManager.giveTask(batchTriangleTask2, 0);
        multiThreadManager.giveTask(batchTriangleTask3, 1);

        RenderLocksMulti.regesterThread(multiThreadManager.getThread(0));
        RenderLocksMulti.regesterThread(multiThreadManager.getThread(1));
    }

    public final void render(RenderableMesh renderableMesh, TransformComponent transformComponent) {
        IndexedMesh indexedMesh = renderableMesh.indexedMesh;
        setBatches(indexedMesh);
//        singleThreadManager.go();//run task on worker thread
        multiThreadManager.goAll();
        batchTriangleTask1.doTask();//run task on main thread
        multiThreadManager.waitForThreadsToFinish();
//        RenderLocks.reset();
        RenderLocksMulti.reset();
    }

    private void setBatches(IndexedMesh indexedMesh) {
        batchTriangleTask1.setBatch(indexedMesh.transformedVertices, indexedMesh.triIndices,
                0, indexedMesh.triIndices.size(), 3 * numThreads);
        batchTriangleTask2.setBatch(indexedMesh.transformedVertices, indexedMesh.triIndices,
                3, indexedMesh.triIndices.size(), 3 * numThreads);
        batchTriangleTask3.setBatch(indexedMesh.transformedVertices, indexedMesh.triIndices,
                6, indexedMesh.triIndices.size(), 3 * numThreads);

    }

    public void setRenderSystem(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
    }

}
