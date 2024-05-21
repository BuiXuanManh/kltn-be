package fit.se.kltn.dto;

import fit.se.kltn.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateDto {
    private Book book;
    private Double rate;
}
