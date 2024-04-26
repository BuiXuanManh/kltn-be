package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "profiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"user"})
public class Profile {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String image;
    private String coverImage;
    private boolean gender;
    private LocalDate birthday;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Field("user_id")
    @Indexed(unique = true)
    @DocumentReference(collection = "users")
    private User user;
    private List<BookInteraction> interactions;

    public Profile(String firstName, String lastName, String bio, String image, String coverImage, boolean gender, LocalDate birthday, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.coverImage = coverImage;
        this.gender = gender;
        this.birthday = birthday;
        this.user = user;
    }
    public Profile(String firstName, String lastName, boolean gender, LocalDate birthday, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.user = user;
    }
    public Profile(String id) {
        this.id = id;
    }
}
