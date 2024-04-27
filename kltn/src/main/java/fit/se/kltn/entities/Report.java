package fit.se.kltn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    private String id;
    @DocumentReference(lazy = true)
    @Field("comment_id")
    private Comment comment;
    @DocumentReference(lazy = true)
    @Field("profile_id")
    private Profile profile;
    private String content;
}
