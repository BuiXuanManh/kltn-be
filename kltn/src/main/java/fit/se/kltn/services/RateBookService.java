package fit.se.kltn.services;

import fit.se.kltn.entities.RateBook;
import fit.se.kltn.entities.RatePage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RateBookService {
    List<RateBook> getAll();
    Optional<RateBook> findById(String id);
    List<RateBook> findByBookId(String id);
    List<RateBook> findByProfileId(String id);
    Optional<RateBook> findByProfileIdAndBookId(String profileId, String bookId);
    RateBook save(RateBook rateBook);
}
