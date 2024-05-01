package fit.se.kltn.implement;

import fit.se.kltn.entities.Report;
import fit.se.kltn.enums.ReportType;
import fit.se.kltn.repositoties.ReportRepository;
import fit.se.kltn.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReportImpl implements ReportService {
    @Autowired
    private ReportRepository repository;
    @Override
    public List<Report> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Report> getByPageId(String pageId) {
        return repository.findByPageBook_Id(pageId);
    }

    @Override
    public List<Report> getByType(ReportType type) {
        return repository.findByType(type);
    }

    @Override
    public Optional<Report> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Report save(Report report) {
        return repository.save(report);
    }

    @Override
    public List<Report> findByProfileId(String profile) {
        return repository.findByProfile_Id(profile);
    }
}
