package fit.se.kltn.controller;

import fit.se.kltn.dto.UserDto;
import fit.se.kltn.entities.Profile;
import fit.se.kltn.entities.User;
import fit.se.kltn.services.ProfileService;
import fit.se.kltn.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService service;
    @Autowired
    private ProfileService profileService;
    @GetMapping("/profile")
    @Operation(summary = "Lấy thông tin cá nhân profile")
    public Profile getProfile(@AuthenticationPrincipal UserDto dto) {
        User u = service.findByUserName(dto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        Optional<Profile> p = profileService.findByMssv(u.getId());
        if (p.isPresent()) {
            return p.get();
        } else throw new RuntimeException("profile not found");
    }
    @PostMapping
    @Operation(summary = "Thêm user")
    public User save(User user) {
        return service.save(user);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Tìm user")
    public User findUser(@PathVariable("id") String id) {
        Optional<User> u = service.findById(id);
        if (u.isEmpty()) throw new RuntimeException("không tìm thấy user có id " + id);
        return u.get();
    }
    @GetMapping("/getAll")
    @Operation(summary = "lấy tất cả danh sách profile")
    public List<Profile> getAll() {
        return profileService.findAll();
    }
}
