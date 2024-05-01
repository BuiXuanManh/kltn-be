package fit.se.kltn.repositoties;

import fit.se.kltn.entities.Report;
import fit.se.kltn.enums.ReportType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    List<Report> findByPageBook_Id(String id);
    List<Report> findByProfile_Id(String id);
    List<Report> findByType(ReportType type);
}
