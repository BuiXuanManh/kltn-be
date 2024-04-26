package fit.se.kltn.controller;

import fit.se.kltn.entities.Genre;
import fit.se.kltn.services.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    @Qualifier("genreImpl")
    @Autowired
    private GenreService service;
    @GetMapping
    @Operation(summary = "Lây danh sách thể loại")
    public List<Genre> getAll(){
        return service.getAll();
    }

}
