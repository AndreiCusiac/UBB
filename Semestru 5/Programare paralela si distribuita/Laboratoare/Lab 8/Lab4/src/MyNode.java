public class MyNode {
public Integer coeficient;
public Integer exponent;

public MyNode nextNode;

    public MyNode(Integer coeficient, Integer exponent, MyNode nextNode) {
        this.coeficient = coeficient;
        this.exponent = exponent;
        this.nextNode = nextNode;
    }

    public MyNode(Integer coeficient, Integer exponent) {
        this.coeficient = coeficient;
        this.exponent = exponent;
        this.nextNode = null;
    }

    public Integer getCoeficient() {
        return coeficient;
    }

    public void setCoeficient(Integer coeficient) {
        this.coeficient = coeficient;
    }

    public Integer getExponent() {
        return exponent;
    }

    public void setExponent(Integer exponent) {
        this.exponent = exponent;
    }

    public MyNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(MyNode nextNode) {
        this.nextNode = nextNode;
    }
}
