package fit.se.kltn.implement;

import fit.se.kltn.entities.Genre;
import fit.se.kltn.repositoties.GenreRepository;
import fit.se.kltn.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GenreImpl implements GenreService {
    @Autowired
    private GenreRepository repository;
    @Override
    public List<Genre> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Optional<Genre> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Genre save(Genre genre) {
        return repository.save(genre);
    }
}
