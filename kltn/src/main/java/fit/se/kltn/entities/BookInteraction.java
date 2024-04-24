package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "book_interactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInteraction {
    private String id;
    @DocumentReference(lazy = true)
    private Book book;
    @DocumentReference(lazy = true)
    @Field("profile_id")
//    @JsonIncludeProperties({"id", "firstName", "lastName", "image", "coverImage", "gender"})
    private Profile profile;
    private boolean like;
    private boolean share;
    private boolean read;
    private boolean save;
    private boolean newBook;
}
