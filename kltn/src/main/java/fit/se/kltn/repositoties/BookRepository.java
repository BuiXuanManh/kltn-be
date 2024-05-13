package fit.se.kltn.repositoties;

import fit.se.kltn.entities.Book;
import fit.se.kltn.enums.BookStatus;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findByTitle(String title);
    List<Book> findByStatus(BookStatus status);
}
