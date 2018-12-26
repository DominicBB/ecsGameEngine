package Rendering.renderUtil.threading.threadManagers;

import Rendering.renderUtil.threading.tasks.ITask;
import Rendering.renderUtil.threading.workers.Worker;

public class MultiThreadManager implements IThreadManager {

    public final int numThreads;

    private final Worker[] workers;
    private final Thread[] threads;
    private int currentWorkerIndex;
    private volatile int threadsReady;

    public MultiThreadManager(int numThreads) {
        this.workers = new Worker[numThreads];
        this.threads = new Thread[numThreads];
        this.numThreads = numThreads;
    }

    private void init() {
        for (int i = 0; i < numThreads; i++) {
            workers[i] = new Worker(this::threadReady);
            threads[i] = new Thread(workers[i]);
            threads[i].start();
        }
    }

    @Override
    public void threadReady() {
        synchronized (this) {
            if (++threadsReady >= numThreads)
                notifyAll();
        }
    }

    public void giveTask(ITask task) {
        giveTask(task, nextWorkerIndex());
        ++currentWorkerIndex;
    }

    public void giveTask(ITask task, int workerNum) {
        workers[workerNum].setTask(task);
    }

    public void goAll() {
        threadsReady = 0;
        notifyAllWorkers();
    }

    private void notifyAllWorkers() {
        for (int i = 0; i < numThreads; i++) {
            workers[i].doNotifyAll();
        }
    }

    public synchronized void waitForThreadsToFinish() {
        while (threadsReady < numThreads) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int nextWorkerIndex() {
        return (currentWorkerIndex >= numThreads) ? 0 : currentWorkerIndex;
    }
}
