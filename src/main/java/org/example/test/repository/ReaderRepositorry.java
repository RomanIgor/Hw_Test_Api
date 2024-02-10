package org.example.test.repository;


import org.example.test.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepositorry extends JpaRepository<Reader,Long> {
}
