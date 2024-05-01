package fit.se.kltn.services;

import fit.se.kltn.entities.ComputedPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ComputedPageService {
    Optional<ComputedPage> findByPageId(String id);
    Optional<ComputedPage> findById(String id);
    ComputedPage save(ComputedPage computedPage);
}
