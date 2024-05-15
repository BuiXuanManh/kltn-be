package fit.se.kltn.repositoties;

import fit.se.kltn.entities.User;
import fit.se.kltn.enums.ERole;
import fit.se.kltn.enums.UserStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByMssv(String mssv);
    Optional<User> findByEmail(String email);
    Optional<User> findByRole(ERole role);
    List<User> findByStatus(UserStatus status);
    List<User> findByCreateAt(LocalDateTime createAt);
    List<User> findByRole(ERole role, Sort sort);
}
