import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        myStack.push(15);
        myStack.push(10);
        myStack.push(5);
        System.out.println(myStack.peek());
        myStack.pop();
        System.out.println(myStack.peek());
        System.out.println(myStack.peek());
    }
    Stack stack = new Stack<>();
}