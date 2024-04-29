package fit.se.kltn.implement;

import fit.se.kltn.entities.RateBook;
import fit.se.kltn.entities.RatePage;
import fit.se.kltn.repositoties.RateBookRepository;
import fit.se.kltn.services.RateBookService;
import fit.se.kltn.services.RatepageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RateBookImpl implements RateBookService {
    @Autowired
    private RateBookRepository repository;
    @Override
    public List<RateBook> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<RateBook> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<RateBook> findByBookId(String id) {
        return repository.findByBook_Id(id);
    }

    @Override
    public List<RateBook> findByProfileId(String id) {
        return repository.findByProfile_Id(id);
    }

    @Override
    public Optional<RateBook> findByProfileIdAndBookId(String profileId, String bookId) {
        return repository.findByProfile_IdAndBook_Id(profileId,bookId);
    }

    @Override
    public RateBook save(RateBook rateBook) {
        return repository.save(rateBook);
    }
}
