package fit.se.kltn.implement;

import fit.se.kltn.entities.Comment;
import fit.se.kltn.repositoties.CommentRepository;
import fit.se.kltn.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class CommentImpl implements CommentService {
    @Autowired
    private CommentRepository repository;
    @Override
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Comment> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Comment save(Comment comment) {
        return repository.save(comment);
    }
}
