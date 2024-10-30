package org.example.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.MongoDB.MongoDBConnection;
import org.example.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    public static Optional<User> addUser(User user) {
        if (hasUserByName(user.getName()).isEmpty()) {
            List<User> users = readAll();
            users.add(user);
            write(users);
            return Optional.of(user);
        }
        return Optional.empty();
    }


    public static Optional<User> hasUserByName(String name) {
        List<User> users = readAll();
        for (User user : users) {
            if (user.getName().equals(name)) return Optional.of(user);
        }
        return Optional.empty();
    }

    private static List<User> readAll() {
        MongoDatabase database = MongoDBConnection.connectToDatabase();
        MongoCollection<Document> collection = database.getCollection("users");

        List<User> users = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                UUID id = doc.get("id", UUID.class);
                String name = doc.getString("name");
                int age = doc.getInteger("age");
                users.add(new User(id, name, age));
            }
        }
        return users;
    }

    private static void write(List<User> users) {
        MongoDatabase database = MongoDBConnection.connectToDatabase();
        MongoCollection<Document> collection = database.getCollection("users");

        collection.drop();

        List<Document> documents = new ArrayList<>();
        for (User user : users) {
            Document doc = new Document("id", user.getId())
                    .append("name", user.getName())
                    .append("age", user.getAge());
            documents.add(doc);
        }
        if (!documents.isEmpty()) {
            collection.insertMany(documents);
        }
    }

}
