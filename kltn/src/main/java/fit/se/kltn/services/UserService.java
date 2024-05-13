package fit.se.kltn.services;

import fit.se.kltn.dto.UserDto;
import fit.se.kltn.entities.User;
import fit.se.kltn.enums.UserStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByUserName(String username);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    UserDetailsService userDetailsService();
    List<User> findByStatus(UserStatus status);
}
