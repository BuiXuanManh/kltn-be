package fit.se.kltn.repositoties;

import fit.se.kltn.entities.Comment;
import fit.se.kltn.enums.RateType;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPageBook_IdAndType(String id, Sort sort, RateType type);
    Optional<Comment> findByProfile_IdAndBook_IdAndType(String pId,String bookId, RateType type);
    Optional<Comment> findByProfile_IdAndPageBook_IdAndType(String pId,String pageId, RateType type);
    List<Comment> findByBook_IdAndType(String bookId, RateType type);
}
