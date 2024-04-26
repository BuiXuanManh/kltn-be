package fit.se.kltn.implement;

import fit.se.kltn.entities.BookInteraction;
import fit.se.kltn.repositoties.BookInteractionRepository;
import fit.se.kltn.services.BookInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookInteractionImpl implements BookInteractionService {
    @Autowired
    private BookInteractionRepository repository;

    @Override
    public List<BookInteraction> findAll() {
        return repository.findAll();
    }

    @Override
    public BookInteraction save(BookInteraction bookInteraction) {
        return repository.save(bookInteraction);
    }

    @Override
    public Optional<BookInteraction> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<BookInteraction> getBookInteraction(String bookId, String profileId) {
        return repository.getBookInteraction(bookId, profileId);
    }
}
