package ro.ubbcluj.map;

import com.sun.management.GcInfo;
import org.w3c.dom.ls.LSInput;
import ro.ubbcluj.map.Factory.Factory;
import ro.ubbcluj.map.Factory.TaskContainerFactory;
import ro.ubbcluj.map.container.Container;
import ro.ubbcluj.map.container.StackContainer;
import ro.ubbcluj.map.container.Strategy;
import ro.ubbcluj.map.decorator.DelayTaskRunner;
import ro.ubbcluj.map.decorator.PrinterTaskRunner;
import ro.ubbcluj.map.model.MessageTask;
import ro.ubbcluj.map.model.SortingTask;
import ro.ubbcluj.map.runner.StrategyTaskRunner;
import ro.ubbcluj.map.sorter.BubbleSort;
import ro.ubbcluj.map.sorter.MergeSort;
import ro.ubbcluj.map.sorter.SortingStrategy;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Main {

    public static void testSort() {
        Integer[] test = new Integer[]{-3,2,70,15,-59,1,2};
        Integer[] test1 = new Integer[]{3,0,-103,-24,65,3,1,4};

        System.out.println("Test BubbleSort:");
        BubbleSort bubbleSort = new BubbleSort(test);
        System.out.println("Inainte: " + Arrays.toString(test));
        bubbleSort.sort();
        System.out.println("Dupa: " + Arrays.toString(bubbleSort.getVect()) + "\n");

        System.out.println("Test MergeSort:");
        MergeSort mergeSort = new MergeSort(test1);
        System.out.println("Inainte: " + Arrays.toString(test1));
        mergeSort.sort();
        System.out.println("Dupa: " + Arrays.toString(mergeSort.getVect()) + "\n");
    }

    public static MessageTask[] createMessageTaskArray(){
        MessageTask t1=new MessageTask("1","Feedback lab1",
                "Ai obtinut 9.60","Gigi", "Ana", LocalDateTime.now());
        MessageTask t2=new MessageTask("2","Feedback lab1",
                "Ai obtinut 5.60","Gigi", "Ana", LocalDateTime.now());
        MessageTask t3=new MessageTask("3","Feedback lab3",
                "Ai obtinut 10","Gigi", "Ana", LocalDateTime.now());
        return new MessageTask[]{t1,t2,t3};
    }
    public static void main(String[] args) {

        testSort();
        testSortingTask();
        System.out.println("Testele au rulat cu succes!\n\nStart:");

        Strategy strategy;

        switch (args[0]) {
            case "LIFO" -> strategy = Strategy.LIFO;
            case "FIFO" -> strategy = Strategy.FIFO;
            default -> { System.out.println("Eroare la citirea strategiei."); return; }
        }

        //testMessageTask(strategy);



        //testInitial(args);
    }

    private static void testInitial(String[] args) {
        MessageTask[] l=createMessageTaskArray();
        for (int i = 0; i < l.length; i++) {
            //System.out.println(l[i]);
        }

        //Factory fabricuta = TaskContainerFactory.getInstance();
        //Container stack = fabricuta.createContainer(Strategy.LIFO);

        //stack.add(l[0]);
        //System.out.println(stack.remove());

        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(Strategy.valueOf(args[0]));
        strategyTaskRunner.addTask(l[0]);
        strategyTaskRunner.addTask(l[1]);
        strategyTaskRunner.addTask(l[2]);

        //strategyTaskRunner.executeAll();

        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(strategyTaskRunner);

        printerTaskRunner.executeAll();
    }

    private static void testMessageTask(Strategy strategy) {
        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(strategy);

        strategyTaskRunner.addTask(new MessageTask("111", "StrategyTaskRunner - 1", strategy.toString() + " - 1", "mine", "tine", LocalDateTime.now()));
        strategyTaskRunner.addTask(new MessageTask("112", "StrategyTaskRunner - 2", strategy.toString() + " - 2", "mine", "tine", LocalDateTime.now()));
        strategyTaskRunner.addTask(new MessageTask("113", "StrategyTaskRunner - 3", strategy.toString() + " - 3", "mine", "tine", LocalDateTime.now()));
        strategyTaskRunner.addTask(new MessageTask("114", "StrategyTaskRunner - 4", strategy.toString() + " - 4", "mine", "tine", LocalDateTime.now()));

        strategyTaskRunner.executeAll();
        System.out.println("\n");

        DelayTaskRunner delayTaskRunner = new DelayTaskRunner(strategyTaskRunner);
        delayTaskRunner.addTask(new MessageTask("211", "DelayTaskRunner - 1", strategy.toString() + " - 1", "mine", "tine", LocalDateTime.now()));
        delayTaskRunner.addTask(new MessageTask("212", "DelayTaskRunner - 2", strategy.toString() + " - 2", "mine", "tine", LocalDateTime.now()));
        delayTaskRunner.addTask(new MessageTask("213", "DelayTaskRunner - 3", strategy.toString() + " - 3", "mine", "tine", LocalDateTime.now()));
        delayTaskRunner.addTask(new MessageTask("214", "DelayTaskRunner - 4", strategy.toString() + " - 4", "mine", "tine", LocalDateTime.now()));
        delayTaskRunner.addTask(new MessageTask("215", "DelayTaskRunner - 5", strategy.toString() + " - 5", "mine", "tine", LocalDateTime.now()));

        delayTaskRunner.executeAll();
        System.out.println("\n");

        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(strategyTaskRunner);

        printerTaskRunner.addTask(new MessageTask("311", "PrinterTaskRunner - 1", strategy.toString() + " - 1", "mine", "tine", LocalDateTime.now()));
        printerTaskRunner.addTask(new MessageTask("312", "PrinterTaskRunner - 2", strategy.toString() + " - 2", "mine", "tine", LocalDateTime.now()));
        printerTaskRunner.addTask(new MessageTask("313", "PrinterTaskRunner - 3", strategy.toString() + " - 3", "mine", "tine", LocalDateTime.now()));

        printerTaskRunner.executeAll();
        System.out.println("\n");
    }

    private static void testSortingTask() {
        Integer[] test = new Integer[]{-3,2,70,15,-59,1,2};
        Integer[] test1 = new Integer[]{3,0,-103,-24,65,3,1,4};

        SortingTask sortingTask = new SortingTask("1", "Test Bubble", SortingStrategy.BUBBLE, test);
        sortingTask.execute();

        SortingTask sortingTask1 = new SortingTask("2", "Test Merge", "MERGE", test1);
        sortingTask1.execute();
    }


}
