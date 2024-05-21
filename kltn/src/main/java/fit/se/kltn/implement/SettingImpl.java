package fit.se.kltn.implement;

import fit.se.kltn.entities.Setting;
import fit.se.kltn.repositoties.SettingRepository;
import fit.se.kltn.services.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SettingImpl implements SettingService {
    @Autowired
    private SettingRepository repository;

    @Override
    public List<Setting> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Setting> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Setting> findByProfileId(String id) {
        return repository.findByProfile_Id(id);
    }

    @Override
    public Setting save(Setting setting) {
        return repository.save(setting);
    }
}
