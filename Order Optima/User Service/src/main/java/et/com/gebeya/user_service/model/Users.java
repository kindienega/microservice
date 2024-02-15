package et.com.gebeya.user_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Entity
@Builder
public class Users extends BaseModel implements UserDetails {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")

    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    private Integer roleId;

    public static class UsersBuilder{
        private boolean isActive;

        public UsersBuilder isActive(boolean isActive){
            this.isActive=isActive;
            return this;

        }
        public Users build(){
            Users users=new Users();
            users.setIsActive(this.isActive);
            users.setUserName(this.userName);
            users.setPassword(this.password);
            users.setRole(this.role);
            users.setStatus(this.status);
            users.setRoleId(this.roleId);
            return users;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getIsActive();
    }
}
