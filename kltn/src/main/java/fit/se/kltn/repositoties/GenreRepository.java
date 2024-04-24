package fit.se.kltn.repositoties;

import fit.se.kltn.entities.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends MongoRepository<Genre,String> {
    Optional<Genre> findByName(String name);
}
