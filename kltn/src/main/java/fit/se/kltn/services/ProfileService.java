package fit.se.kltn.services;

import fit.se.kltn.entities.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProfileService {
    List<Profile> findAll();
    Optional<Profile> findById(String id);
    Optional<Profile> findByLastName(String lastName);
    Optional<Profile> findByFirstName(String firstName);
    Optional<Profile> findByUserId(String mssv);
    Profile save(Profile profile);
}
