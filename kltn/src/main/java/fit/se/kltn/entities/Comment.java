package fit.se.kltn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    private String id;
    @Indexed
    private String content;
    @Indexed
    private LocalDateTime uploadDate;
    @Field("parent_id")
    @Indexed(unique = true)
    @DocumentReference(collection = "comments")
    private Comment parent;
    private boolean unSend;
    @Field("book_id")
    @Indexed(unique = true)
    @DocumentReference(collection = "books")
    private Book book;
}
