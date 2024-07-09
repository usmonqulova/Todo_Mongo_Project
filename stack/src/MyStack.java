import java.util.EmptyStackException;

public class MyStack {

    private class Node {
        public int val;
        private Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    public Node first;
    private int index = 0;


    public void push(int n) {
        Node node = new Node(n);
        node.next = first;
        first = node;
        index++;
    }

    public int peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return first.val;
    }

    public int pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        index--;
        return first.val;
    }

    public boolean isEmpty() {
        return index == 0;
    }
}
