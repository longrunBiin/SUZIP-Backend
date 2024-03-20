package Fo.Suzip.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class CustomUserDetails extends Member implements UserDetails {
    private Long id;
    private String email;
    private MemberRole role;
    private String password;

    private CustomUserDetails(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.role = member.getMemberRole();
    }

    private CustomUserDetails(String email, MemberRole role) {
        this.email = email;
        this.role = role;
    }

    private CustomUserDetails(String email, String password, MemberRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static CustomUserDetails of(Member member) {
        return new CustomUserDetails(member);
    }

    public static CustomUserDetails of(String email, MemberRole role) {
        return new CustomUserDetails(email, role);
    }

    public static CustomUserDetails of(String email, String password, MemberRole role) {
        return new CustomUserDetails(email, password, role);
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return null;//CustomAuthorityUtils.createAuthorities(role);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}