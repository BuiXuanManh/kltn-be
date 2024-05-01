package fit.se.kltn.implement;

import fit.se.kltn.entities.ComputedPage;
import fit.se.kltn.repositoties.ComputedPageRepository;
import fit.se.kltn.services.ComputedPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class ComputedPageImpl implements ComputedPageService {
    @Autowired
    private ComputedPageRepository repository;
    @Override
    public Optional<ComputedPage> findByPageId(String id) {
        return repository.findByPageBook_Id(id);
    }

    @Override
    public Optional<ComputedPage> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public ComputedPage save(ComputedPage computedPage) {
        return repository.save(computedPage);
    }
}
