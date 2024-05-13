package fit.se.kltn.dto;

import fit.se.kltn.entities.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookGenresDto {
    private String field;
    private List<Genre> genres;
    private String keyword;
}
