package org.example.test.service;

import org.example.test.entity.Issue;
import org.example.test.entity.IssueRequest;
import java.util.List;
import java.util.Optional;

public interface IssueService {
    Issue addNewIssue(IssueRequest issueRequest);

    Optional<Issue> getIssueById(Long id);

    List<Issue>getAllIssues();

    List<Issue> getIssuesByReaderId(long readerId);

    Issue updateIssue(long issueId);





}
