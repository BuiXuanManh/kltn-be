package fit.se.kltn.repositoties;

import fit.se.kltn.entities.RateBook;
import fit.se.kltn.entities.RatePage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateBookRepository extends MongoRepository<RateBook,String> {
    List<RateBook> findByProfile_Id(String id);
    List<RateBook> findByBook_Id(String id);
    Optional<RateBook> findByProfile_IdAndBook_Id(String profileId, String bookId);
}
