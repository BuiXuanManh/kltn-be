package fit.se.kltn.services;

import fit.se.kltn.dto.NominatedBookDto;
import fit.se.kltn.entities.Book;
import fit.se.kltn.entities.BookInteraction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface BookInteractionService {
    List<BookInteraction> findAll();

    BookInteraction save(BookInteraction bookInteraction);

    Optional<BookInteraction> findById(String id);

    Optional<BookInteraction> getBookInteraction(String bookId, String profileId);
    List<BookInteraction> findByBookId(String bookId);
    List<BookInteraction> findByProfileId(String profileId);
    Map<Book, List<BookInteraction>> groupInteractionsByBookId(List<BookInteraction> interactions);
    List<Book> findRecentNominations(String period);
    List<Long> findRecentNominationsByDate();
    NominatedBookDto findNominationsWithCounts(String bookId);
    List<NominatedBookDto> findNominationsListWithCounts();
}
