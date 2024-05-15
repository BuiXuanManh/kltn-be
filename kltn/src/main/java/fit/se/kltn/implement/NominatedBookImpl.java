package fit.se.kltn.implement;

import fit.se.kltn.entities.NominatedBook;
import fit.se.kltn.enums.NominatedType;
import fit.se.kltn.repositoties.NominatedBookRepository;
import fit.se.kltn.services.NominatedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class NominatedBookImpl implements NominatedBookService {
    @Autowired
    private NominatedBookRepository repository;
    @Override
    public List<NominatedBook> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<NominatedBook> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public NominatedBook save(NominatedBook book) {
        return repository.save(book);
    }

    @Override
    public Optional<NominatedBook> findByBookId(String bookId) {
        return repository.findByBook_Id(bookId);
    }
}
