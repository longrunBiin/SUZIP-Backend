package Fo.Suzip.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


import java.io.Serializable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@DynamicInsert
//@RedisHash(value = "jwtToken", timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    private String username;

    @Column(columnDefinition = "varchar(1000)")
    private String accessToken;

    @Column(columnDefinition = "varchar(1000)")
    private String refreshToken;

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void updateToken(RefreshToken token) {
        this.username = token.getUsername();
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
    }
}