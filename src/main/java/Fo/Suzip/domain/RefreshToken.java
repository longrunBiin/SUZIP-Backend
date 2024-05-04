package Fo.Suzip.domain;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@RedisHash(value = "jwtToken", timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    private String username;

    private String accessToken;

    private String refreshToken;

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void updateToken(RefreshToken token) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}