public class VectorCalculator {

    public Integer[] addition(Integer[] a, Integer[] b){

        Integer[] c = new Integer[a.length];

        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] + b[i];
        }

        return c;
    }

    public Double[] squareOfSum(Double[] a, Double[] b){

        Double[] c = new Double[a.length];

        for (int i = 0; i < a.length; i++) {
            c[i] = Math.sqrt(Math.pow(a[i], 4) + Math.pow(b[i], 4));
        }

        return c;
    }
}
