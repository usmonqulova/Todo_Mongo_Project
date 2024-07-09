import java.util.ArrayDeque;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<String> queue = new ArrayDeque<>();
        queue.add("hello");
        queue.add("hello3");
        System.out.println(queue.peek());

        MyQueue myQueue = new MyQueue();
        myQueue.enqueue("madina");
        myQueue.enqueue("umida");
        System.out.println(myQueue.dequeue);
        System.out.println("peek -->" + myQueue.myQueue.peek();


    }
}