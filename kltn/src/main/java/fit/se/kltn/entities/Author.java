package fit.se.kltn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @Indexed
    private String id;
    @Indexed
    private String name;
}
