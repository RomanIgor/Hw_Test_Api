package org.example.test.service;

import org.example.test.entity.Reader;
import java.util.List;
import java.util.Optional;

public interface ReaderService {

    Reader addReader(Reader reader);

    List<Reader>getAllReaders();

    Optional<Reader> getReaderById(Long id);

    void deleteReader(long id);


}
