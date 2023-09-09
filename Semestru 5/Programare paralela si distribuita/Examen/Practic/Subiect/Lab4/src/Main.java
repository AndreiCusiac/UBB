import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    final static String path = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Programare paralela si distribuita\\Examen\\Practic\\Subiect\\";
    static ArrayList<Path> allPaths = new ArrayList<>();

    static Integer noThreads = 5;


    final static Integer noPolynomials = 10;
    final static Integer prefix = 1;

    static Path path1 = Paths.get(path + "1p1.txt");
    static Path path2 = Paths.get(path + "1p2.txt");
    static Path path3 = Paths.get(path + "1p3.txt");
    static Path path4 = Paths.get(path + "1p4.txt");
    static Path path5 = Paths.get(path + "1p5.txt");
    static Path path6 = Paths.get(path + "1p6.txt");
    static Path path7 = Paths.get(path + "1p7.txt");
    static Path path8 = Paths.get(path + "1p8.txt");
    static Path path9 = Paths.get(path + "1p9.txt");
    static Path path10 = Paths.get(path + "1p10.txt");

    static Path pathRes = Paths.get(path + "1pR.txt");
    static Path pathRes1 = Paths.get(path + "1pR1.txt");
    static Path pathRes2 = Paths.get(path + "1pR2.txt");
    static Path pathRes3 = Paths.get(path + "1pR3.txt");
    static Path pathRes4 = Paths.get(path + "1pR4.txt");
    static Path pathRes5 = Paths.get(path + "1pR5.txt");
    static Path pathTest = Paths.get(path + "1pRes.txt");

    public static void buildPathArray() {
        allPaths.add(path1);
        allPaths.add(path2);
        allPaths.add(path3);
        allPaths.add(path4);
        allPaths.add(path5);
        allPaths.add(path6);
        allPaths.add(path7);
        allPaths.add(path8);
        allPaths.add(path9);
        allPaths.add(path10);
    }

