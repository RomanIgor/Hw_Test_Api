package org.example.test.service;

import org.example.test.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
     Optional<Book> getBookById(Long id);

     Book addNewBook(Book book);

     void deleteBook(Long id);
}
