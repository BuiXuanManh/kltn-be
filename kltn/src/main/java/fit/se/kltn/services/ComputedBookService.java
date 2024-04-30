package fit.se.kltn.services;

import fit.se.kltn.entities.ComputedBook;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ComputedBookService {
    List<ComputedBook> getAll();
    Optional<ComputedBook> findById(String id);
    Optional<ComputedBook> findByBookId(String id);
    ComputedBook save(ComputedBook computedBook);
}
