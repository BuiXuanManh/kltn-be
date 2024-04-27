package fit.se.kltn.repositoties;

import fit.se.kltn.entities.BookInteraction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookInteractionRepository extends MongoRepository<BookInteraction, String> {
    Optional<BookInteraction> findByBook_IdAndProfile_Id(String bockId, String profileId);
    List<BookInteraction> findByProfile_Id(String id);
    List<BookInteraction> findByBook_Id(String id);
}
