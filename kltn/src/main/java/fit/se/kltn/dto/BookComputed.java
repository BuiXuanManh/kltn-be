package fit.se.kltn.dto;

import fit.se.kltn.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookComputed {
    private int nominated;
    private int read;
    private int love;
    private int comment;
    private int save;
    private Double rate;
    private int rateCount;
}
