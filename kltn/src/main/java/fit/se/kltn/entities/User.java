package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fit.se.kltn.dto.UserDto;
import fit.se.kltn.enums.ERole;
import fit.se.kltn.enums.UserStatus;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Document("users")
@NoArgsConstructor
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    @Indexed
    private String name;
    @Email
    private String email;
    private String password;
    private ERole role;
    @CreatedDate
    private LocalDateTime createAt;
    @LastModifiedDate
    private LocalDateTime updateAt;
    private UserStatus status;
    @JsonIgnore
    @ToString.Exclude
    @DocumentReference
    private List<Book> readingBook;

    public User(String username, String password, ERole role, UserStatus status) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public User(UserDto user) {
        this.id = user.getId();
        this.username=user.getUsername();
        this.name=user.getName();
        this.email=user.getEmail();
        this.role=user.getRole();
        this.password=user.getPassword();
        this.status=user.getStatus();
    }
}
