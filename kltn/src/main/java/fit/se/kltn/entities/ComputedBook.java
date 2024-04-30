package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("computed_books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComputedBook {
    @Id
    private String id;
    @Field("book_id")
    @JsonIncludeProperties({"id", "title","pageCount","uploadDate","image","bgImage","authors"})
    @DocumentReference(lazy = true)
    private Book book;
    @Min(1) @Max(5)
    private Double helpful;
    @Min(1) @Max(5)
    private Double contentBook;
    @Min(1) @Max(5)
    private Double understand;
    private Double totalRate;
    private Double readCount;
    private long save;
    private Double contentPage;
    private int reviewCount;
    private long nominatedCount;
    private int commentCount;
    private long fun;
    private long like;
    private long love;
    private long sad;
    private long angry;
}
