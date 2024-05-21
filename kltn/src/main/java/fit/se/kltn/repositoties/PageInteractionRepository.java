package fit.se.kltn.repositoties;

import fit.se.kltn.entities.BookInteraction;
import fit.se.kltn.entities.PageInteraction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PageInteractionRepository extends MongoRepository<PageInteraction, String> {
    List<PageInteraction> findByPageBook_Id(String id);
    List<PageInteraction> findByProfile_Id(String id);
    Optional<PageInteraction> findByProfile_IdAndPageBook_Id(String profileId, String pageBookId);
    List<PageInteraction> findByPageBook_Book_Id(String bookId);
    @Query("{ 'readTime' : { $gte: ?0, $lt: ?1 } }")
    List<PageInteraction> findByReadDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<PageInteraction> findByProfile_IdAndMark(String pId, boolean mark);
}
