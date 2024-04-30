package fit.se.kltn.implement;

import fit.se.kltn.entities.Comment;
import fit.se.kltn.enums.RateType;
import fit.se.kltn.repositoties.CommentRepository;
import fit.se.kltn.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @Override
    public List<Comment> findByPageId(String pageId) {
        return repository.findByPageBook_IdAndType(pageId,Sort.by(Sort.Direction.DESC, "createdAt"),RateType.COMMENT);
    }

    @Override
    public Optional<Comment> findByProfileIdAndBookIdAndType(String pId, String bookId, RateType type) {
        return repository.findByProfile_IdAndBook_IdAndType(pId,bookId,type);
    }

    @Override
    public Optional<Comment> findByProfileIdAndPageIdAndType(String pId, String pageId, RateType type) {
        return repository.findByProfile_IdAndPageBook_IdAndType(pId,pageId,type);
    }

    @Override
    public List<Comment> findByBookIdAndType(String bookId, RateType type) {
        return repository.finByBookId(bookId,type);
    }
}
