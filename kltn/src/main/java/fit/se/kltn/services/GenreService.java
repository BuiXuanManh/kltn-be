package fit.se.kltn.services;

import fit.se.kltn.entities.Genre;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GenreService {
    List<Genre> getAll();
    Optional<Genre>  findByName(String name);
    Optional<Genre> findById(String id);
    Genre save(Genre genre);
}
