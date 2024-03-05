package fit.se.kltn.implement;

import fit.se.kltn.entities.Book;
import fit.se.kltn.repositoties.BookRepository;
import fit.se.kltn.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookImpl implements BookService {
    @Autowired
    private BookRepository repository;
    @Override
    public Book save(Book book) {
        return repository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Book> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Book> findByISBN(String isbn) {
        return repository.findByIsbn(isbn);
    }
}
