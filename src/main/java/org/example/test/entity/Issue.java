package org.example.test.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "issue")
public class Issue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "reader_id")
  private Reader reader;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  private LocalDateTime issued_at;

  private LocalDateTime returned_at;

  public Issue() {
  }
  public Issue(Book book, Reader reader) {
    this.book = book;
    this.reader = reader;
    this.issued_at = LocalDateTime.now();
    this.returned_at = null;
  }

}
