package ro.ubbcluj.map.decorator;

import ro.ubbcluj.map.model.Task;
import ro.ubbcluj.map.runner.TaskRunner;

public abstract class AbstractTaskRunner implements TaskRunner {

    private TaskRunner taskRunner;

    /**
     * constructor
     * @param taskRunner
     */
    public AbstractTaskRunner(TaskRunner taskRunner) {
        this.taskRunner = taskRunner;
    }


    @Override
    public void executeOneTask() {
        taskRunner.executeOneTask();
    }

    @Override
    public void executeAll() {
        while(taskRunner.hasTask()) {
            executeOneTask();
        }

        // taskRunner.executeAll();
    }

    @Override
    public void addTask(Task t) {
        taskRunner.addTask(t);
    }

    @Override
    public boolean hasTask() {
        return taskRunner.hasTask();
    }
}
