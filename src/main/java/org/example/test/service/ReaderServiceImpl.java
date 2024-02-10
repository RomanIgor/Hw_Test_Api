package org.example.test.service;

import org.example.test.entity.Reader;
import org.example.test.repository.ReaderRepositorry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReaderServiceImpl implements ReaderService {

    ReaderRepositorry readerRepositorry;
    @Autowired
    public ReaderServiceImpl(ReaderRepositorry readerRepositorry) {
        this.readerRepositorry = readerRepositorry;
    }

    @Override
    public Reader addReader(Reader reader) {
        return readerRepositorry.save(reader);
    }

    @Override
    public List<Reader> getAllReaders() {
        return readerRepositorry.findAll();
    }

    @Override
    public Optional<Reader> getReaderById(Long id) {
        return readerRepositorry.findById(id);
    }

    @Override
    public void deleteReader(long id) {
        readerRepositorry.deleteById(id);

    }
}
