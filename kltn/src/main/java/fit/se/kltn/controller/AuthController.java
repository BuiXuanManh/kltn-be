package fit.se.kltn.controller;

import fit.se.kltn.dto.ResetPassDto;
import fit.se.kltn.dto.SignupDto;
import fit.se.kltn.dto.TokenDto;
import fit.se.kltn.dto.UsernameDto;
import fit.se.kltn.entities.User;
import fit.se.kltn.jwt.JwtRequest;
import fit.se.kltn.jwt.JwtResponse;
import fit.se.kltn.jwt.RefreshTokenRequest;
import fit.se.kltn.services.AuthService;
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
    public ResponseEntity<User> signup(@RequestBody SignupDto dto) {
        return ResponseEntity.ok(service.signup(dto));
    }
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody JwtRequest dto) {
        return ResponseEntity.ok(service.signin(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(service.refreshToken(request));
    }
    @PostMapping("/password/forgot")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid UsernameDto dto) {
        return ResponseEntity.ok(service.forgotPassword(dto.getUsername()));
    }
    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPassDto dto) {
        return ResponseEntity.ok(service.resetPassword(dto.getToken(), dto.getPassword()));
    }

}