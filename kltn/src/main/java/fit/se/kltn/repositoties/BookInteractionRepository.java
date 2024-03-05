package fit.se.kltn.repositoties;

import fit.se.kltn.entities.BookInteraction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInteractionRepository extends MongoRepository<BookInteraction, String> {

}
