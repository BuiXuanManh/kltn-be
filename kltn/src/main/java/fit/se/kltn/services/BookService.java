package fit.se.kltn.services;

import fit.se.kltn.entities.Book;
import fit.se.kltn.enums.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    Book save(Book book);
    List<Book> findAll();
    Optional<Book> findById(String id);
    Page<Book> findPage(int pageNo, int pageSize, String sortBy, String sortDerection);
    Optional<Book> findByTitle(String title);
    List<Book> findByCreatedAt();
    List<Book> findByUpdateAt();
    List<Book> findByStatus(BookStatus status);
}
