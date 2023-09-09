package ro.ubbcluj.map.container;

import ro.ubbcluj.map.model.Task;

public class StackContainer extends SuperContainer{

    /**
     * elimina un Task dintr-un stack
     * @return Task-ul din varful stivei
     */
    @Override
    public Task remove() {
        if (size == 0)
        {
            return null;
        }

        size--;
        return tasks[size];
    }
}
