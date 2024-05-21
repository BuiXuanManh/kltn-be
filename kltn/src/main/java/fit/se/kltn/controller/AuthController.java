package fit.se.kltn.controller;

import fit.se.kltn.dto.*;
import fit.se.kltn.entities.Profile;
import fit.se.kltn.entities.User;
import fit.se.kltn.enums.ERole;
import fit.se.kltn.jwt.JwtResponse;
import fit.se.kltn.jwt.RefreshTokenRequest;
import fit.se.kltn.services.AuthService;
import fit.se.kltn.services.ProfileService;
import fit.se.kltn.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Qualifier("authServiceImpl")
    @Autowired
    private AuthService service;
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;

    public Profile authenProfile(UserDto dto) {
        User u = userService.findByUserName(dto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        Optional<Profile> p = profileService.findByUserId(u.getId());
        if (p.isPresent()) {
            return p.get();
        } else throw new RuntimeException("không tìm thấy profile user có mssv: " + u.getMssv());
    }

    @GetMapping("/valid")
    public String validAdmin(@AuthenticationPrincipal UserDto dto) {
        if (dto != null) {
            Profile p = authenProfile(dto);
            if (dto.getRole().equals(ERole.ADMIN))
                return "true";
        }
        return "false";
    }

    @PostMapping("/signup")
    @Operation(summary = "Đăng ký")
    public ResponseEntity<ProfileDto> signup(@RequestBody @Valid SignupDto dto) {
        return ResponseEntity.ok(service.signup(dto));
    }

    @PostMapping("/signin")
    @Operation(summary = "Đăng nhập", description = "Đăng nhập tk: 20103091 mk: Manh@2002")
    public ResponseEntity<JwtResponse> signin(@RequestBody @Valid JwtRequest dto) {
        return ResponseEntity.ok(service.signin(dto));
    }

    @PostMapping("/refresh")
    @Operation(summary = "cập nhập lại token")
    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(service.refreshToken(request));
    }

    @PostMapping("/password/forgot")
    @Operation(summary = "Quên mật khẩu")
    public ResponseEntity<JwtResponse> forgotPassword(@RequestBody @Valid UsernameDto dto) {
        return ResponseEntity.ok(service.forgotPassword(dto.getUsername()));
    }

    @PostMapping("/password/reset")
    @Operation(summary = "Đổi mật khẩu")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPassDto dto) {
        return ResponseEntity.ok(service.resetPassword(dto.getToken(), dto.getPassword()));
    }
}