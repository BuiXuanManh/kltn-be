package fit.se.kltn.repositoties;

import fit.se.kltn.dto.UserDto;
import fit.se.kltn.entities.User;
import fit.se.kltn.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByMssv(String mssv);
    Optional<User> findByEmail(String email);
    Optional<User> findByRole(ERole role);
}
