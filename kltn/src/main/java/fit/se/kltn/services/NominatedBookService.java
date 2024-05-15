package fit.se.kltn.services;

import fit.se.kltn.entities.NominatedBook;
import fit.se.kltn.enums.NominatedType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface NominatedBookService {
    List<NominatedBook> getAll();
    Optional<NominatedBook> findById(String id);
    NominatedBook save(NominatedBook book);
    Optional<NominatedBook> findByBookId(String bookId);
}
