package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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

@Document("page_interactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInteraction {
    @Id
    private String id;
    @Field("page_id")
    @DocumentReference(lazy = true)
    @ToString.Include
    private PageBook pageBook;
    @DocumentReference(lazy = true)
    @Field("profile_id")
    @JsonIncludeProperties({"id", "firstName", "lastName", "image", "coverImage", "gender"})
    @ToString.Include
    private Profile profile;
    @Indexed
    private LocalDateTime readTime;
    private boolean like;
    private boolean share;
    private boolean read;
    private boolean save;
    private boolean mark;
    @DocumentReference(lazy = true)
    private List<Comment> comments;
}
