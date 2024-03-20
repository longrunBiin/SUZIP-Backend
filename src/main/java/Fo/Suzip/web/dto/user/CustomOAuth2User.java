package Fo.Suzip.web.dto.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private final UserDTO userDTO;

    public CustomOAuth2User(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public static CustomOAuth2User of(String subject, String authority) {
        UserDTO userDTO = new UserDTO.UserDTOBuilder()
                .subject(subject)
                .role(authority)
                .build();
        return new CustomOAuth2User(userDTO);
    }

    @Override
    public Map<String, Object> getAttributes() {

        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userDTO.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getName() {

        return userDTO.getName();
    }

    public Long getId() {

        return userDTO.getId();
    }

    public String getUsername() {

        return userDTO.getUsername();
    }

    public String getEmail() {
        return userDTO.getEmail();
    }

    public String getRole() {
        return userDTO.getRole();
    }
}