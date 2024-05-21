package fit.se.kltn.repositoties;

import fit.se.kltn.entities.Setting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends MongoRepository<Setting,String> {
    Optional<Setting> findByProfile_Id(String id);
}
