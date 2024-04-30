package fit.se.kltn.implement;

import fit.se.kltn.entities.ComputedBook;
import fit.se.kltn.repositoties.ComputedBookRepository;
import fit.se.kltn.services.ComputedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class ComputedBookImpl implements ComputedBookService {
    @Autowired
    private ComputedBookRepository repository;
    @Override
    public List<ComputedBook> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ComputedBook> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<ComputedBook> findByBookId(String id) {
        return repository.findByBook_Id(id);
    }

    @Override
    public ComputedBook save(ComputedBook computedBook) {
        return repository.save(computedBook);
    }
}
