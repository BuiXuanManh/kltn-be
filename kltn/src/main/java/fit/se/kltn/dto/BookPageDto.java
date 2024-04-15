package fit.se.kltn.dto;

import fit.se.kltn.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookPageDto {
    private Page<Book> pageBook;
    private List<Integer> pageNumbers;
}
