import java.util.function.BinaryOperator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        int length = 10000;

        BinaryOperator<Double> addOp = (a, b) -> a + b;
        BinaryOperator<Double> calculateOp = (a, b) -> Math.sqrt(Math.pow(a, 3) + Math.pow(b, 3));;

        // !!! //
        BinaryOperator<Double> currentOp = addOp;
        // !!! //

        double a[] = new double[length];
        for (int i = 0; i<length; i++) {
            a[i] = i*i;
        }

        double b[] = new double[length];
        for (int i = 0; i<length; i++) {
            b[i] = -i;
        }

        double c[] = new double[length];
        double c1[] = new double[length];
        double c2[] = new double[length];

        long start = System.nanoTime();
//        add(a, b, c);
//        calculate(a, b, c);
        operation(a, b, c, currentOp);
        long finish = System.nanoTime();

        //print(c);

        long time = finish - start;

        System.out.println("\nTime: " + time);

        int no_threads = 4;
        long startP = System.nanoTime();
        addPar(a, b, c1, no_threads, currentOp);
        long finishP = System.nanoTime();

        //print(c1);

        long timeP = finishP - startP;

        System.out.println("\nTimeP: " + timeP);

        if (equal(c, c1)) {
            System.out.println("Egale");
        } else {
            System.out.println("Inegale");
        }

        long startC = System.nanoTime();
        addParCiclic(a, b, c2, no_threads, currentOp);
        long finishC = System.nanoTime();

        //print(c1);

        long timeC = finishC - startC;

        System.out.println("\nTimeC: " + timeC);

        if (equal(c, c2)) {
            System.out.println("Egale");
        } else {
            System.out.println("Inegale");
        }
    }

    public static boolean equal(double[] a, double[] b) {
        for (int i=0; i<a.length; i++) {
//            if (a[i] != b[i]) {
//                return false;
//            }

            if (Math.abs(a[i] - b[i]) > 0.01) {
                return false;
            }
        }

        return true;
    }

    public static double[] addition(double[] a, double[] b) {
        double[] c = new double[a.length];

        for (int i=0; i<a.length; i++) {
            c[i] = a[i] + b[i];
        }

        return c;
    }

    public static void add(double[] a, double[] b, double[] c) {
        for (int i=0; i<a.length; i++) {
            c[i] = a[i] + b[i];
        }
    }

    public static void calculate(double[] a, double[] b, double[] c) {
        for (int i=0; i<a.length; i++) {
            c[i] = Math.sqrt(Math.pow(a[i], 3) + Math.pow(b[i], 3));
        }
    }

    public static void operation(double[] a, double[] b, double[] c, BinaryOperator<Double> op) {
        for (int i=0; i<a.length; i++) {
//            c[i] = Math.sqrt(Math.pow(a[i], 3) + Math.pow(b[i], 3));
            c[i] = op.apply(a[i], b[i]);
        }
    }

    private static class MyThreadLiniar extends Thread {
        private double a[];
        private double b[];
        private double c[];

        private int st;
        private int finish;

        private BinaryOperator<Double> op;

        public MyThreadLiniar(double[] a, double[] b, double[] c, int start, int finish, BinaryOperator<Double> op) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.st = start;
            this.finish = finish;
            this.op = op;
        }

        public void run() {
            for (int i=st; i<finish; i++) {
//                c[i] = a[i] + b[i];
//                c[i] = Math.sqrt(Math.pow(a[i], 3) + Math.pow(b[i], 3));
                c[i] = op.apply(a[i], b[i]);
            }
        }
    }
    private static class MyThreadCiclic extends Thread {
        private double a[];
        private double b[];
        private double c[];

        private int st;
        private int finish;

        private int pas, thr;

        private BinaryOperator<Double> op;

        public MyThreadCiclic(double[] a, double[] b, double[] c, BinaryOperator<Double> op, int pas, int thr) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.finish = a.length;
            this.pas = pas;
            this.thr = thr;
            this.op = op;
        }

        public void run() {
            for (int i=thr; i<finish; i+= pas) {
//                c[i] = a[i] + b[i];
//                c[i] = Math.sqrt(Math.pow(a[i], 3) + Math.pow(b[i], 3));
                c[i] = op.apply(a[i], b[i]);
            }
        }
    }

    public static void addPar(double[] a, double[] b, double[] c, int no_threads, BinaryOperator<Double> op) {
        MyThreadLiniar[] p = new MyThreadLiniar[no_threads];

        int start = 0;
        int chunk_size = a.length / no_threads;
        int end = chunk_size;
        int r = a.length % no_threads;

        for (int i = 0; i<no_threads; i++) {

            if (r > 0) {
                end++;
                r--;
            }

            p[i] = new MyThreadLiniar(a, b, c, start, end, op);
            p[i].start();

            start = end;
            end = chunk_size + start;
        }

        for (int i = 0; i<no_threads; i++) {
            try {
                p[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void addParCiclic(double[] a, double[] b, double[] c, int no_threads, BinaryOperator<Double> op) {
        MyThreadCiclic[] p = new MyThreadCiclic[no_threads];

        for (int i = 0; i<no_threads; i++) {
            p[i] = new MyThreadCiclic(a, b, c, op, no_threads, i);
            p[i].start();
        }

        for (int i = 0; i<no_threads; i++) {
            try {
                p[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void print(double[] a) {
        for (int i = 0; i <a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }


}