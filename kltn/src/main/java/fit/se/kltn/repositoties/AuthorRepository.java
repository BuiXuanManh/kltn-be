package fit.se.kltn.repositoties;

import fit.se.kltn.entities.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends MongoRepository<Author,String> {
    Optional<Author> findByName(String name);
}
