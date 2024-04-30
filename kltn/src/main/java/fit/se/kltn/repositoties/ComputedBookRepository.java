package fit.se.kltn.repositoties;

import fit.se.kltn.entities.ComputedBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComputedBookRepository extends MongoRepository<ComputedBook,String> {
    Optional<ComputedBook> findByBook_Id(String id);
}
