package fit.se.kltn.implement;

import fit.se.kltn.entities.Role;
import fit.se.kltn.repositoties.RoleRepository;
import fit.se.kltn.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class RoleImpl implements RoleService {
    @Autowired
    private RoleRepository repository;
    @Override
    public Optional<Role> findByRoleName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }
}
