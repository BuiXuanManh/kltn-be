package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import fit.se.kltn.enums.NominatedType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("nominated_books")
public class NominatedBook {
    @Id
    private String id;
    @JsonIncludeProperties({"id", "title","pageCount","uploadDate","image","bgImage","authors"})
    @DocumentReference(lazy = true)
    @Field("book_id")
    private Book book;
    @Indexed
    private LocalDateTime updateAt;
    private NominatedType type;
}
