package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ratePages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatePage {
    @Id
    private String id;
    @Field("profile_id")
    @JsonIncludeProperties({"id", "firstName", "lastName", "image", "coverImage", "gender"})
    @DocumentReference(lazy = true)
    private Profile profile;
    @Field("page_id")
    @DocumentReference(lazy = true)
    private PageBook page;
    @Min(1) @Max(5)
    private Double rate;
}