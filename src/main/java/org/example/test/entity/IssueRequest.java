package org.example.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.io.Serializable;


@Data
@Entity
@Table(name = "issuerequest")
public class IssueRequest implements Serializable {

  @Id
  private long readerId;
  @Id
  private long bookId;

  public IssueRequest(long readerId, long bookId) {
    this.readerId = readerId;
    this.bookId = bookId;
  }

  public IssueRequest() {
  }
}
