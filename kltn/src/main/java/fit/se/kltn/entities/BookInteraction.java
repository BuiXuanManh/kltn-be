package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import fit.se.kltn.enums.InteractionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "book_interactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInteraction {
    @Id
    private String id;
    @Field("book_id")
    @JsonIncludeProperties({"id", "title", "pageCount", "uploadDate", "image", "bgImage", "authors","updateDate"})
    @DocumentReference(lazy = true)
    private Book book;
    @Field("profile_id")
    @JsonIncludeProperties({"id", "firstName", "lastName", "image", "coverImage", "gender"})
    @DocumentReference(lazy = true)
    private Profile profile;
    private boolean followed;
    private boolean nominated;
    @Indexed
    private LocalDateTime nominalTime;
    @Indexed
    private LocalDateTime readTime;
    @Indexed
    private int readCount;
    private InteractionStatus status;

    public BookInteraction(Book book, Profile profile, int readCount) {
        this.book = book;
        this.profile = profile;
        this.readCount = readCount;
    }
}
