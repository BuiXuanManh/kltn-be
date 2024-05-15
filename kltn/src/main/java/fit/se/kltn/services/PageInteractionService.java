package fit.se.kltn.services;

import fit.se.kltn.entities.Book;
import fit.se.kltn.entities.Genre;
import fit.se.kltn.entities.PageInteraction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface PageInteractionService {
    List<PageInteraction> getInteractions();
    Optional<PageInteraction> findById(String id);
    List<PageInteraction> finByProfileId(String id);
    List<PageInteraction> findByPageBookId(String id);
    Optional<PageInteraction> findByProfileIDAndPageBookId(String profileId, String pageBookId);
    PageInteraction save(PageInteraction interaction);
    List<PageInteraction> findByBookId(String bookId);
    List<Book> findRecentReads(String date);
    List<Book> findComputedByLove();
    List<Book> findComputedByComment();
    List<Book> findComputedByRate();
    List<Book> findComputedByRateCount();
    List<Book> findComputedBySave();
    List<Long> findRecentReadsByDate();
    List<Long> findRecentEmoByDate();
    List<Long> findRecentCommentByDate();
    List<Long> findRecentRateByDate();
    List<Long> findUserByDate();
}
