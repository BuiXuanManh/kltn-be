package fit.se.kltn.repositoties;

import fit.se.kltn.entities.NominatedBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NominatedBookRepository extends MongoRepository<NominatedBook,String> {
    Optional<NominatedBook> findByBook_Id(String bookId);
}
