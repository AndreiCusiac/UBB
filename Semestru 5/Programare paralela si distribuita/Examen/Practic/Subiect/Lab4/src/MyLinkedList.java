import java.util.Objects;

public class MyLinkedList {
    public MyNode firstNode;

    public MyLinkedList(MyNode firstNode) {
        this.firstNode = firstNode;
    }

    public MyLinkedList() {
        this.firstNode = null;
    }

    public MyNode getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(MyNode firstNode) {
        this.firstNode = firstNode;
    }

    public void add(MyNode newNode) {
        if (firstNode == null) {
            firstNode = newNode;
        }
        else {
            MyNode currentNode = firstNode;
            MyNode previousNode = null;
            boolean added = false;

            while (currentNode != null) {
                if (Objects.equals(currentNode.idProblema, newNode.idProblema)) {
                    currentNode.idConcurent += newNode.idConcurent;

                    if (currentNode.idConcurent == 0) {
                        if (previousNode != null) {
                            previousNode.nextNode = currentNode.nextNode;
                        } else {
                            firstNode = firstNode.nextNode;
                        }
                    }
                    added = true;
                    break;
                }
                if (currentNode.idProblema > newNode.idProblema) {
                    if (previousNode == null) {
                        newNode.nextNode = firstNode;
                        firstNode = newNode;
                    } else {
                        newNode.nextNode = currentNode;
                        previousNode.nextNode = newNode;
                    }
                    added = true;
                    break;
                }
                previousNode = currentNode;
                currentNode = currentNode.nextNode;
            }

            if (!added) {
                previousNode.nextNode = newNode;
            }
        }
    }

}
