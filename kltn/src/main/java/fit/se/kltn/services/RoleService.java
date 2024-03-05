package fit.se.kltn.services;

import fit.se.kltn.entities.Role;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RoleService {
    Optional<Role> findByRoleName(String name);
    Role save(Role role);

}
