package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import fit.se.kltn.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    @Indexed
    private String id;
    @Field("comment_id")
    @JsonIncludeProperties({"id","content","createAt","type","rate"})
    private Comment comment;
    @JsonIncludeProperties({"id", "firstName", "lastName", "image", "coverImage", "gender"})
    @Field("profile_id")
    private Profile profile;
    @Field("page_id")
    private PageBook pageBook;
    private ReportType type;
    private String content;
}
