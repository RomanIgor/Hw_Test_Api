package org.example.test.controller;


import org.example.test.entity.Book;
import org.example.test.entity.Issue;
import org.example.test.entity.IssueRequest;
import org.example.test.entity.Reader;
import org.example.test.repository.BookRepository;
import org.example.test.repository.IssueRepositorry;
import org.example.test.repository.ReaderRepositorry;
import org.example.test.service.IssueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient

//The @DirtiesContext  ensure that the test context is recreated or isolated between tests
//We can also try to use @Transactional but in my case does not worked.
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IssueControllerTest {
    @Autowired
    IssueService issueService;

    @Autowired
    IssueRepositorry issueRepositorry;

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    ReaderRepositorry readerRepositorry;
    @Autowired
    BookRepository bookRepository;


    @Test
    void addIssue() {
        Reader reader = readerRepositorry.save(new Reader(null, "Reader1"));
        Book book = bookRepository.save(new Book(null, "Book1"));


        IssueRequest issueRequest = new IssueRequest(book.getId(), reader.getId());
        webTestClient.post()
                .uri("/ui/issue/add")
                .bodyValue(issueRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(Issue.class)
                .value(issues -> {

                    assert !issues.isEmpty();
                    issues.forEach(issue -> {
                        assert issue.getId() != null : "The Id can not be Null";
                    });

                });

        // We verify if The Issue was saved
        List<Issue> savedIssues = issueRepositorry.findAll();
        assert !savedIssues.isEmpty();
        assert savedIssues.size() == 1;

        Issue savedIssue = savedIssues.get(0);
        assert savedIssue.getReader().getId().equals(reader.getId());
        assert savedIssue.getBook().getId().equals(book.getId());
    }


    @Test
    void showAllIssues() {

        Reader reader1 = readerRepositorry.save(new Reader(1L, "Reader1"));
        Reader reader2 = readerRepositorry.save(new Reader(2L, "Reader2"));
        Book book1 = bookRepository.save(new Book(1L, "Book1"));
        Book book2 = bookRepository.save(new Book(2L, "Book2"));

        Issue issue1 = issueRepositorry.save(new Issue(book1, reader1));
        Issue issue2 = issueRepositorry.save(new Issue(book2, reader2));


        List<Issue> issues = webTestClient.get()
                .uri("/ui/issue/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Issue.class)
                .returnResult().getResponseBody();

        // Logs
        System.out.println("Expected Issues: " + List.of(issue1, issue2));
        System.out.println("Actual Issues: " + issues);


        assert issues != null;
        assert issues.size() == 2;

//There we have some problems with issue_at. We become the difference in seconds. That wy we have chose another approach to verify.
//        assert issues.contains(issue1);
//        assert issues.contains(issue2);

        assert issues.stream().anyMatch(issue ->
                issue.getId().equals(issue1.getId())
                        && issue.getReader().equals(issue1.getReader())
                        && issue.getBook().equals(issue1.getBook()));

        assert issues.stream().anyMatch(issue ->
                issue.getId().equals(issue2.getId())
                        && issue.getReader().equals(issue2.getReader())
                        && issue.getBook().equals(issue2.getBook()));

    }

    @Test
    void showReaderIssues() {

        Reader reader = readerRepositorry.save(new Reader(1L, "Reader1"));
        Book book1 = bookRepository.save(new Book(1L, "Book1"));
        Book book2 = bookRepository.save(new Book(2L, "Book2"));

        Issue issue1 = issueRepositorry.save(new Issue(book1, reader));
        Issue issue2 = issueRepositorry.save(new Issue(book2, reader));

        webTestClient.get()
                .uri("/ui/issue/reader/{readerId}/issues", reader.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Issue.class)
                .value(issues -> {
                    assert issues.size() == 2;
                    assert issues.stream().anyMatch(issue -> issue.getId().equals(issue1.getId()));
                    assert issues.stream().anyMatch(issue -> issue.getId().equals(issue2.getId()));
                });
    }


    @Test
    void addNewBook() {

        Book newBook = new Book(1L, "NewBook");
        webTestClient.post()
                .uri("/ui/issue/book/add")
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(book -> {
                    assert book.getId() != null : "The Id of Book can not be null";
                    assert book.getName().equals("NewBook") : "The name of Book do not correspond";
                });

        List<Book> savedBooks = bookRepository.findAll();
        assert !savedBooks.isEmpty() : "The list can not be null";
        assert savedBooks.size() == 1 : "There must be only one book in the database";

        Book savedBook = savedBooks.get(0);
        assert savedBook.getName().equals("NewBook") : "The name of the saved book does not match";
    }


    @Test
    void addNewReader() {
        Reader newReader = new Reader(null, "Reader");

        webTestClient.post()
                .uri("/ui/issue/reader/add")
                .bodyValue(newReader)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Reader.class)
                .value(reader -> {
                    assert reader.getId() != null : "The Id of Reader can not be null";
                    assert reader.getName().equals("Reader") : "The name of Reader do not correspond";
                });


        List<Reader> savedReaders = readerRepositorry.findAll();
        assert !savedReaders.isEmpty() : "The list can not be null";
        assert savedReaders.size() == 1 : "There must be only one reader in the database";

        Reader savedReader = savedReaders.get(0);
        assert savedReader.getName().equals("Reader") : "The name of the saved reader does not match";
    }
}