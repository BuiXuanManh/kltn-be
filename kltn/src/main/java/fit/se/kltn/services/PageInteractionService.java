package fit.se.kltn.services;

import fit.se.kltn.entities.PageInteraction;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
