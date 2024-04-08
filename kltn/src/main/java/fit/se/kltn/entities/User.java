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
import java.util.Set;

@Data
@AllArgsConstructor
@Document("users")
@NoArgsConstructor
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String mssv;
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
    private Set<Book> readingBook;
    @JsonIgnore
    @ToString.Exclude
    @DocumentReference
    private Set<Book> likeBook;
    @JsonIgnore
    @ToString.Exclude
    @DocumentReference
    private Set<Book> saveBook;
    @JsonIgnore
    @ToString.Exclude
    @DocumentReference
    private Set<Book> nominateBook;
    @JsonIgnore
    @ToString.Exclude
    @DocumentReference
    private Set<Book> readBook;
    @JsonIgnore
    @ToString.Exclude
    @DocumentReference
    private Set<Comment> comments;
    @JsonIgnore
    @ToString.Exclude
    @DocumentReference
    private Set<Rate> rates;

    public User(String mssv, String password, ERole role, UserStatus status) {
        this.mssv = mssv;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public User(UserDto user) {
        this.id = user.getId();
        this.mssv=user.getUsername();
        this.name=user.getName();
        this.email=user.getEmail();
        this.role=user.getRole();
        this.password=user.getPassword();
        this.status=user.getStatus();
    }
}
