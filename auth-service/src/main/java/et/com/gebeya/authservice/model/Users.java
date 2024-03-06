package et.com.gebeya.authservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import et.com.gebeya.authservice.enums.Role;
import et.com.gebeya.authservice.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
    @Column(name = "username", length = 32)
    private String userName;

    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;
    @Column(length = 255,unique = true)
    @Email(message = "Email is mandatory")
    private String email;


    public boolean isApproved() {
        if(status==Status.APPROVED){
            return true;
        }
        return false;
    }

    public static class UsersBuilder {
        private boolean isActive;

        public UsersBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Users build() {
            Users users = new Users();
            users.setIsActive(this.isActive);
            users.setUserName(this.userName);
            users.setPassword(this.password);
            users.setRole(this.role);
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
