package fit.se.kltn.entities;

import fit.se.kltn.enums.RateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    private String id;
    private String content;
    @Indexed
    private LocalDateTime createDate;
    @Field("parent_id")
    @DocumentReference(lazy = true)
    private Comment parent;
    @DocumentReference(lazy = true)
    @ToString.Include
    private List<Report> reports;
    @Indexed
    private Double rate;
    private RateType type;
}
