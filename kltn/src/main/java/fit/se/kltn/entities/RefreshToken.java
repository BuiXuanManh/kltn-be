package fit.se.kltn.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "refresh_token")
public class RefreshToken {
    @Id
    private String id;

    private String token;

    private Instant expiryDate;
    @DocumentReference(lazy = true)
    private User userInfo;

    public RefreshToken(String token, Instant expiryDate, User userInfo) {
        this.token = token;
        this.expiryDate = expiryDate;
        this.userInfo = userInfo;
    }
}
