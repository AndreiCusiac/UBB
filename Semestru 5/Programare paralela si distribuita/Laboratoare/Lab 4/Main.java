import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {
    //static int no_threads = 4;

    /*
    static String m_file = "0";
    static int n = 3;
    static int m = 3;
    static int N = 6;
    static int M = 6;
    */
/*
    static String m_file = "1";
    static int n = 3;
    static int m = 3;
    static int N = 10;
    static int M = 10;
*/

    static String m_file = "2";
    static int n = 5;
    static int m = 5;
    static int N = 1000;
    static int M = 1000;

/*
    static String m_file = "3";
    static int n = 5;
    static int m = 5;
    static int N = 10;
    static int M = 10000;
*/
/*
    static String m_file = "4";
    static int n = 5;
    static int m = 5;
    static int N = 10000;
    static int M = 10;
*/

    private static boolean equalFiles(Path firstFile, Path secondFile) {
        try {
            long size = Files.size(firstFile);
            if (size != Files.size(secondFile)) {
                return false;
            }

            if (size < 2048)
            {
                return Arrays.equals(Files.readAllBytes(firstFile),
                        Files.readAllBytes(secondFile));
            }

            // Compare character-by-character
            try (BufferedReader bf1 = Files.newBufferedReader(firstFile);
                 BufferedReader bf2 = Files.newBufferedReader(secondFile))
            {
                int ch;
                while ((ch = bf1.read()) != -1)
                {
                    if (ch != bf2.read()) {
                        return false;
                    }
                }
            }

            return true;
        } catch (IOException e) {
            System.out.println("A apărut o eroare la compararea fișierelor.");
            e.printStackTrace();
        }
        return false;
    }

    public static void readMatrix(int[][] matrix, Path file_path, int rows, int columns) {
        FileInputStream fis= null;
        try {
            fis = new FileInputStream(file_path.toFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner sc=new Scanner(fis);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                String current_number = sc.next();
                matrix[i][j] = Integer.parseInt(current_number);
            }
        }
        sc.close();
    }

    public static void printMatrix(String name, int[][] matrix, int rows, int columns) {
        System.out.println("\n" + name + ":");
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    private static File createFile(Path path) throws Exception {
        try {
            File myObj = new File(path.toUri());
            if (myObj.createNewFile()) {
                //System.out.println("File created: " + myObj.getName());
            } else {
                //System.out.println("File already exists.");
            }

            return myObj;
        } catch (IOException e) {
            throw new Exception("An error occurred.");
        }
    }

    public static void printToFileMatrix(Path path, int[][] matrix, int rows, int columns) throws Exception {
        File myObj = createFile(path);

        if (myObj == null) {
            //System.out.println("Eroare la crearea fisierului.");
            throw new Exception("Fisiere inegale!");
        }

        FileWriter myWriter = new FileWriter(myObj);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                myWriter.write(String.valueOf(matrix[i][j]) + " ");;
            }
            myWriter.write("\n");
        }

        //System.out.println("File " + path.getFileName() + " fully completed.");
        myWriter.close();
    }

    private static void calculateMatrix(int[][] f, int[][] w, int[][] v, int matrix_rows, int matrix_columns, int window_rows, int window_columns) {
        int row_shift = window_rows/2, column_shift = window_columns/2;
        int current_overlap_row, current_overlap_column;
        int sum, prod;

        for (int i = 0; i < matrix_rows; i++){
            for (int j = 0; j < matrix_columns; j++) {
                sum = 0;

                for (int k = 0; k < window_rows; k++){
                    current_overlap_row = i - row_shift + k;
                    for (int l = 0; l < window_columns; l++) {
                        current_overlap_column = j - column_shift + l;

                        if (current_overlap_row < 0 && current_overlap_column < 0 ||
                                current_overlap_row >= matrix_rows && current_overlap_column >= matrix_columns ||
                                current_overlap_row < 0 && current_overlap_column >= matrix_columns ||
                                current_overlap_row >= matrix_rows && current_overlap_column < 0) {
                            prod = f[i][j] * w[k][l];
                        } else if (current_overlap_row < 0 || current_overlap_row >= matrix_rows) {
                            prod = f[i][current_overlap_column] * w[k][l];
                        } else if (current_overlap_column < 0 || current_overlap_column >= matrix_columns) {
                            prod = f[current_overlap_row][j] * w[k][l];
                        } else {
                            prod = f[current_overlap_row][current_overlap_column] * w[k][l];
                        }

                        sum += prod;
                    }
                }

                v[i][j] = sum;
            }
        }
    }

    private static abstract class MyThread extends Thread {

    }
    private static class MyThreadPeLat extends MyThread {
        private int[][] f;
        private int[][] w;
        private int matrix_rows;
        private int matrix_columns;
        private int window_rows;
        private int window_columns;
        private int row_shift, column_shift;
        private int iStart;
        private int jStart;
        private final int pas;
        private CyclicBarrier cyclicBarrier;
        Vector<Integer> buffer;

        public MyThreadPeLat(int[][] f, int[][] w, int N, int M, int n, int m, int iStart, int jStart, int pas, CyclicBarrier cyclicBarrier) {
            this.f = f;
            this.w = w;
            this.matrix_rows = N;
            this.matrix_columns = M;
            this.window_rows = n;
            this.window_columns = m;
            this.iStart = iStart;
            this.jStart = jStart;
            this.pas = pas;
            this.cyclicBarrier = cyclicBarrier;
            this.buffer = new Vector<>();

            row_shift = window_rows/2;
            column_shift = window_columns/2;
        }

        public void run() {
            int i = iStart;
            int j = jStart;
            int current_overlap_row, current_overlap_column;

            while (i < matrix_rows) {
                int sum = 0;
                int prod;

                for (int k = 0; k < window_rows; k++){
                    current_overlap_row = i - row_shift + k;
                    for (int l = 0; l < window_columns; l++) {
                        current_overlap_column = j - column_shift + l;

                        if (current_overlap_row < 0 && current_overlap_column < 0 ||
                                current_overlap_row >= matrix_rows && current_overlap_column >= matrix_columns ||
                                current_overlap_row < 0 && current_overlap_column >= matrix_columns ||
                                current_overlap_row >= matrix_rows && current_overlap_column < 0) {
                            prod = f[i][j] * w[k][l];
                        } else if (current_overlap_row < 0 || current_overlap_row >= matrix_rows) {
                            prod = f[i][current_overlap_column] * w[k][l];
                        } else if (current_overlap_column < 0 || current_overlap_column >= matrix_columns) {
                            prod = f[current_overlap_row][j] * w[k][l];
                        } else {
                            prod = f[current_overlap_row][current_overlap_column] * w[k][l];
                        }

                        sum += prod;
                    }
                }

                // v[i][j] = sum;
                buffer.add(sum);

                j += pas;
                if (j + pas > matrix_columns) {
                    i += j / matrix_columns;
                    j = j % matrix_columns;
                }
            }

            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }

            i = iStart;
            j = jStart;
            int k = 0;

            while (i < matrix_rows) {
                f[i][j] = buffer.get(k);
                k++;

                j += pas;
                if (j + pas > matrix_columns) {
                    i += j / matrix_columns;
                    j = j % matrix_columns;
                }
            }
        }
    }

    private static class MyThreadPeLung extends MyThread {
        private int[][] f;
        private int[][] w;
        private int matrix_rows;
        private int matrix_columns;
        private int window_rows;
        private int window_columns;
        private int row_shift, column_shift;
        private int iStart;
        private int jStart;
        private final int pas;
        private CyclicBarrier cyclicBarrier;
        Vector<Integer> buffer;

        public MyThreadPeLung(int[][] f, int[][] w, int N, int M, int n, int m, int iStart, int jStart, int pas, CyclicBarrier cyclicBarrier) {
            this.f = f;
            this.w = w;
            this.matrix_rows = N;
            this.matrix_columns = M;
            this.window_rows = n;
            this.window_columns = m;
            this.iStart = iStart;
            this.jStart = jStart;
            this.pas = pas;
            this.cyclicBarrier = cyclicBarrier;
            this.buffer = new Vector<>();

            row_shift = window_rows/2;
            column_shift = window_columns/2;
        }

        public void run() {
            int i = iStart;
            int j = jStart;
            int current_overlap_row, current_overlap_column;

            while (j < matrix_columns) {
                int sum = 0;
                int prod;

                for (int k = 0; k < window_rows; k++){
                    current_overlap_row = i - row_shift + k;
                    for (int l = 0; l < window_columns; l++) {
                        current_overlap_column = j - column_shift + l;

                        if (current_overlap_row < 0 && current_overlap_column < 0 ||
                                current_overlap_row >= matrix_rows && current_overlap_column >= matrix_columns ||
                                current_overlap_row < 0 && current_overlap_column >= matrix_columns ||
                                current_overlap_row >= matrix_rows && current_overlap_column < 0) {
                            prod = f[i][j] * w[k][l];
                        } else if (current_overlap_row < 0 || current_overlap_row >= matrix_rows) {
                            prod = f[i][current_overlap_column] * w[k][l];
                        } else if (current_overlap_column < 0 || current_overlap_column >= matrix_columns) {
                            prod = f[current_overlap_row][j] * w[k][l];
                        } else {
                            prod = f[current_overlap_row][current_overlap_column] * w[k][l];
                        }

                        sum += prod;
                    }
                }

                // v[i][j] = sum;
                buffer.add(sum);

                i += pas;
                if (i + pas > matrix_rows) {
                    j += i / matrix_rows;
                    i = i % matrix_rows;
                }
            }

            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }

            i = iStart;
            j = jStart;
            int k = 0;

            while (j < matrix_columns) {
                f[i][j] = buffer.get(k);
                k++;

                i += pas;
                if (i + pas > matrix_rows) {
                    j += i / matrix_rows;
                    i = i % matrix_rows;
                }
            }
        }
    }

    public static void calculateThreads(int[][] f, int[][] w, int N, int M, int n, int m, int no_threads) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(no_threads);
        int iStart = 0, jStart = 0;

        MyThreadPeLat[] p = new MyThreadPeLat[no_threads];
        for (int i = 0; i<no_threads; i++) {
            p[i] = new MyThreadPeLat(f, w, N, M, n, m, iStart, jStart, no_threads, cyclicBarrier);
            p[i].start();

            jStart += 1;
            if (jStart >= M) {
                iStart += 1;
                jStart = 0;
            }
        }

        for (int i = 0; i<no_threads; i++) {
            try {
                p[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        /*
        if (M > N) {
            MyThreadPeLat[] p = new MyThreadPeLat[no_threads];
            for (int i = 0; i<no_threads; i++) {
                p[i] = new MyThreadPeLat(f, w, N, M, n, m, iStart, jStart, no_threads, cyclicBarrier);
                p[i].start();

                jStart += 1;
                if (jStart >= M) {
                    iStart += 1;
                    jStart = 0;
                }
            }

            for (int i = 0; i<no_threads; i++) {
                try {
                    p[i].join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            MyThreadPeLung[] p = new MyThreadPeLung[no_threads];
            for (int i = 0; i<no_threads; i++) {
                p[i] = new MyThreadPeLung(f, w, N, M, n, m, iStart, jStart, no_threads, cyclicBarrier);
                p[i].start();

                iStart += 1;
                if (iStart >= N) {
                    jStart += 1;
                    iStart = 0;
                }
            }

            for (int i = 0; i<no_threads; i++) {
                try {
                    p[i].join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }*/
    }

    public static void main(String[] args) throws Exception {
        //System.out.println("Hello world!");

        final String path = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Programare paralela si distribuita\\Laboratoare\\Lab 3\\";
        final String pathOut = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Programare paralela si distribuita\\Laboratoare\\Lab 4\\";

        String matrix_file = "r" + m_file + ".txt";
        String w_file = "1";
        String window_file = "w"    + m_file + ".txt";
        String v_file = "1";
        String final_file = "v" + m_file + ".txt";
        String check_file = "v" + m_file + "S.txt";

        Path matrixPath = Paths.get(path + matrix_file);
        Path windowPath = Paths.get(path + window_file);
        Path finalPath = Paths.get(pathOut + final_file);
        Path checkPath = Paths.get(path + check_file);

        //int length = 10000;
        int no_threads = Integer.parseInt(args[0]);

        int F[][] = new int[N][M];
        int W[][] = new int[n][m];

        readMatrix(F, matrixPath, N, M);
        //printMatrix("Matrix", F, N, M);

        readMatrix(W, windowPath, n, m);
        //printMatrix("Window", W, n, m);

        long startTime = System.nanoTime();
//        calculateMatrix(F, W, V, N, M, n, m);
        calculateThreads(F, W, N, M, n, m, no_threads);
        long endTime = System.nanoTime();

        //printMatrix("Final", V, N, M);
        printToFileMatrix(finalPath, F, N, M);

        if (!equalFiles(finalPath, checkPath)) {
            throw new Exception("Fisiere inegale!");
        } else {
            //System.out.println("Fisiere egale!");
        }

        System.out.println((double) (endTime - startTime)/1E6);
    }
}