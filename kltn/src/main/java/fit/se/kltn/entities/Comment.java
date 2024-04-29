package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
    private LocalDateTime createAt;
    @Field("parent_id")
    @DocumentReference(lazy = true)
    private Comment parent;
    @Indexed
    private Double rate;
    private RateType type;
    private int childrenCount;
    @Field("profile_id")
    @JsonIncludeProperties({"id", "firstName", "lastName", "image", "coverImage", "gender"})
    private Profile profile;
    @Field("page_id")
    @DocumentReference(lazy = true)
    private PageBook pageBook;
    @JsonIncludeProperties({"id", "firstName", "lastName", "image", "coverImage", "gender"})
    @DocumentReference(lazy = true)
    private List<Profile> liker;
}
