package fit.se.kltn.implement;

import fit.se.kltn.entities.Book;
import fit.se.kltn.repositoties.BookRepository;
import fit.se.kltn.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    @Override
    public Page<Book> findPage(int pageNo, int pageSize, String sortBy, String sortDerection) {
        Sort sort= Sort.by(Sort.Direction.fromString(sortDerection),sortBy);
        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        return repository.findAll(pageable);
    }

}
