package ro.ubbcluj.map.model;

import ro.ubbcluj.map.container.Strategy;
import ro.ubbcluj.map.sorter.AbstractSorter;
import ro.ubbcluj.map.sorter.BubbleSort;
import ro.ubbcluj.map.sorter.MergeSort;
import ro.ubbcluj.map.sorter.SortingStrategy;

import java.util.Arrays;

public class SortingTask extends Task {

    SortingStrategy sortingStrategy;
    AbstractSorter abstractSorter;
    Integer[] vector;

    public SortingTask(String taskId, String description, String sortStrategy, Integer[] vector) {
        super(taskId, description);

        switch (sortStrategy) {
            case "BUBBLE" -> this.sortingStrategy = SortingStrategy.BUBBLE;
            case "MERGE" -> this.sortingStrategy = SortingStrategy.MERGE;
        }

        this.sortingStrategy = sortingStrategy;
        this.vector = vector;

        switch (this.sortingStrategy) {
            case BUBBLE -> abstractSorter = new BubbleSort(this.vector);
            case MERGE -> abstractSorter = new MergeSort(this.vector);
        }
    }

    public SortingTask(String taskId, String description, SortingStrategy sortingStrategy, Integer[] vector) {
        super(taskId, description);

        this.sortingStrategy = sortingStrategy;
        this.vector = vector;

        switch (sortingStrategy) {
            case BUBBLE -> abstractSorter = new BubbleSort(this.vector);
            case MERGE -> abstractSorter = new MergeSort(this.vector);
        }
    }


    @Override
    public void execute() {
        this.vector = abstractSorter.sort();

        System.out.println("Vectorul sortat prin " + sortingStrategy.toString() + " este: " + Arrays.toString(this.vector));
    }
}
