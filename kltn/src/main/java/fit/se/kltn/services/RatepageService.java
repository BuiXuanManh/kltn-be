package fit.se.kltn.services;

import fit.se.kltn.entities.RatePage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RatepageService {
    List<RatePage> getAll();
    Optional<RatePage> findById(String id);
    List<RatePage> findByPageId(String id);
    List<RatePage> findByProfileId(String id);
    Optional<RatePage> findByProfileIdAndPageId(String proId,String id);
    RatePage save(RatePage ratePage);
}
