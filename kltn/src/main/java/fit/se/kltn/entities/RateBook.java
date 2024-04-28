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

@Document(collection = "rateBooks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateBook {
    @Id
    private String id;
    @Field("profile_id")
    @JsonIncludeProperties({"id", "firstName", "lastName", "image", "coverImage", "gender"})
    private Profile profile;
    @Field("book_id")
    @JsonIncludeProperties({"id", "title","pageCount","uploadDate","image","bgImage","authors"})
    @DocumentReference(lazy = true)
    private Book book;
    @Min(0) @Max(5)
    private Double helpful;
    @Min(0) @Max(5)
    private Double contentBook;
    @Min(0) @Max(5)
    private Double understand;
}
