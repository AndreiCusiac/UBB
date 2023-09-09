package ro.ubbcluj.map.container;

import ro.ubbcluj.map.model.Task;

public class QueueContainer extends SuperContainer{

    /**
     * elimina unui Task dintr-un queue
     * @return Task-ul din capul listei
     */
    @Override
    public Task remove() {
        if (size == 0)
        {
            return null;
        }

        var t = tasks[0];

        size--;

        for (int i = 0; i < size; i++ ) {
            tasks[i] = tasks[i+1];
        }

        return t;
    }
}
