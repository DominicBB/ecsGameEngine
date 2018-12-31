package Rendering.renderUtil.threading.workers;

import Rendering.renderUtil.threading.tasks.ITask;
import Rendering.renderUtil.threading.threadManagers.IThreadManager;

public class Worker implements Runnable {


    private final IThreadManager threadManager;
    private boolean isRunning;
    private volatile boolean hasStuffToProcess;

    private ITask task;

    public Worker(IThreadManager threadManager) {
        this.threadManager = threadManager;
    }

    public void setTask(ITask task) {
        this.task = task;
        threadManager.threadReady();
    }

    public ITask getTask() {
        return task;
    }

    public boolean isAlive() {
        return isRunning;
    }

    public boolean isProccesing() {
        return hasStuffToProcess;
    }

    @Override
    public void run() {
        isRunning = true;
        doWait();
//        doSpinWait();
        while (isRunning) {
            task.doTask();
            hasStuffToProcess = false;
            threadManager.threadReady();
            doWait();
//            doSpinWait();
        }
    }

    public synchronized void doNotify() {
        hasStuffToProcess = true;
        notify();

    }

    public synchronized void doNotifyAll() {
        hasStuffToProcess = true;
        notifyAll();
    }

    public synchronized void notifyWorkerSpin() {
        hasStuffToProcess = true;
    }

    private void doSpinWait() {
//        long timeStamp = System.currentTimeMillis();
        while (!hasStuffToProcess) {
            Thread.onSpinWait();
        }
//        System.out.println("Waited for: " + (System.currentTimeMillis() - timeStamp));
    }

    protected synchronized void doWait() {
        while (!hasStuffToProcess && isRunning) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        isRunning = false;
        doNotifyAll();
    }
}
