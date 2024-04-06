package fit.se.kltn.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Map;
import java.util.function.Function;
@Service
public interface JwtService {
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extractClaims, UserDetails user);
    boolean isTokenValid(String token, UserDetails userDetails);
    String extractUserName(String token);
    boolean isTokenExprired(String token);
}
