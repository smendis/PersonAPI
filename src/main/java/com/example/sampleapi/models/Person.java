package com.example.sampleapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Entity
//@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;

    public Person(String name) {
        this.name = name;
    }
}
