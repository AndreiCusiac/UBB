package org.example.server;

public class Verifier implements Runnable {
    private Repo repo;
    private Integer timeOut;

    public Verifier(Repo repo, Integer timeOut) {
        this.repo = repo;
        this.timeOut = timeOut;
    }
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(timeOut);
                repo.verify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}