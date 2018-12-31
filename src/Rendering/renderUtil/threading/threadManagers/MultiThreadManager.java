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
        init();
    }

    private void init() {
        for (int i = 0; i < numThreads; i++) {
            workers[i] = new Worker(this);
            threads[i] = new Thread(workers[i]);
            threads[i].start();
        }
    }

    @Override
    public void threadReady() {
        synchronized (this) {
            if (++threadsReady >= numThreads)
                notifyAll();//notifies the dispatch thread (if waiting) that all threads are ready
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

    public Thread getThread(int i){
        return threads[i];
    }

    private int nextWorkerIndex() {
        return (currentWorkerIndex >= numThreads) ? 0 : currentWorkerIndex;
    }
}
