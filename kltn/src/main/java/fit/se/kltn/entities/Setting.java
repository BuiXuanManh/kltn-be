package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import fit.se.kltn.enums.EColor;
import fit.se.kltn.enums.FontEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "settings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setting {
    private String id;
    @JsonIncludeProperties({"id", "firstName", "lastName", "image", "coverImage", "gender"})
    @Field("profile_id")
    @DocumentReference(lazy = true)
    private Profile profile;
    private EColor color;
    private FontEnum font;
    private int textSize;
    private Double lineHeight;
}
