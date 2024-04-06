package fit.se.kltn.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsernameDto {
    @NotNull(message = "Mã số sinh viên là bắt buộc")
    @Pattern(regexp = "^\\d{8}$", message = "Mã số sinh viên phải gồm 8 số")
    private String username;
}
