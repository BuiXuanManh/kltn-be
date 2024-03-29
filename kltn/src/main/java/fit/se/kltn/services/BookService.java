package fit.se.kltn.services;

import fit.se.kltn.entities.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    Book save(Book book);
    List<Book> findAll();
    Optional<Book> findById(String id);
    Optional<Book> findByISBN(String isbn);
}
