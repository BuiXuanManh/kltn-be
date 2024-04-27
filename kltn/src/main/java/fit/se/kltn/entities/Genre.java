package fit.se.kltn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("genres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    private String id;
    @Indexed
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
