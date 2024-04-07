package fit.se.kltn.implement;

import fit.se.kltn.dto.SignupDto;
import fit.se.kltn.dto.UserDto;
import fit.se.kltn.entities.Profile;
import fit.se.kltn.entities.User;
import fit.se.kltn.enums.ERole;
import fit.se.kltn.enums.UserStatus;
import fit.se.kltn.dto.JwtRequest;
import fit.se.kltn.jwt.JwtResponse;
import fit.se.kltn.jwt.RefreshTokenRequest;
import fit.se.kltn.repositoties.UserRepository;
import fit.se.kltn.services.AuthService;
import fit.se.kltn.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

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
    private JwtService jwtService;

    @Override
    public Profile signup(SignupDto dto) {
        Optional<User> opUser = repository.findByMssv(dto.getMssv());
        if (opUser.isEmpty()) {
            User u = new User();
            u.setMssv(dto.getMssv());
            u.setRole(ERole.USER);
            u.setName(dto.getFirstName() + " " + dto.getFirstName());
            u.setEmail(dto.getEmail());
            u.setPassword(passwordEncoder.encode(dto.getPassword()));
            User us = repository.save(u);
            Profile p = new Profile();
            p.setBirthday(dto.getBirthday());
            p.setGender(dto.isGender());
            p.setFirstName(dto.getFirstName());
            p.setLastName(dto.getLastName());
            p.setUpdatedAt(LocalDateTime.now());
            p.setUser(us);
            return p;
        } else {
            throw new RuntimeException("existedUser");
        }
    }

    @Override
    public JwtResponse signin(JwtRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getMssv(), request.getPassword()));
        var user = repository.findByMssv(request.getMssv()).orElseThrow(() -> new IllegalArgumentException("invalid username"));
        UserDto dto = new UserDto(user);
        var jwt = jwtService.generateToken(dto);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), dto);
        JwtResponse response = new JwtResponse();
        response.setAccessToken(jwt);
        response.setRefreshToken(refreshToken);
        return response;
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest request) {
        String userName = jwtService.extractUserName(request.getToken());
        User user = repository.findByMssv(userName).orElseThrow(() -> new UsernameNotFoundException("user is not existed!"));
        UserDto dto = new UserDto(user);
        if (jwtService.isTokenValid(request.getToken(), dto)) {
            var jwt = jwtService.generateToken(dto);
            JwtResponse response = new JwtResponse();
            response.setAccessToken(jwt);
            response.setRefreshToken(request.getToken());
            return response;
        }
        return null;
    }

    @Override
    public String forgotPassword(String username) {
        User user = repository.findByMssv(username).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản " + username));
        if (user.getStatus() == UserStatus.LOOKED) {
            throw new RuntimeException("Tài khoản " + username + " đã bị khóa.");
        }
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new RuntimeException("Tài khoản " + user + " không sẵn sàng.");
        }
        return "sendCodeSuccess";
    }

    @Override
    public String resetPassword(String token, String pass) {
        if (!jwtService.isTokenExprired(token)) {
            String username = jwtService.extractUserName(token);
            User user = repository.findByMssv(username).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user " + username));
            user.setPassword(passwordEncoder.encode(pass));
            repository.save(user);
            return "passUpdateSuccess";
        }
        throw new RuntimeException("passUpdateFailed");
    }

}
