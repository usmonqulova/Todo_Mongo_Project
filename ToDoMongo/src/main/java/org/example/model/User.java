package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
    private UUID id;
    private String name;
    private int age;

    public User() {
        this.id = UUID.randomUUID();
    }

    public User(String name,int age) {
        this();
        this.age = age;
        this.name = name;
    }
}
