package fit.se.kltn.services;

import fit.se.kltn.entities.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CommentService {
    List<Comment> findAll();
    Optional<Comment> findById(String id);
    Comment save(Comment comment);
    Comment delete(Comment comment);
}
