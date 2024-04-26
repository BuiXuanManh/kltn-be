package fit.se.kltn.repositoties;

import fit.se.kltn.entities.BookInteraction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookInteractionRepository extends MongoRepository<BookInteraction, String> {
    @Query("{'book.id':?0,'profile.id':?1}")
    Optional<BookInteraction> getBookInteraction(String bockId, String profileId);
}
