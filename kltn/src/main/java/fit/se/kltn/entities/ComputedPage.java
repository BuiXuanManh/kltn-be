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

@Document("computed_pages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComputedPage {
    @Id
    private String id;
    @Field("page_id")
    @DocumentReference(lazy = true)
    private PageBook pageBook;
    @Indexed
    private Double readCount;
    @Indexed
    private int commentCount;
    @Indexed
    private long emotion;
    private long mark;
}
