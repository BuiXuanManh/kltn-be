package fit.se.kltn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "book_interactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInteraction {
    private String id;
    @DocumentReference(lazy = true)
    private Book book;
    @DocumentReference(lazy = true)
    private User user;
    private boolean like;
    private boolean share;
    private boolean read;
    private boolean save;
    private boolean newBook;
}
