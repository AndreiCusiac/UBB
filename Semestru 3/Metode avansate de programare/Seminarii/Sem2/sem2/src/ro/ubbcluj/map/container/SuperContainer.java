package ro.ubbcluj.map.container;

import ro.ubbcluj.map.model.Task;

public abstract class SuperContainer implements Container{

    Task[] tasks;
    int size;

    /**
     * constructor
     */
    public SuperContainer() {
        this.tasks = new Task[10];
        this.size = 0;
    }

    /**
     * elimina un Task dintr-un container
     * @return Task-ul dorit
     */
    @Override
    public abstract Task remove();

    /**
     * adauga un Task intr-un container
     * @param task
     */
    @Override
    public void add(Task task) {
        if (size >= tasks.length) {
            resize();
        }

        tasks[size] = task;
        size++;
    }

    /**
     * mareste capacitatea containerului
     */
    private void resize() {
        Task[] newTasks = new Task[tasks.length*2];

        System.arraycopy(tasks, 0, newTasks, 0, tasks.length);

        tasks = newTasks;
    }

    /**
     * returneaza dimensiunea containerului
     * @return size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * verifica daca containerul este vid
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
