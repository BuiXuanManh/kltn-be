package fit.se.kltn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "rates")
public class Rate {
    @Id
    private String id;
    private Double rate;
    @Indexed
    @CreatedDate
    private LocalDateTime uploadDate;
    @Field("book_id")
    @Indexed(unique = true)
    @DocumentReference(collection = "books")
    private Book book;
}
