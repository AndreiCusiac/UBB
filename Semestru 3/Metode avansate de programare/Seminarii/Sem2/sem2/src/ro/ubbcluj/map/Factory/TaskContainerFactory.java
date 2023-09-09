package ro.ubbcluj.map.Factory;

import ro.ubbcluj.map.container.Container;
import ro.ubbcluj.map.container.QueueContainer;
import ro.ubbcluj.map.container.StackContainer;
import ro.ubbcluj.map.container.Strategy;
import ro.ubbcluj.map.model.Task;

public class TaskContainerFactory implements Factory{

    private static TaskContainerFactory instance = null;
    //public static final TaskContainerFactory instance = new TaskContainerFactory();

    /**
     * creeaza si returneaza un container conform strategiei primite
     * @param strategy
     * @return Container
     */
    @Override
    public Container createContainer(Strategy strategy) {
        if (strategy == Strategy.LIFO) return new StackContainer();
        if (strategy == Strategy.FIFO) return new QueueContainer();
        // eventual switch
        return null;
    }

    private TaskContainerFactory()
    {

    }

    /**
     * creeaza si returneaza singura instanta a TaskContainerFactory
     * @return
     */
    public static TaskContainerFactory getInstance()
    {
        if (instance == null)
        {
            instance = new TaskContainerFactory();
        }
        return instance;
    }
}
