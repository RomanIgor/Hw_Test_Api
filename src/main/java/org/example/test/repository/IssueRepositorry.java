package org.example.test.repository;

import org.example.test.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepositorry extends JpaRepository<Issue,Long> {

    @Query("SELECT i FROM Issue i JOIN FETCH i.book JOIN FETCH i.reader WHERE i.reader.id = :readerId")
    List<Issue> findByReaderId(@Param("readerId") long readerId);

    @Modifying
    @Query("UPDATE Issue i SET i.returned_at = CURRENT_TIMESTAMP WHERE i.id = :issueId")
    void updateIssueReturnedAt(@Param("issueId") long issueId);

    @Query("SELECT i FROM Issue i WHERE i.book.id = :bookId AND i.returned_at IS NULL")
    Optional<Object> findByBookIdAndReturnedAtIsNull(@Param("bookId") long bookId);


}
