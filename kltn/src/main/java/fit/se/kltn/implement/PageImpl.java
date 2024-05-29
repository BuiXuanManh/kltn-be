package fit.se.kltn.implement;

import fit.se.kltn.entities.PageBook;
import fit.se.kltn.repositoties.PageRepository;
import fit.se.kltn.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class PageImpl implements PageService  {
    @Autowired
    private PageRepository repository;

    @Override
    public List<PageBook> getPages() {
        return repository.findAll();
    }

    @Override
    public Optional<PageBook> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public PageBook save(PageBook pageBook) {
        return repository.save(pageBook);
    }

    @Override
    public List<PageBook> findByBookId(String id) {
        return repository.findByBook_Id(id);
    }

    @Override
    public Optional<PageBook> findByBookIdAndPageNo(String bId, int pageNo) {
        return repository.findByBook_IdAndPageNo(bId, pageNo);
    }
}
