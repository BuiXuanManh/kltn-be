package fit.se.kltn.repositoties;

import fit.se.kltn.entities.PageBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends MongoRepository<PageBook,String> {
    Optional<PageBook> findById(String id);
    Optional<PageBook> findByPageNo(int pageNo);
    List<PageBook> findByBook_Id(String id);
}
