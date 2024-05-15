package fit.se.kltn.controller;

import fit.se.kltn.dto.UserDto;
import fit.se.kltn.entities.Profile;
import fit.se.kltn.entities.User;
import fit.se.kltn.enums.ERole;
import fit.se.kltn.enums.UserStatus;
import fit.se.kltn.services.ProfileService;
import fit.se.kltn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService service;
    @Autowired
    private ProfileService profileService;
    public Profile getProfile(String mssv) {
        User u = service.findByUserName(mssv).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        Optional<Profile> p = profileService.findByUserId(u.getId());
        if (p.isPresent()) {
            return p.get();
        } else throw new RuntimeException("không tìm thấy profile user có mssv: " + u.getMssv());
    }
    @GetMapping("/users")
    public List<Profile> getUser(@AuthenticationPrincipal UserDto dto){
        if(dto.getRole().equals(ERole.ADMIN)) {
            List<Profile> p = new ArrayList<>();
            List<User> l = service.findByRole(ERole.USER);
            for(User u: l){
                Profile profile = getProfile(u.getMssv());
                p.add(profile);
            }
            return p;
        }
        throw new RuntimeException("Bạn không có quyền admin");
    }
    @PostMapping("/users/save")
    public List<Profile> postUser(@AuthenticationPrincipal UserDto dto ,@RequestBody List<Profile> profiles){
        if(dto.getRole().equals(ERole.ADMIN)) {
            for(Profile p: profiles){
                User u = p.getUser();
                service.save(u);
            }
            return profiles;
        }
        throw new RuntimeException("Bạn không có quyền admin");
    }
    @PostMapping("/users/change/{id}")
    public User postUser(@AuthenticationPrincipal UserDto dto ,@PathVariable("id") String id, @RequestParam("type") String type){
        if(dto.getRole().equals(ERole.ADMIN)) {
            Optional<User> u= service.findById(id);
            if(u.isPresent()){
                User uu = u.get();
                uu.setUpdateAt(LocalDateTime.now());
                if(type.equals("active"))
                uu.setStatus(UserStatus.ACTIVE);
                else uu.setStatus(UserStatus.LOCKED);
                return service.save(uu);
            }
            throw new RuntimeException("Không tìm thấy user cos id: "+id);
        }
        throw new RuntimeException("Bạn không có quyền admin");
    }
}
