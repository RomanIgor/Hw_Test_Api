package org.example.test.controller;

import org.example.test.entity.Book;
import org.example.test.entity.Issue;
import org.example.test.entity.IssueRequest;
import org.example.test.entity.Reader;
import org.example.test.service.BookService;
import org.example.test.service.IssueService;
import org.example.test.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/ui/issue")

public class IssueController {

    private final IssueService issueService;

    private final BookService bookService;

    private final ReaderService readerService;

    @Autowired
    public IssueController(IssueService issueService, BookService bookService, ReaderService readerService) {
        this.issueService = issueService;
        this.bookService = bookService;
        this.readerService = readerService;
    }


    @PostMapping("/add")
    public ResponseEntity<Issue> addIssue(@RequestBody IssueRequest issueRequest) {
        try {
            Issue issue = issueService.addNewIssue(issueRequest);
            return new ResponseEntity<>(issue, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/all")
    public List<Issue> showAllIssues() {
        return issueService.getAllIssues();

    }


    @GetMapping("/reader/{readerId}/issues")
    public ResponseEntity<List<Issue>> showReaderIssues(@PathVariable Long readerId) {
        List<Issue> issues = issueService.getIssuesByReaderId(readerId);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }


    @PostMapping("/book/add")
    public ResponseEntity<Book> addNewBook(@RequestBody Book book) {
        Book newBook = bookService.addNewBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }


    @PostMapping("/reader/add")
    public ResponseEntity<Reader> addNewReader(@RequestBody Reader reader) {
        Reader newReader = readerService.addReader(reader);
        return new ResponseEntity<>(newReader, HttpStatus.CREATED);
    }

}

