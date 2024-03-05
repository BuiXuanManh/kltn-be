package fit.se.kltn.services;

import fit.se.kltn.dto.SignupDto;
import fit.se.kltn.entities.User;
import fit.se.kltn.jwt.JwtRequest;
import fit.se.kltn.jwt.JwtResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    User signup(SignupDto dto);
    JwtResponse signin(JwtRequest request);
}
