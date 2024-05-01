package fit.se.kltn.repositoties;

import fit.se.kltn.entities.ComputedPage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComputedPageRepository extends MongoRepository<ComputedPage,String> {
    Optional<ComputedPage> findByPageBook_Id(String id);
}
