package fit.se.kltn.services;

import fit.se.kltn.dto.ProfileDto;
import fit.se.kltn.dto.SignupDto;
import fit.se.kltn.entities.Profile;
import fit.se.kltn.dto.JwtRequest;
import fit.se.kltn.jwt.JwtResponse;
import fit.se.kltn.jwt.RefreshTokenRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ProfileDto signup(SignupDto dto);
    JwtResponse signin(JwtRequest request);
    JwtResponse refreshToken(RefreshTokenRequest request);
    String forgotPassword(String username);
    String resetPassword(String token, String pass);
}
