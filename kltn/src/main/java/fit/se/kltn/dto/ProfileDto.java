package fit.se.kltn.dto;

import fit.se.kltn.entities.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileDto {
    private Profile profile;
    private String token;
    private String refreshToken;
}
