package fit.se.kltn.implement;

import fit.se.kltn.entities.RatePage;
import fit.se.kltn.repositoties.RatePageRepository;
import fit.se.kltn.services.RatepageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RatePageImpl implements RatepageService {
    @Autowired
    private RatePageRepository repository;
    @Override
    public List<RatePage> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<RatePage> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<RatePage> findByPageId(String id) {
        return repository.findByPageBook_Id(id);
    }

    @Override
    public List<RatePage> findByProfileId(String id) {
        return repository.findByProfile_Id(id);
    }

    @Override
    public Optional<RatePage> findByProfileIdAndPageId(String proId, String id) {
        return repository.findByProfile_IdAndPageBook_Id(proId, id);
    }

    @Override
    public RatePage save(RatePage ratePage) {
        return repository.save(ratePage);
    }

    @Override
    public List<RatePage> findByBookId(String id) {
        return repository.findByBook_Id(id);
    }
}