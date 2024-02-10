package org.example.test.service;

import org.example.test.entity.Book;
import org.example.test.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    BookRepository bookRepositpory;

    @Autowired
    public BookServiceImpl(BookRepository bookRepositpory) {
        this.bookRepositpory = bookRepositpory;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepositpory.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepositpory.findById(id);
    }

    @Override
    public Book addNewBook(Book book) {
        return bookRepositpory.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepositpory.deleteById(id);
    }
}
