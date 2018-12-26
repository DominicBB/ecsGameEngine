package Rendering.renderingSystems;

import Rendering.renderUtil.Meshes.IndexedMesh;
import Rendering.renderUtil.RenderLocks;
import Rendering.renderUtil.threading.tasks.BatchTriangleTask;
import Rendering.renderUtil.threading.threadManagers.SingleThreadManager;
import components.*;
import core.coreSystems.EntityGrabberSystem;

import java.util.Arrays;

public class MeshRenderSystem {
    private RenderSystem renderSystem;
    private int numThreads;
    private SingleThreadManager singleThreadManager = new SingleThreadManager();
    private BatchTriangleTask batchTriangleTask2 = new BatchTriangleTask();
    private BatchTriangleTask batchTriangleTask1 = new BatchTriangleTask();

    public MeshRenderSystem(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
        singleThreadManager.giveTask(batchTriangleTask1);
        numThreads = 2;
        RenderLocks.regesterThread(singleThreadManager.thread);
    }

    public final void render(RenderableMesh renderableMesh, TransformComponent transformComponent) {
        IndexedMesh indexedMesh = renderableMesh.indexedMesh;
        setBatches(indexedMesh);
        singleThreadManager.go();//run task on worker thread
        batchTriangleTask2.doTask();//run task on main thread
        singleThreadManager.waitForThreadToFinish();
        RenderLocks.reset();
    }

    private void setBatches(IndexedMesh indexedMesh) {
        batchTriangleTask1.setBatch(indexedMesh.transformedVertices, indexedMesh.triIndices,
                0, indexedMesh.triIndices.size(), 3 * numThreads);
        batchTriangleTask2.setBatch(indexedMesh.transformedVertices, indexedMesh.triIndices,
                3, indexedMesh.triIndices.size(), 3 * numThreads);
    }

    public void setRenderSystem(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
    }

}
