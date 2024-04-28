package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import fit.se.kltn.enums.EmoType;
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
    @ToString.Include
    private PageBook pageBook;
    @Field("profile_id")
    @JsonIncludeProperties({"id", "firstName", "lastName", "image", "coverImage", "gender"})
    @ToString.Include
    private Profile profile;
    @Indexed
    private LocalDateTime readTime;
    private EmoType type;
    private int read;
    private boolean save;
    private boolean mark;
    @ToString.Include
    private List<Comment> comments;
    @ToString.Include
    private List<Report> reports;
}
