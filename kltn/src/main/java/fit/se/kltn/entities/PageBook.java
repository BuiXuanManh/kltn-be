package fit.se.kltn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("pages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBook {
    @Id
    private String id;
    private String name;
    @Indexed
    private int pageNo;
    @DocumentReference
    @Field("book_id")
    private Book book;
}
