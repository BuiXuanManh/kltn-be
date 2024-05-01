package fit.se.kltn.services;

import fit.se.kltn.entities.Report;
import fit.se.kltn.enums.ReportType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReportService {
    List<Report> getAll();
    List<Report> getByPageId(String pageId);
    List<Report> getByType(ReportType type);
    Optional<Report> findById(String id);
    Report save(Report report);
    List<Report> findByProfileId(String profile);
}