/*
    static final Integer noPolynomials = 5;
    final static Integer prefix = 2;

    static Path path1 = Paths.get(path + "2p1.txt");
    static Path path2 = Paths.get(path + "2p2.txt");
    static Path path3 = Paths.get(path + "2p3.txt");
    static Path path4 = Paths.get(path + "2p4.txt");
    static Path path5 = Paths.get(path + "2p5.txt");
    static Path pathRes = Paths.get(path + "2pR.txt");
    static Path pathTest = Paths.get(path + "2pRes.txt");

    private static void buildPathArray() {
        allPaths.add(path1);
        allPaths.add(path2);
        allPaths.add(path3);
        allPaths.add(path4);
        allPaths.add(path5);
    }
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

    public static void readPolynom(MyLinkedList resultList, Path file_path) {
        FileInputStream fis= null;
        try {
            fis = new FileInputStream(file_path.toFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner sc = new Scanner(fis);

        while (sc.hasNext()) {
            String currentId = sc.next();
            String currentProb = sc.next();
            String currentPunctaj = sc.next();
            String currentTimp = sc.next();
            Integer idConc = new Integer(currentId);
            Integer prob = new Integer(currentProb);
            Integer punctaj = new Integer(currentPunctaj);
            Integer timp = new Integer(currentTimp);

            MyNode newNode = new MyNode(idConc, prob, punctaj, timp);
            resultList.add(newNode);
        }
        sc.close();
    }

    public static void printPolynom(String name, MyLinkedList resultList) {
        //System.out.println("\n" + name + ":");
    }
    private static File createFile(Path path) {
        try {
            File myObj = new File(path.toUri());
            if (myObj.createNewFile()) {
                //System.out.println("File created: " + myObj.getName());
            } else {
                //System.out.println("File already exists.");
            }

            return myObj;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return null;
    }

    public static void printToFilePolynom(Path path, MyLinkedList resultList) throws IOException {
        File myObj = createFile(path);

        if (myObj == null) {
            System.out.println("Eroare la crearea fisierului.");
            return;
        }

        FileWriter myWriter = new FileWriter(myObj);

        MyNode currentNode = resultList.firstNode;
        Integer coef, expo;

        while (currentNode != null) {

            coef = currentNode.idConcurent;
            expo = currentNode.idProblema;

            myWriter.write(String.valueOf(coef) + " " + String.valueOf(expo) + '\n');

            currentNode = currentNode.nextNode;
        }

        //System.out.println("File " + path.getFileName() + " fully completed.");
        myWriter.close();
    }

    static MyLinkedList linkedList = new MyLinkedList();
    static MyLinkedList linkedList1 = new MyLinkedList();
    static MyLinkedList linkedList2 = new MyLinkedList();
    static MyLinkedList linkedList3 = new MyLinkedList();
    static MyLinkedList linkedList4 = new MyLinkedList();
    static MyLinkedList linkedList5 = new MyLinkedList();
    static MyQueue queueOfMonomials = new MyQueue();
    static AtomicBoolean allRead = new AtomicBoolean(false);
    static AtomicBoolean emptyQueue = new AtomicBoolean(true);

    public static void main(String[] args) {

        ArrayList<Scanner> readerOfPoly = provideScanners();

        noThreads = Integer.valueOf(args[0]);
        //System.out.println(noThreads);

        long startTime = System.nanoTime();
        //secvential(readerOfPoly);
        paralel(readerOfPoly);
        long endTime = System.nanoTime();


        if (!equalFiles(pathRes, pathTest)) {
            System.out.println("Fisiere inegale!");
        } else {
            //System.out.println("Fisiere egale");
        }

        System.out.println((double) (endTime - startTime)/1E6);
    }

    private static void paralel(ArrayList<Scanner> readerOfPoly) {
        Thread firstThread = new Thread(() -> {
            for (Scanner currentScanner : readerOfPoly) {
                while (currentScanner.hasNext()) {
                    String currentId = currentScanner.next();
                    String currentProb = currentScanner.next();
                    String currentPunctaj = currentScanner.next();
                    String currentTimp = currentScanner.next();
                    Integer id = new Integer(currentId);
                    Integer prob = new Integer(currentProb);
                    Integer punctaj = new Integer(currentPunctaj);
                    Integer timp = new Integer(currentTimp);

                    MyNode newNode = new MyNode(id, prob, punctaj, timp);

                    synchronized (queueOfMonomials) {
                        queueOfMonomials.add(newNode);
                    }
                }
                currentScanner.close();
            }
            allRead.set(true);

            /*while (!emptyQueue.get()) {

            }*/

            /*try {
                //printToFilePolynom(pathRes, linkedList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/
        });
        Integer currentNoThreads = noThreads;
        MyThread[] myThreads = new MyThread[currentNoThreads];

        firstThread.start();

        for (int i = 0; i < currentNoThreads; i++) {
            myThreads[i] = new MyThread();
            myThreads[i].start();
        }

        for (int i = 0; i < currentNoThreads; i++) {
            try {
                myThreads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            firstThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class MyThread extends Thread {
        public void run() {
            while (!(allRead.get() && queueOfMonomials.isEmpty())) {
                MyNode queuedNode;

                synchronized (queueOfMonomials) {
                    queuedNode = queueOfMonomials.remove();
                }

                if (queuedNode != null) {
                    MyNode calculatedNode = new MyNode(queuedNode.getIdConcurent(), queuedNode.getIdProblema(), queuedNode.getPunctaj(), queuedNode.getTimpIncarcare());

                    synchronized (linkedList) {
                        linkedList.add(calculatedNode);
                    }
                }
            }
        }
    }

    /*private static void secvential(ArrayList<Scanner> readerOfPoly) {
        boolean finishedReading = false;
        Scanner currentScanner;

        while (!finishedReading) {
            finishedReading = true;

            for (int i = 0; i < readerOfPoly.size(); i++) {
                currentScanner = readerOfPoly.get(i);
                while (currentScanner.hasNext()) {
                    finishedReading = false;

                    String currentCoef = currentScanner.next();
                    String currentExpo = currentScanner.next();
                    Integer coef = new Integer(currentCoef);
                    Integer expo = new Integer(currentExpo);

                    MyNode newNode = new MyNode(coef, expo);
                    linkedList.add(newNode);

                } //else {
                    currentScanner.close();
                    readerOfPoly.remove(currentScanner);
                //}
            }
        }

        try {
            printToFilePolynom(pathRes, linkedList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    private static ArrayList<Scanner> provideScanners() {

        buildPathArray();
        ArrayList<Scanner> toReturn = new ArrayList<>(noPolynomials);

        for (int i = 0; i < noPolynomials; i++) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(allPaths.get(i).toFile());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Scanner sc = new Scanner(fis);

            toReturn.add(sc);
        }

        return toReturn;
    }
}