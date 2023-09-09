public class MyNode {
public Integer idConcurent;
public Integer idProblema;
public Integer punctaj;
public Integer timpIncarcare;

public MyNode nextNode;

    public MyNode(Integer idConcurent, Integer idProblema, Integer punctaj, Integer timpIncarcare, MyNode nextNode) {
        this.idConcurent = idConcurent;
        this.idProblema = idProblema;
        this.punctaj = punctaj;
        this.timpIncarcare = timpIncarcare;
        this.nextNode = nextNode;
    }

    public MyNode(Integer idConcurent, Integer idProblema, Integer punctaj, Integer timpIncarcare) {
        this.idConcurent = idConcurent;
        this.idProblema = idProblema;
        this.punctaj = punctaj;
        this.timpIncarcare = timpIncarcare;
        this.nextNode = null;
    }

    public Integer getIdConcurent() {
        return idConcurent;
    }

    public void setIdConcurent(Integer idConcurent) {
        this.idConcurent = idConcurent;
    }

    public Integer getIdProblema() {
        return idProblema;
    }

    public Integer getPunctaj() {
        return punctaj;
    }

    public Integer getTimpIncarcare() {
        return timpIncarcare;
    }

    public void setIdProblema(Integer idProblema) {
        this.idProblema = idProblema;
    }

    public MyNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(MyNode nextNode) {
        this.nextNode = nextNode;
    }
}
