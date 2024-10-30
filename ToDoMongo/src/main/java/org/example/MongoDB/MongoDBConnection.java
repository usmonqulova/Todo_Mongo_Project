package org.example.MongoDB;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;

public class MongoDBConnection {
    public static MongoDatabase connectToDatabase() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);

        return mongoClient.getDatabase("admin");
    }
}
