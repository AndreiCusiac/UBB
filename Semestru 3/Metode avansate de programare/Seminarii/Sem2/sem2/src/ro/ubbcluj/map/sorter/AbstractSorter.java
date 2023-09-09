package ro.ubbcluj.map.sorter;

public abstract class AbstractSorter {

    Integer[] vect;

    public AbstractSorter(Integer[] vect) {
        this.vect = vect;
    }

    /**
     * returneaza vectorul vect
     * @return
     */
    public Integer[] getVect() {
        return vect;
    }

    /**
     * sorteaza vectorul sort si il returneaza
     * @return vect
     */
    public abstract Integer[] sort();

}
