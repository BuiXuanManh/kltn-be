package fit.se.kltn.services;

import fit.se.kltn.entities.Setting;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SettingService {
    List<Setting> findAll();
    Optional<Setting> findById(String id);
    Optional<Setting> findByProfileId(String id);
    Setting save(Setting setting);
}
