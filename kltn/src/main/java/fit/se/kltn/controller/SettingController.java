package fit.se.kltn.controller;

import fit.se.kltn.dto.UserDto;
import fit.se.kltn.entities.Profile;
import fit.se.kltn.entities.Setting;
import fit.se.kltn.entities.User;
import fit.se.kltn.services.ProfileService;
import fit.se.kltn.services.SettingService;
import fit.se.kltn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/settings")
public class SettingController {
    @Qualifier("settingImpl")
    @Autowired
    private SettingService service;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;
    @GetMapping("/user")
    public Setting getSettingByProfile(@AuthenticationPrincipal UserDto dto){
        Profile p = authenProfile(dto);
        return service.findByProfileId(p.getId()).orElse(null);
    }
    @PostMapping("/save")
    public Setting saveSetting(@RequestBody Setting setting, @AuthenticationPrincipal UserDto dto){
        Profile p = authenProfile(dto);
        Optional<Setting> s = service.findByProfileId(p.getId());
        if(s.isPresent()){
            Setting sett = s.get();
            sett.setColor(setting.getColor());
            sett.setFont(setting.getFont());
            sett.setLineHeight(setting.getLineHeight());
            sett.setTextSize(setting.getTextSize());
            return service.save(sett);
        }
        Setting sett = new Setting();
        sett.setProfile(p);
        sett.setColor(setting.getColor());
        sett.setFont(setting.getFont());
        sett.setLineHeight(setting.getLineHeight());
        sett.setTextSize(setting.getTextSize());
        return service.save(sett);
    }
    public Profile authenProfile(UserDto dto) {
        User u = userService.findByUserName(dto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        Optional<Profile> p = profileService.findByUserId(u.getId());
        if (p.isPresent()) {
            return p.get();
        } else throw new RuntimeException("không tìm thấy profile user có mssv: " + u.getMssv());
    }
}
