package fit.se.kltn.implement;

import fit.se.kltn.entities.PageInteraction;
import fit.se.kltn.repositoties.PageInteractionRepository;
import fit.se.kltn.services.PageInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PageInteractionImpl implements PageInteractionService {
    @Autowired
    private PageInteractionRepository repository;
    @Override
    public List<PageInteraction> getInteractions() {
        return repository.findAll();
    }

    @Override
    public Optional<PageInteraction> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<PageInteraction> finByProfileId(String id) {
        return repository.findByProfile_Id(id);
    }

    @Override
    public List<PageInteraction> findByPageBookId(String id) {
        return repository.findByPageBook_Id(id);
    }

    @Override
    public Optional<PageInteraction> findByProfileIDAndPageBookId(String profileId, String pageBookId) {
        return repository.findByProfile_IdAndPageBook_Id(profileId, pageBookId);
    }

    @Override
    public PageInteraction save(PageInteraction interaction) {
        return repository.save(interaction);
    }

    @Override
    public List<PageInteraction> findByBookId(String bookId) {
        return repository.findByPageBook_Book_Id(bookId);
    }
}
