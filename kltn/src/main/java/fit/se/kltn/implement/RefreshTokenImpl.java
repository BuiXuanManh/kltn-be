package fit.se.kltn.implement;

import fit.se.kltn.entities.RefreshToken;
import fit.se.kltn.repositoties.RefreshTokenRepository;
import fit.se.kltn.repositoties.UserRepository;
import fit.se.kltn.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class RefreshTokenImpl implements RefreshTokenService {
    @Autowired
    private RefreshTokenRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Value("${jwt.lifetime-refresh}")
    private int time;

    @Override
    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(),Instant.now().plusMillis(time),userRepository.findByUsername(username).get());
        return repository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            repository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new sign in request");
        }
        return token;
    }


}
