package org.example;

import org.example.model.Todo;
import org.example.model.User;
import org.example.service.TodoService;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static Scanner scannerInt = new Scanner(System.in);
    private static Scanner scannerStr = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static TodoService todoService = new TodoService();
    private static Optional<User> loginUser;

    public static void main(String[] args) {

        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.atLevel(Level.ERROR);

        int steepCode = 10;
        while (steepCode != 0) {
            System.out.println("1.Sign up, 2.Sign in, 0.Exit");
            int n = scannerInt.nextInt();
            switch (n) {
                case 1 -> signUp();
                case 2 -> signIn();
                case 0 -> steepCode = 0;
            }
        }
    }

    private static void signUp() {
        System.out.println("Enter name: ");
        String name = scannerStr.nextLine();
        System.out.println("Enter age: ");
        int age = scannerInt.nextInt();
        User user = new User(name, age);
        Optional<User> user1 = userService.addUser(user);
        if (user1.isEmpty()) {
            System.out.println("Ushbu ismli user bazada mavjud, iltimos boshqa ism kiriting!");
        } else {
            System.out.println("Amaliyot muvaffaqiyatli yakunlandi!");
        }
    }

    private static void signIn() {
        System.out.println("Enter name: ");
        String name = scannerStr.nextLine();
        Optional<User> user = userService.hasUserByName(name);
        if (!user.isEmpty()) {
            System.out.println("Welcome " + name + "!");
            loginUser = user;
            begin();
        } else {
            System.out.println("Ushbu foydalanovchi bazada mavzud emas: ");
        }
    }

    private static void begin() {
        int steepCode = 10;
        while (steepCode != 0) {
            System.out.println("1.Add todo, 2.Add todo done, 3.My todo, 4.Search todo by name, 0.Exit");
            int n = scannerInt.nextInt();
            switch (n) {
                case 1 -> addTodo();
                case 2 -> addTodoDone();
                case 3 -> myTodo(loginUser.get().getId());
                case 4 -> searchTodo();
                case 0 -> steepCode = 0;
            }
        }
    }

    private static void searchTodo() {
        System.out.println("Enter name");
        String name = scannerStr.nextLine();
        Optional<User> user = userService.hasUserByName(name);
        if (user.isEmpty()){
            System.out.println("Ushbu user bazada mavjud emas!");
        } else {
            myTodo(user.get().getId());
        }
    }

    private static void myTodo(UUID userId) {
        Optional<List<Todo>> todos = todoService.myTodos(userId);

        if (todos.isEmpty()) {
            System.out.println("Sizda todolar mavjud emas!");
        } else {
            List<Todo> todoList = todos.get();
            int n = 1;
            for (Todo todo : todoList) {
                if (todo.isStatus()) {
                    System.out.println(n + ") " + todo.getText() + " ✅");
                } else {
                    System.out.println(n + ") " + todo.getText() + " ❌");
                }
                n++;
            }
        }
    }


    private static void addTodoDone() {
        System.out.println("Enter todo text: ");
        String text = scannerStr.nextLine();
        boolean result = todoService.addTodoDone(loginUser.get().getId(),text);
        if (result){
            System.out.println("amaliyot muvaffaqiyatli yakunlandi!");
        } else {
            System.out.println("bunday todo mavjud emas!");
        }
    }

    private static void addTodo() {
        System.out.println("Enter todo text: ");
        String text = scannerStr.nextLine();
        Todo todo = new Todo(loginUser.get().getId(), text, false);
        todoService.addTodo(todo);
        System.out.println("Amaliyot muvaffaqiyatli yakunlandi!");
    }

}