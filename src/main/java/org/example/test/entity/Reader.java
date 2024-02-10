package org.example.test.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reader")
public class Reader {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;


  public Reader(Long id, String name) {
    this.id = id;
    this.name = name;
  }
  public Reader() {
  }
}
