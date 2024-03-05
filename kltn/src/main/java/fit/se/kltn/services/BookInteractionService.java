package fit.se.kltn.services;

import fit.se.kltn.entities.BookInteraction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookInteractionService {
    List<BookInteraction> findAll();
    BookInteraction save(BookInteraction bookInteraction);
    Optional<BookInteraction> findById(String id);
}
