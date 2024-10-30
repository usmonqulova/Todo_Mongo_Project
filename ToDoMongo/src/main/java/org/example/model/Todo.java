package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Todo {
    private UUID id;
    private UUID userId;
    private String text;
    private boolean status;

    public Todo() {
        this.id = UUID.randomUUID();
    }

    public Todo(UUID userId, String text, boolean status) {
        this();
        this.userId = userId;
        this.text = text;
        this.status = status;
    }
}

