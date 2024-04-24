package fit.se.kltn.implement;

import fit.se.kltn.entities.Author;
import fit.se.kltn.repositoties.AuthorRepository;
import fit.se.kltn.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class AuthorImpl implements AuthorService {
    @Autowired
    private AuthorRepository repository;
    @Override
    public List<Author> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Author> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Author> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Author save(Author save) {
        return repository.save(save);
    }
}
