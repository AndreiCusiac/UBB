package ro.ubbcluj.map.runner;

import ro.ubbcluj.map.Factory.TaskContainerFactory;
import ro.ubbcluj.map.container.Container;
import ro.ubbcluj.map.container.Strategy;
import ro.ubbcluj.map.model.Task;

public class StrategyTaskRunner implements TaskRunner{

    private Container container;

    public StrategyTaskRunner(Strategy strategy) {
        container = TaskContainerFactory.getInstance().createContainer(strategy);
    }

    /**
     * elimina ultimul Task din container si il executa
     */
    @Override
    public void executeOneTask() {
        if (!container.isEmpty()) {
            container.remove().execute();
        }
    }

    /**
     * elimina si executa toate Task-urile din container
     */
    @Override
    public void executeAll() {
        while (!container.isEmpty()) {
            executeOneTask();
        }
    }

    /**
     * adauga un Task in container
     * @param t
     */
    @Override
    public void addTask(Task t) {
        container.add(t);
    }

    /**
     * verifica daca container-ul contine Task-uri
     * @return
     */
    @Override
    public boolean hasTask() {
        return !container.isEmpty();
    }
}
