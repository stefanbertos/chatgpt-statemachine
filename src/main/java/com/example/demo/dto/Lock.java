package com.example.demo.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "locks")
public record Lock(
        @Id String id,
        Instant creationTime,
        String owner
) {
    public Lock(String id, String owner) {
        this(id, Instant.now(), owner);
    }
}
