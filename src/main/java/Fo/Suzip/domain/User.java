package Fo.Suzip.domain;

import Fo.Suzip.domain.oauth.ProviderType;
import Fo.Suzip.domain.oauth.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@Setter
@Entity
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String name;

    private String email;

    private String role;

    public User() {

    }
}


