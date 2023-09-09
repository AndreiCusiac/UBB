package ro.ubbcluj.map.decorator;

import ro.ubbcluj.map.runner.TaskRunner;

public class DelayTaskRunner extends AbstractTaskRunner{

    public DelayTaskRunner(TaskRunner taskRunner) {
        super(taskRunner);
    }

    /**
     * executa Task-urile cu o intarziere
     */
    @Override
    public void executeOneTask() {

        try {
            Thread.sleep(3000);
            super.executeOneTask();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
