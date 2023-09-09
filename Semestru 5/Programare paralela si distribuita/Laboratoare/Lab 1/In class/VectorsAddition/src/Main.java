import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        VectorCalculator vectorCalculator = new VectorCalculator();

        int n = 4;

        Integer[] a = {0, 1 ,2, 3};
        Integer[] b = {1, 2 ,3, 4};
        Integer[] c = vectorCalculator.addition(a, b);

        for (int i = 0; i < n; i++) {
            System.out.print(c[i] + " ");
        }

        System.out.println();

        Double[] a1 = {0.0, 1.0 ,2.0, 3.0};
        Double[] b1 = {1.0, 2.0 ,3.0, 4.0};
        Double[] c1 = vectorCalculator.squareOfSum(a1, b1);

        for (int i = 0; i < n; i++) {
            System.out.print(c1[i] + " ");
        }
    }
}