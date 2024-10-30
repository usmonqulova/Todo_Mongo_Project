package org.example.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.example.MongoDB.MongoDBConnection;
import org.example.model.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TodoService {

    public static Optional<Todo> addTodo(Todo todo) {
        List<Todo> todos = readAll();
        for (Todo todo1 : todos) {
            if (todo1.getText().equals(todo.getText())) {
                return Optional.empty();
            }
        }
        todos.add(todo);
        write(todos);
        return Optional.of(todo);
    }

    public static Optional<List<Todo>> myTodos(UUID userId) {
        List<Todo> todos = readAll();
        if (todos == null) {
            return Optional.empty();
        }
        List<Todo> newTodos = new ArrayList<>();
        for (Todo todo : todos) {
            if (todo.getUserId().equals(userId)) {
                newTodos.add(todo);
            }
        }
        return Optional.of(newTodos);
    }



    public static boolean addTodoDone(UUID userId, String text) {
        List<Todo> todos = readAll();
        boolean updated = false;
        for (Todo todo1 : todos) {
            if (todo1.getText().equals(text) && todo1.getUserId().equals(userId)) {
                todo1.setStatus(true);
                updated = true;
                break;
            }
        }
        if (updated) {
            write(todos);
        }
        return updated;
    }




    private static List<Todo> readAll() {
        MongoDatabase database = MongoDBConnection.connectToDatabase();
        MongoCollection<Document> collection = database.getCollection("todos");

        List<Todo> todos = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                UUID id = doc.get("id", UUID.class);
                UUID userId = doc.get("userId", UUID.class);
                String text = doc.getString("text");
                boolean status = doc.getBoolean("status", false);
                todos.add(new Todo(id, userId, text, status));
            }
        }
        return todos;
    }

    private static void write(List<Todo> todos) {
        MongoDatabase database = MongoDBConnection.connectToDatabase();
        MongoCollection<Document> collection = database.getCollection("todos");

        for (Todo todo : todos) {
            Document doc = new Document("id", todo.getId())
                    .append("userId", todo.getUserId())
                    .append("text", todo.getText())
                    .append("status", todo.isStatus());

            collection.replaceOne(
                    Filters.eq("id", todo.getId()),
                    doc,
                    new ReplaceOptions().upsert(true)
            );
        }
    }


}

