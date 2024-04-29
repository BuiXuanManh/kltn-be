package fit.se.kltn.repositoties;

import fit.se.kltn.entities.RatePage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatePageRepository extends MongoRepository<RatePage,String> {
    List<RatePage> findByPageBook_Id(String id);
    List<RatePage> findByProfile_Id(String id);
    Optional<RatePage> findByProfile_IdAndPageBook_Id(String profileId, String pageId);
}
