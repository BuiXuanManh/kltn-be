package fit.se.kltn.dto;


import fit.se.kltn.entities.User;
import fit.se.kltn.enums.ERole;
import fit.se.kltn.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements UserDetails {
    private String id;
    private String username;
    private String name;
    private String email;
    private String password;
    private ERole role;
    private UserStatus status;


    public UserDto(User user) {
        this.id = user.getId();
        this.username=user.getMssv();
        this.name=user.getName();
        this.email=user.getEmail();
        this.role=user.getRole();
        this.password=user.getPassword();
        this.status=user.getStatus();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return status.equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !status.equals(UserStatus.LOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status.equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return status.equals(UserStatus.ACTIVE);
    }
}
