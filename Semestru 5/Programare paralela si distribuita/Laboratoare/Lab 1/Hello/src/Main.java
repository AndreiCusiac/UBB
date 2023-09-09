import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        //compareFilesTest();

        try {
            generateRandomTest();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void compareFilesTest() {

        FileComparer fileComparer = new FileComparer();

        final String path = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Programare paralela si distribuita\\Laboratoare\\Lab 1\\";

        Path path1 = Path.of(path + "r1.txt");
        Path path2 = Path.of(path + "r2.txt");
        Path path3 = Path.of(path + "r3.txt");

        System.out.println("1 cu 2 " + fileComparer.isEqual(path1, path2));
        System.out.println("1 cu 3 " + fileComparer.isEqual(path1, path3));
        System.out.println("2 cu 3 " + fileComparer.isEqual(path2, path3));
    }

    public static Integer giveRandomIntegerBetween(Integer minVal, Integer maxVal) {

        Random r = new Random();
        double random = minVal + r.nextFloat() * (maxVal - minVal);

        random = BigDecimal.valueOf(random)
                .setScale(0, RoundingMode.HALF_UP)
                .doubleValue();

        return (Integer) (int) random;
    }

    private static void runA(String path, RandomNumberGenerator randomNumberGenerator, Integer minValOfMonomial, Integer maxValOfMonomial, Integer minGrade, Integer maxGrade, Integer minCoef, Integer maxCoef) throws IOException {
        Path path1 = Path.of(path + "1p1.txt");
        Path path2 = Path.of(path + "1p2.txt");
        Path path3 = Path.of(path + "1p3.txt");
        Path path4 = Path.of(path + "1p4.txt");
        Path path5 = Path.of(path + "1p5.txt");
        Path path6 = Path.of(path + "1p6.txt");
        Path path7 = Path.of(path + "1p7.txt");
        Path path8 = Path.of(path + "1p8.txt");
        Path path9 = Path.of(path + "1p9.txt");
        Path path10 = Path.of(path + "1p10.txt");

        /*randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path1, minGrade, maxGrade, minCoef, maxCoef, 0);
        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path2, minGrade, maxGrade, minCoef, maxCoef, 0);
        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path3, minGrade, maxGrade, minCoef, maxCoef, 0);
        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path4, minGrade, maxGrade, minCoef, maxCoef, 0);
        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path5, minGrade, maxGrade, minCoef, maxCoef, 0);
        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path6, minGrade, maxGrade, minCoef, maxCoef, 0);
        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path7, minGrade, maxGrade, minCoef, maxCoef, 0);
        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path8, minGrade, maxGrade, minCoef, maxCoef, 0);
        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path9, minGrade, maxGrade, minCoef, maxCoef, 0);
        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path10, minGrade, maxGrade, minCoef, maxCoef, 0);*/

        ArrayList<Integer> timpi = new ArrayList<Integer>(Arrays.asList(30, 45, 60, 20, 70));
        randomNumberGenerator.generateResults(100, 1, 5, path1, 10, 100, timpi);
        randomNumberGenerator.generateResults(100, 2, 5, path2, 10, 100, timpi);
        randomNumberGenerator.generateResults(100, 3, 5, path3, 10, 100, timpi);
        randomNumberGenerator.generateResults(100, 4, 5, path4, 10, 100, timpi);
        randomNumberGenerator.generateResults(100, 5, 5, path5, 10, 100, timpi);
        randomNumberGenerator.generateResults(100, 6, 5, path6, 10, 100, timpi);
        randomNumberGenerator.generateResults(100, 7, 5, path7, 10, 100, timpi);
        randomNumberGenerator.generateResults(100, 8, 5, path8, 10, 100, timpi);
        randomNumberGenerator.generateResults(100, 9, 5, path9, 10, 100, timpi);
        randomNumberGenerator.generateResults(100, 10, 5, path10, 10, 100, timpi);
    }

    private static void runB(String path, RandomNumberGenerator randomNumberGenerator, Integer minValOfMonomial, Integer maxValOfMonomial, Integer minGrade, Integer maxGrade, Integer minCoef, Integer maxCoef) throws IOException {
        Path path1 = Path.of(path + "2p1.txt");
        Path path2 = Path.of(path + "2p2.txt");
        Path path3 = Path.of(path + "2p3.txt");
        Path path4 = Path.of(path + "2p4.txt");
        Path path5 = Path.of(path + "2p5.txt");

        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path1, minGrade, maxGrade, minCoef, maxCoef, 0);
        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path2, minGrade, maxGrade, minCoef, maxCoef, 0);
        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path3, minGrade, maxGrade, minCoef, maxCoef, 0);
        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path4, minGrade, maxGrade, minCoef, maxCoef, 0);
        randomNumberGenerator.generateNNumbersRange(giveRandomIntegerBetween(minValOfMonomial, maxValOfMonomial), path5, minGrade, maxGrade, minCoef, maxCoef, 0);
    }


    private static void generateRandomTest() throws IOException {

        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        final String path = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Programare paralela si distribuita\\Examen\\Practic\\Subiect\\";



        Integer minValOfMonomial = 1;
        Integer maxValOfMonomial = 100;
        Integer minGrade = 0;
        Integer maxGrade = 10000;
        Integer minCoef = -100;
        Integer maxCoef = 100;

        runA(path, randomNumberGenerator, minValOfMonomial, maxValOfMonomial, minGrade, maxGrade, minCoef, maxCoef);
        //runB(path, randomNumberGenerator, minValOfMonomial, maxValOfMonomial, minGrade, maxGrade, minCoef, maxCoef);
    }
}