package fit.se.kltn.dto;

import fit.se.kltn.entities.NominatedBook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NominatedBookDto {
    private NominatedBook nominatedBook;
    private long nominated;
}
