package ro.ubbcluj.map.sem6;

public class Cerc {
    int raza;

    public Cerc(int raza) {
        this.raza = raza;
    }

    public int getRaza() {
        return raza;
    }

    public void setRaza(int raza) {
        this.raza = raza;
    }

    @Override
    public String toString() {
        return "Cerc{" +
                "raza=" + raza +
                '}';
    }
}
