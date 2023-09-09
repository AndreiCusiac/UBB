import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;

public class RandomNumberGenerator {
    Path pathToOutput;
    Integer numbersToGenerate;

    public Path getPathToOutput() {
        return pathToOutput;
    }

    public void setPathToOutput(Path pathToOutput) {
        this.pathToOutput = pathToOutput;
    }

    public Integer getNumbersToGenerate() {
        return numbersToGenerate;
    }

    public void setNumbersToGenerate(Integer numbersToGenerate) {
        this.numbersToGenerate = numbersToGenerate;
    }
/*
    public void generateNNumbers(Integer n, Path path) {
        File myObj = new File(path.toUri());

        Random r = new Random();
        float random = r.nextFloat();
    }*/

    private File createFile(Path path) {
        try {
            File myObj = new File(path.toUri());
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            return myObj;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return null;
    }

    public void generateNMMatrixRange(Path path, Integer n, Integer m, Integer min, Integer max, Float value) throws IOException {
        File myObj = createFile(path);

        if (myObj == null) {
            System.out.println("Eroare la crearea fisierului.");
            return;
        }

        FileWriter myWriter = new FileWriter(myObj);

        for (int i=0; i<n; i++) {
            for (int j = 0; j < m; j++) {
                if (value != null) {
                    myWriter.write(String.valueOf(value) + " ");;
                } else {
                    Random r = new Random();
                    int random = (int) (min + r.nextFloat() * (max - min));
                    /*
                    random = BigDecimal.valueOf(random)
                        .setScale(precision, RoundingMode.HALF_UP)
                        .doubleValue();
                    */
                    myWriter.write(String.valueOf(random) + " ");
                }
            }
            myWriter.write("\n");
        }

        System.out.println("File fully completed.");
        myWriter.close();
    }

    public void generateNNumbersRange(Integer n, Path path, double min, double max, double minCoef, double maxCoef, Integer precision) throws IOException {

        File myObj = createFile(path);

        if (myObj == null) {
            System.out.println("Eroare la generarea numerelor.");
            return;
        }

        FileWriter myWriter = new FileWriter(myObj);

        for (int i=0; i<n; i++) {

            Random r = new Random();
            double random = min + r.nextFloat() * (max - min);

            random = BigDecimal.valueOf(random)
                    .setScale(precision, RoundingMode.HALF_UP)
                    .doubleValue();

            Integer expRandom = (int) random;

            r = new Random();
            random = minCoef + r.nextFloat() * (maxCoef - minCoef);

            random = BigDecimal.valueOf(random)
                    .setScale(precision, RoundingMode.HALF_UP)
                    .doubleValue();

            Integer coefRandom = (int) random;
            if (coefRandom == 0) {
                coefRandom = 1;
            }

            myWriter.write(String.valueOf(coefRandom) + " " + String.valueOf(expRandom) + '\n');
        }
        System.out.println("File fully completed.");
        myWriter.close();
    }

    public void generateResults(Integer n, Integer J, Integer nrProb, Path path, Integer minPunctaj, Integer maxPunctaj, ArrayList<Integer> timpiPerProbl) throws IOException {

        File myObj = createFile(path);

        if (myObj == null) {
            System.out.println("Eroare la generarea numerelor.");
            return;
        }

        FileWriter myWriter = new FileWriter(myObj);

        for (int i = 1; i <= n; i++) {
            Integer t_start = 0;
            Integer t_finish;
            for (int pr = 1; pr <= nrProb; pr++) {
                t_finish = t_start;
                t_finish += timpiPerProbl.get(pr-1);

                Random r = new Random();

                Integer punctajPerProb = minPunctaj + r.nextInt((maxPunctaj - minPunctaj) + 1);

                Integer timp = t_start + r.nextInt(t_finish + 5 - t_start + 1);


                Integer idConc = (J-1) * 100 + i;

                myWriter.write(String.valueOf(idConc) + " " + String.valueOf(pr) + " " + String.valueOf(punctajPerProb) + " " + String.valueOf(timp) + '\n');

                t_start = t_finish;
            }

        }

        /*for (int i=0; i<n; i++) {


            double random = min + r.nextFloat() * (max - min);

            random = BigDecimal.valueOf(random)
                    .setScale(precision, RoundingMode.HALF_UP)
                    .doubleValue();

            Integer expRandom = (int) random;

            r = new Random();
            random = minCoef + r.nextFloat() * (maxCoef - minCoef);

            random = BigDecimal.valueOf(random)
                    .setScale(precision, RoundingMode.HALF_UP)
                    .doubleValue();

            Integer coefRandom = (int) random;
            if (coefRandom == 0) {
                coefRandom = 1;
            }

            myWriter.write(String.valueOf(coefRandom) + " " + String.valueOf(expRandom) + '\n');
        }*/
        System.out.println("File fully completed.");
        myWriter.close();
    }
}
