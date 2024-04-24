package fit.se.kltn.services;

import fit.se.kltn.entities.PageBook;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PageService {
    List<PageBook> getPages();
    Optional<PageBook> findById(String id);
    PageBook save(PageBook pageBook);
    Optional<PageBook> findByPageNo(int pageNo);
}
