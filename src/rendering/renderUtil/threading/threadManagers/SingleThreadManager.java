package rendering.renderUtil.threading.threadManagers;

import rendering.renderUtil.threading.tasks.ITask;
import rendering.renderUtil.threading.workers.Worker;

public class SingleThreadManager implements IThreadManager {

    private final Worker worker;
    public final Thread thread;

    private volatile boolean threadReady;

    public SingleThreadManager() {
        worker = new Worker(this::threadReady);
        thread = new Thread(worker);
        thread.start();
    }

    public void giveTask(ITask task) {
        worker.setTask(task);
    }

    public void giveTaskandGo(ITask task) {
        giveTask(task);
        go();
    }

    public void go() {
        threadReady = false;
//        worker.notifyWorkerSpin();
        worker.doNotifyAll();
    }

    public void lockStepGoSpin() {
        waitForThreadToFinishSpin();
        go();
    }

    public void waitForThreadToFinishSpin() {
        while (!threadReady) {
            Thread.onSpinWait();
        }
    }

    public synchronized void waitForThreadToFinish() {
        while (!threadReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void threadReady() {
        synchronized (this) {
            threadReady = true;
            notifyAll();
        }

//        threadReady = true;
    }


}
