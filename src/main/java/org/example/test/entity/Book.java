package org.example.test.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    public Book() {
    }

    public Book(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
