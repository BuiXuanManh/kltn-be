package fit.se.kltn.repositoties;

import fit.se.kltn.entities.PageInteraction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageInteractionRepository extends MongoRepository<PageInteraction, String> {
    List<PageInteraction> findByPageBook_Id(String id);
    List<PageInteraction> findByProfile_Id(String id);
    Optional<PageInteraction> findByProfile_IdAndPageBook_Id(String profileId, String pageBookId);
}
