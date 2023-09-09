package ro.ubbcluj.map.sorter;

public class MergeSort extends AbstractSorter{

    public MergeSort(Integer[] vect) {
        super(vect);
    }

    /**
     * sorteaza prin MergeSort si returneaza vectorul sortat
     * @return
     */
    @Override
    public Integer[] sort() {

        sorteaza(vect, 0, vect.length - 1);

        return vect;
    }

    /**
     * functie ajutatoare pentru MergeSort (divizie)
     * @param vect
     * @param st
     * @param dr
     */
    private void sorteaza(Integer[] vect, int st, int dr) {
        if (st < dr) {

            int mij = (st + dr) / 2;
            sorteaza(vect, st, mij);
            sorteaza(vect, mij + 1, dr);

            interclaseaza(vect, st, mij, dr);
        }
    }

    /**
     * functie ajutatoare pentru MergeSort (interclasare)
     * @param vect
     * @param st
     * @param mij
     * @param dr
     */
    private void interclaseaza(Integer[] vect, int st, int mij, int dr) {
        int k = st;
        int len1 = mij - st + 1;
        int len2 = dr - (mij + 1) + 1;

        int[] S = new int[len1];
        int[] D = new int[len2];

        for (int i = 0; i < len1; i++)
        {
            S[i] = vect[st + i];
        }
        for (int j = 0; j < len2; j++)
        {
            D[j] = vect[mij + 1 + j];
        }

        int i = 0, j = 0;

        while (i < len1 && j < len2) {
            if (S[i] <= D[j]) {
                vect[k] = S[i];
                i++;
            }
            else
            {
                vect[k] = D[j];
                j++;
            }
            k++;
        }

        while (i < len1) {
            vect[k] = S[i];
            i++;
            k++;
        }

        while (j < len2) {
            vect[k] = D[j];
            j++;
            k++;
        }
    }
}
