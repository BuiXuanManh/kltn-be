package fit.se.kltn.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignupDto {
    @NotNull(message = "Mã số sinh viên là bắt buộc")
    @Pattern(regexp = "^\\d{8}$", message = "Mã số sinh viên phải gồm 8 số")
    private String mssv;
    @NotNull(message = "Họ đệm là bắt buộc")
    @Pattern(regexp = "^[a-zA-Z]{2,}$", message = "Tên phải có 2 ký tự trở lên")
    private String firstName;
    @NotNull(message = "Tên là bắt buộc")
    @Pattern(regexp = "^[a-zA-Z]{2,}$", message = "Tên phải có 2 ký tự trở lên")
    private String lastName;
    @Email(message = "phải đúng định dạng email")
    @NotNull(message = "email là bắt buộc")
    private String email;
    @NotNull(message = "password là bắt buộc")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&+=])(?=\\S+$).{8,32}$", message = "Mật khẩu từ 8 - 32 ký tự gồm tối thiểu 1 chữ cái viết hoa, 1 chữ cái viết thường, 1 chữ số và 1 ký tự đặc biệt")
    private String password;
    private String bio;
    private String image;
    private String coverImage;
    private boolean gender;
    @NotNull(message = "Ngày sinh là bắt buộc")
    @Past(message = "Ngày sinh phải trước ngày hiện tại")
    private LocalDate birthday;
}

