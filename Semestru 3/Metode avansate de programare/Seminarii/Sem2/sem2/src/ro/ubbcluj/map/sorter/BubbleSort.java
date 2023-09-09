package ro.ubbcluj.map.sorter;

public class BubbleSort extends AbstractSorter{

    public BubbleSort(Integer[] vect) {
        super(vect);
    }

    /**
     * sorteaza prin BubbleSort si returneaza vectorul sortat
     * @return
     */
    @Override
    public Integer[] sort() {
        boolean swap = true;

        while (swap) {
            swap = false;
            for (int j = 0; j < vect.length - 1; j++) {
                if (vect[j] > vect[j+1]) {
                    swap = true;
                    var aux = vect[j];
                    vect[j] = vect[j+1];
                    vect[j+1] = aux;
                }
            }
        }

        return vect;
    }
}
