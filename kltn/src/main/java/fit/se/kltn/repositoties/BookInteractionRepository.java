package fit.se.kltn.repositoties;

import fit.se.kltn.entities.BookInteraction;
import fit.se.kltn.enums.InteractionStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookInteractionRepository extends MongoRepository<BookInteraction, String> {
    Optional<BookInteraction> findByBook_IdAndProfile_Id(String bockId, String profileId);
    List<BookInteraction> findByProfile_IdAndStatus(String id, InteractionStatus status);
    List<BookInteraction> findByBook_Id(String id);
    @Query("{ 'nominatedDate' : { $gte: ?0, $lt: ?1 } }")
    List<BookInteraction> findByNominatedDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
