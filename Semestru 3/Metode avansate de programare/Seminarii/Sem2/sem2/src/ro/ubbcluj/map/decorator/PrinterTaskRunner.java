package ro.ubbcluj.map.decorator;

import ro.ubbcluj.map.runner.TaskRunner;
import ro.ubbcluj.map.utils.Constants;

import java.time.LocalDateTime;
import java.util.Locale;

public class PrinterTaskRunner extends AbstractTaskRunner{

    public PrinterTaskRunner(TaskRunner taskRunner) {
        super(taskRunner);
    }

    /**
     * executa Task-urile afisand un mesaj special
     */
    @Override
    public void executeOneTask() {
        super.executeOneTask();
        System.out.println("Success registered at: " + LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER));
    }
}
