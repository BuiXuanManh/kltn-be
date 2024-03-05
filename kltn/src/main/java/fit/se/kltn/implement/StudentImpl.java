package fit.se.kltn.implement;

import fit.se.kltn.entities.Student;
import fit.se.kltn.repositoties.StudentRepository;
import fit.se.kltn.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentImpl implements StudentService {
    @Autowired
    private StudentRepository repository;
    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Student> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Student> findByStudentCode(String code) {
        return repository.findByStudentCode(code);
    }

    @Override
    public Student save(Student student) {
        return repository.save(student);
    }
}
