package fit.se.kltn.services;

import fit.se.kltn.entities.Comment;
import fit.se.kltn.enums.RateType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CommentService {
    List<Comment> findAll();
    Optional<Comment> findById(String id);
    Comment save(Comment comment);
    List<Comment> findByPageId(String pageId);
    Optional<Comment> findByProfileIdAndPageIdAndType(String pId, String pageId, RateType type);
}
