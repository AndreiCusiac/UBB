package ro.ubbcluj.map.sem6;

public class Patrat {
    int latura;

    public Patrat(int latura) {
        this.latura = latura;
    }

    @Override
    public String toString() {
        return "Patrat{" +
                "latura=" + latura +
                '}';
    }

    public int getLatura() {
        return latura;
    }

    public void setLatura(int latura) {
        this.latura = latura;
    }
}
