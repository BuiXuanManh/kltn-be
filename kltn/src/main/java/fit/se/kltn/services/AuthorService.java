package fit.se.kltn.services;

import fit.se.kltn.entities.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AuthorService {
    List<Author> getAll();
    Optional<Author> findById(String id);
    Optional<Author> findByName(String name);
    Author save(Author save);
}
