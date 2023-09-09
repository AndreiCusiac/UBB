public class MyQueue {

    public MyNode firstNode;
    public MyNode lastNode;

    public MyQueue(MyNode firstNode, MyNode lastNode) {
        this.firstNode = firstNode;
        this.lastNode = lastNode;
    }

    public MyQueue() {
        this.firstNode = null;
        this.lastNode = null;
    }

    public MyNode getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(MyNode firstNode) {
        this.firstNode = firstNode;
    }

    public MyNode getLastNode() {
        return lastNode;
    }

    public void setLastNode(MyNode lastNode) {
        this.lastNode = lastNode;
    }

    public void add(MyNode newNode) {
        if (firstNode == null) {
            firstNode = lastNode = newNode;
        }
        else {
            lastNode.nextNode = newNode;
            lastNode = newNode;

            if (firstNode.nextNode == null) {
                firstNode.nextNode = lastNode;
            }
        }
    }

    public MyNode remove() {
        MyNode toReturn = firstNode;

        if (firstNode == null) {
            lastNode = null;
        } else {
            firstNode = firstNode.nextNode;
        }

        return toReturn;
    }

    public boolean isEmpty() {
        return firstNode == null;
    }
}
