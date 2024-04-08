package fit.se.kltn.implement;

import fit.se.kltn.entities.Profile;
import fit.se.kltn.repositoties.ProfileRepository;
import fit.se.kltn.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProfileImpl implements ProfileService {
    @Autowired
    private ProfileRepository repository;
    @Override
    public List<Profile> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Profile> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Profile> findByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    @Override
    public Optional<Profile> findByFirstName(String firstName) {
        return repository.findByFirstName(firstName);
    }

    @Override
    public Optional<Profile> findByMssv(String mssv) {
        return repository.findByUserMssv(mssv);
    }

    @Override
    public Profile save(Profile profile) {
        return repository.save(profile);
    }
}
