package fit.se.kltn.controller;

import fit.se.kltn.dto.*;
import fit.se.kltn.entities.Profile;
import fit.se.kltn.jwt.JwtResponse;
import fit.se.kltn.jwt.RefreshTokenRequest;
import fit.se.kltn.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Qualifier("authServiceImpl")
    @Autowired
    private AuthService service;

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
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid UsernameDto dto) {
        return ResponseEntity.ok(service.forgotPassword(dto.getUsername()));
    }
    @PostMapping("/password/reset")
    @Operation(summary = "Đổi mật khẩu")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPassDto dto) {
        return ResponseEntity.ok(service.resetPassword(dto.getToken(), dto.getPassword()));
    }
}