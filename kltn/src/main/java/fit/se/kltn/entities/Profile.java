package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
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
    @Indexed
    private String firstName;
    private String lastName;
    @Indexed
    private String image;
    @Indexed
    private String coverImage;
    private boolean gender;
    private LocalDate birthday;
    @Email
    private String email;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Field("user_id")
    @Indexed(unique = true)
    @DocumentReference(lazy = true)
    private User user;

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
