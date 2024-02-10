package org.example.test.service;

import jakarta.transaction.Transactional;
import org.example.test.entity.Book;
import org.example.test.entity.Issue;
import org.example.test.entity.IssueRequest;
import org.example.test.entity.Reader;
import org.example.test.repository.BookRepository;
import org.example.test.repository.IssueRepositorry;
import org.example.test.repository.ReaderRepositorry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

    private BookRepository bookRepository;
    private ReaderRepositorry readerRepository;

    private IssueRepositorry issueRepository;

    @Autowired
    public IssueServiceImpl(BookRepository bookRepository, ReaderRepositorry readerRepository, IssueRepositorry issueRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.issueRepository = issueRepository;
    }


    @Override
   @Transactional
    public Issue addNewIssue(IssueRequest issueRequest) {
        long readerId = issueRequest.getReaderId();
        long bookId = issueRequest.getBookId();

        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new NoSuchElementException("Не найден читатель с идентификатором \"" + readerId + "\""));

        List<Issue> existingIssues = issueRepository.findByReaderId(readerId);


        if (existingIssues.size() >= 5) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Читатель уже имеет максимальное количество выданных книг");
        }


        if (existingIssues.stream().anyMatch(issue -> issue.getBook().getId() == bookId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Книга уже выдана читателю");
        }


        if (issueRepository.findByBookIdAndReturnedAtIsNull(bookId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Книга уже выдана другому читателю");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Не найдена книга с идентификатором \"" + bookId + "\""));


        Issue issue = new Issue(book, reader);
        issueRepository.save(issue);

        return issue;
    }



    @Override
    public Optional<Issue> getIssueById(Long id) {
        return issueRepository.findById(id);
    }

    @Override
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    @Override
    public List<Issue> getIssuesByReaderId(long readerId) {

        return issueRepository.findByReaderId(readerId);
    }


    @Transactional
    @Override
    public Issue updateIssue(long issueId) {
        issueRepository.updateIssueReturnedAt(issueId);
        return issueRepository.findById(issueId).orElse(null);
    }


}
