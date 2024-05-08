package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
    @Indexed(unique = true)
    private Book book;
    @Indexed
    @Min(1) @Max(5)
    private Double helpful;
    @Min(1) @Max(5)
    @Indexed
    private Double contentBook;
    @Indexed
    @Min(1) @Max(5)
    private Double understand;
    @Indexed
    private Double totalRate;
    @Indexed
    private Double readCount;
    @Indexed
    private Double readCountDay;
    @Indexed
    private Double readCountMonth;
    @Indexed
    private Double readCountYear;
    @Indexed
    private long mark;
    @Indexed
    private long save;
    @Indexed
    private Double contentPage;
    @Indexed
    private int reviewCount;
    @Indexed
    private long nominatedCount;
    @Indexed
    private long nominatedCountDay;
    @Indexed
    private long nominatedCountMonth;
    @Indexed
    private long nominatedCountYear;
    @Indexed
    private int commentCount;
    @Indexed
    private long fun;
    @Indexed
    private long like;
    @Indexed
    private long love;
    @Indexed
    private long sad;
    @Indexed
    private long angry;
}
