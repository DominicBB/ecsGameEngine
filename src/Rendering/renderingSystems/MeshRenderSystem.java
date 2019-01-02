package Rendering.renderingSystems;

import Rendering.Materials.Material;
import Rendering.renderUtil.Meshes.IndexedMesh;
import Rendering.renderUtil.threading.tasks.StealingBatchTriTask;
import Rendering.renderUtil.threading.threadManagers.MultiThreadManager;
import Rendering.renderUtil.threading.threadSaftey.RenderLocks;
import components.RenderableMesh;
import components.TransformComponent;

public class MeshRenderSystem {
    private RenderSystem renderSystem;
    private final int numThreads;
    private final MultiThreadManager multiThreadManager = new MultiThreadManager(2);
//    private final BatchTriangleTask[] batchTriangleTasks;
    private final StealingBatchTriTask stealingBatchTriTask;

    public MeshRenderSystem(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
        numThreads = multiThreadManager.numThreads + 1;// +1 for main thread
//        batchTriangleTasks = new BatchTriangleTask[/*numThreads*/12];
        stealingBatchTriTask = new StealingBatchTriTask(12);

//        initBatches();
        registerThreads();
    }

   /* private void initBatches() {
        for (int i = 0, len = batchTriangleTasks.length; i < len; i++) {
            batchTriangleTasks[i] = new BatchTriangleTask();
        }
    }*/

    private void registerThreads() {
        for (int i = 0, len = multiThreadManager.numThreads; i < len; i++) {
            RenderLocks.regesterThread(multiThreadManager.getThread(i));
            multiThreadManager.giveTask(stealingBatchTriTask, i);
        }
    }

    public final void render(RenderableMesh renderableMesh, TransformComponent transformComponent) {
        IndexedMesh indexedMesh = renderableMesh.indexedMesh;
        setBatches(indexedMesh, renderableMesh.material);
        multiThreadManager.goAll();
        stealingBatchTriTask.doTask();//run task on main thread
        multiThreadManager.waitForThreadsToFinish();
        RenderLocks.resetAll();
    }

    private void setBatches(IndexedMesh indexedMesh, Material material) {
        stealingBatchTriTask.setShaderType(material.getShader().getShaderType());
        stealingBatchTriTask.setBatches(indexedMesh);
    }

    public void setRenderSystem(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
    }

}
