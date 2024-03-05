package fit.se.kltn.implement;

import fit.se.kltn.dto.SignupDto;
import fit.se.kltn.entities.User;
import fit.se.kltn.enums.ERole;
import fit.se.kltn.jwt.JwtRequest;
import fit.se.kltn.jwt.JwtResponse;
import fit.se.kltn.repositoties.UserRepository;
import fit.se.kltn.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Component
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtServiceImpl jwtService;
    @Override
    public User signup(SignupDto dto){
        User u= new User();
        u.setUsername(dto.getUsername());
        u.setRole(ERole.USER);
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        log.info("toi day");
        return repository.save(u);
    }

    @Override
    public JwtResponse signin(JwtRequest request){
        log.info("signin");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        var user = repository.findByUsername(request.getUsername()).orElseThrow(() -> new IllegalArgumentException("invalid username"));
        var jwt = jwtService.generateToken(user);
        var refreshToken= jwtService.generateRefreshToken(new HashMap<>(),user);
        JwtResponse response= new JwtResponse();
        response.setAccessToken(jwt);
        response.setRefreshToken(refreshToken);
        return response;
    }

}
