package fit.se.kltn.repositoties;

import fit.se.kltn.entities.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<Profile,String> {
    Optional<Profile> findByFirstName(String firstName);
    Optional<Profile> findByLastName(String lastName);
    @Query("{'user.id': ?0}")
    Optional<Profile> findByUserId(String id);

}
