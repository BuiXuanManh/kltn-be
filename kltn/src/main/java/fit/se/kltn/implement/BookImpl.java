package fit.se.kltn.implement;

import fit.se.kltn.entities.Book;
import fit.se.kltn.enums.BookStatus;
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
    public Page<Book> findPage(int pageNo, int pageSize, String sortBy, String sortDerection) {
        Sort sort= Sort.by(Sort.Direction.fromString(sortDerection),sortBy);
        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    @Override
    public List<Book> findByCreatedAt() {
        Sort sort= Sort.by(Sort.Direction.fromString("desc"),"createdAt");
        return repository.findAll(sort);
    }

    @Override
    public List<Book> findByUpdateAt() {
        Sort sort= Sort.by(Sort.Direction.fromString("desc"),"updateDate");
        return repository.findAll(sort);
    }

    @Override
    public List<Book> findByStatus(BookStatus status) {
        return repository.findByStatus(status);
    }

}
