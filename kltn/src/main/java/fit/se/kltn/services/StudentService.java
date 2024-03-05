package fit.se.kltn.services;

import fit.se.kltn.entities.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StudentService {
    List<Student> findAll();
    Optional<Student> findById(String id);
    Optional<Student> findByStudentCode(String code);
    Student save(Student student);
}
