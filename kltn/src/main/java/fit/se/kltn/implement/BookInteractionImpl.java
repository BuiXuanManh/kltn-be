package fit.se.kltn.implement;

import fit.se.kltn.entities.Book;
import fit.se.kltn.entities.BookInteraction;
import fit.se.kltn.repositoties.BookInteractionRepository;
import fit.se.kltn.services.BookInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookInteractionImpl implements BookInteractionService {
    @Autowired
    private BookInteractionRepository repository;

    @Override
    public List<BookInteraction> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC,"nominalTime" ));
    }
    @Override
    public Map<Book, List<BookInteraction>> groupInteractionsByBookId(List<BookInteraction> interactions) {
        return interactions.stream()
                .collect(Collectors.groupingBy(BookInteraction::getBook));
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
        return repository.findByBook_IdAndProfile_Id(bookId, profileId);
    }

    @Override
    public List<BookInteraction> findByBookId(String bookId) {
        return repository.findByBook_Id(bookId);
    }

    @Override
    public List<BookInteraction> findByProfileId(String profileId) {
        return repository.findByProfile_Id(profileId);
    }
}
